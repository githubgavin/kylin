/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.protocol.hessian;

import com.caucho.hessian.client.*;
import com.caucho.hessian.io.*;
import com.store59.kylin.rpc.protocol.hessian.ConfigurationProperties.HessianHttpClientProperties;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;

/**
 * hessian客户端工厂类
 *
 * @author <a href="mailto:zhongc@59store.com">士兵</a>
 * @version 1.0 15/12/29
 * @since 1.0
 */
public class KylinHessianProxyFactory extends HessianProxyFactory {

    private HessianHttpClientProperties hessianHttpClientProperties;

    /**
     * 创建一个代理工厂
     */
    public KylinHessianProxyFactory() {
        super();
    }

    public void setHessian2(boolean hessian2) {
        this.setHessian2Request(hessian2);
        this.setHessian2Reply(hessian2);
    }

    /**
     * 创建一个代理工厂
     */
    public KylinHessianProxyFactory(ClassLoader loader) {
        super(loader);
    }

    /**
     * 重载该方法，采用 httpClient实现hessian远程调用
     *
     * @return
     */
    protected HessianConnectionFactory createHessianConnectionFactory() {
        String className
                = System.getProperty(HessianConnectionFactory.class.getName());

        HessianConnectionFactory factory = null;

        try {
            if (className != null) {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();

                Class<?> cl = Class.forName(className, false, loader);

                factory = (HessianConnectionFactory) cl.newInstance();

                return factory;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new HessianHttpClientConnectionFactory(hessianHttpClientProperties);
    }

    /**
     * 重载该方法，实现类采用KylinHessianProxy
     */
    public Object create(Class<?> api, URL url, ClassLoader loader) {
        if (api == null)
            throw new NullPointerException("api must not be null for KylinHessianProxyFactory.create()");
        InvocationHandler handler = null;

        handler = new KylinHessianProxy(url, this, api);

        return Proxy.newProxyInstance(loader,
                new Class[]{api,
                        HessianRemoteObject.class},
                handler);
    }

    public HessianHttpClientProperties getHessianHttpClientProperties() {
        return hessianHttpClientProperties;
    }

    public void setHessianHttpClientProperties(HessianHttpClientProperties hessianHttpClientProperties) {
        this.hessianHttpClientProperties = hessianHttpClientProperties;
    }
}
