/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.client;

import com.caucho.hessian.HessianException;
import com.caucho.hessian.client.*;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.RemoteProxyFailureException;
import org.springframework.remoting.caucho.HessianClientInterceptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 *
 * 修改invoke方法, 增加client端loadbalance支持, 可同时
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/5/12
 * @since 2.1
 */
public class KylinHessianClientInterceptor extends HessianClientInterceptor {

    private Map<String, Object> hessianProxyMap = new ConcurrentHashMap<>();
    private HessianProxyFactory proxyFactory;
    private ServiceUrlSelector serviceUrlSelector;
    private String exportName;
    private String serviceName;

    public KylinHessianClientInterceptor() {
        super();
        proxyFactory = new HessianProxyFactory();
        super.setProxyFactory(proxyFactory); // 保持和父类中同一个factory对象
    }

    /**
     * Set the HessianProxyFactory instance to use.
     * If not specified, a default HessianProxyFactory will be created.
     * <p>Allows to use an externally configured factory instance,
     * in particular a custom HessianProxyFactory subclass.
     */
    @Override
    public void setProxyFactory(HessianProxyFactory proxyFactory) {
        this.proxyFactory = (proxyFactory != null ? proxyFactory : new HessianProxyFactory());
        super.setProxyFactory(this.proxyFactory);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Object hessianProxy = null;
        String url = null;

        try {
            url = this.getServiceUrl();
            hessianProxy = hessianProxyMap.get(url);
            if (hessianProxy == null) {
                hessianProxy = createHessianProxy(this.proxyFactory);
                hessianProxyMap.put(url, hessianProxy);
            }
        } catch (MalformedURLException ex) {
            throw new RemoteLookupFailureException("Service URL [" + getServiceUrl() + "] is invalid", ex);
        }

        if (hessianProxy == null) {
            throw new IllegalStateException("HessianProxy init fail, service url : " + url);
        }

        ClassLoader originalClassLoader = overrideThreadContextClassLoader();
        try {
            return invocation.getMethod().invoke(hessianProxy, invocation.getArguments());
        } catch (InvocationTargetException ex) {
            Throwable targetEx = ex.getTargetException();
            // Hessian 4.0 check: another layer of InvocationTargetException.
            if (targetEx instanceof InvocationTargetException) {
                targetEx = ((InvocationTargetException) targetEx).getTargetException();
            }
            if (targetEx instanceof HessianConnectionException) {
                throw convertHessianAccessException(targetEx);
            } else if (targetEx instanceof HessianException || targetEx instanceof HessianRuntimeException) {
                Throwable cause = targetEx.getCause();
                throw convertHessianAccessException(cause != null ? cause : targetEx);
            } else if (targetEx instanceof UndeclaredThrowableException) {
                UndeclaredThrowableException utex = (UndeclaredThrowableException) targetEx;
                throw convertHessianAccessException(utex.getUndeclaredThrowable());
            } else {
                throw targetEx;
            }
        } catch (Throwable ex) {
            throw new RemoteProxyFailureException(
                    "Failed to invoke Hessian proxy for remote service [" + getServiceUrl() + "]", ex);
        } finally {
            resetThreadContextClassLoader(originalClassLoader);
        }
    }

    @Override
    public void afterPropertiesSet() {
        if(this.getServiceUrlSelector() == null) {
            throw new IllegalArgumentException("Property \'serviceUrlSelector\' is required");
        }
        if(this.getExportName() == null) {
            throw new IllegalArgumentException("Property \'exportName\' is required");
        }
        if(this.getServiceName() == null) {
            throw new IllegalArgumentException("Property \'serviceName\' is required");
        }
    }

    public String getServiceUrl() {
        String serviceUrl = getServiceUrlSelector().selectUrl(serviceName);
        if (!serviceUrl.endsWith("/")) {
            serviceUrl = serviceUrl + "/";
        }
        return serviceUrl + exportName;
    }

    public ServiceUrlSelector getServiceUrlSelector() {
        return serviceUrlSelector;
    }

    public String getExportName() {
        return exportName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceUrlSelector(ServiceUrlSelector serviceUrlSelector) {
        this.serviceUrlSelector = serviceUrlSelector;
    }

    public void setExportName(String exportName) {
        this.exportName = exportName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
