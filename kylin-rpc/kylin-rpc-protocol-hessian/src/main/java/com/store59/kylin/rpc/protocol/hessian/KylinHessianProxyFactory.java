/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.protocol.hessian;

import com.caucho.hessian.client.*;
import com.caucho.hessian.io.*;

import javax.naming.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

/**
 * hessian客户端工厂类
 *
 * @author <a href="mailto:zhongc@59store.com">士兵</a>
 * @version 1.0 15/12/29
 * @since 1.0
 */
public class KylinHessianProxyFactory extends HessianProxyFactory {

    private String userAgent;
    private boolean systemProperties;
    private boolean contentCompressionDisabled;
    private int maxConnTotal = 400;
    private int maxConnPerRoute = 400;

    /**
     * 创建一个代理工厂
     */
    public KylinHessianProxyFactory() {
        super();
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
        return new HessianHttpClientFactory();
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

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public boolean isSystemProperties() {
        return systemProperties;
    }

    public void setSystemProperties(boolean systemProperties) {
        this.systemProperties = systemProperties;
    }

    public boolean isContentCompressionDisabled() {
        return contentCompressionDisabled;
    }

    public void setContentCompressionDisabled(boolean contentCompressionDisabled) {
        this.contentCompressionDisabled = contentCompressionDisabled;
    }

    public int getMaxConnTotal() {
        return maxConnTotal;
    }

    public void setMaxConnTotal(int maxConnTotal) {
        this.maxConnTotal = maxConnTotal;
    }

    public int getMaxConnPerRoute() {
        return maxConnPerRoute;
    }

    public void setMaxConnPerRoute(int maxConnPerRoute) {
        this.maxConnPerRoute = maxConnPerRoute;
    }
}
