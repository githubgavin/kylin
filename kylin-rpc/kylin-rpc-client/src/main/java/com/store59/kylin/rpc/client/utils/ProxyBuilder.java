/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.client.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.remoting.caucho.HessianClientInterceptor;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.store59.kylin.cloud.client.support.rpc.KylinCloudServiceUrlSelector;
import com.store59.kylin.context.SpringContext;
import com.store59.kylin.rpc.client.KylinHessianProxyFactoryBean;
import com.store59.kylin.rpc.client.ServiceUrlSelector;
import com.store59.kylin.rpc.protocol.hessian.KylinHessianProxyFactory;

/**
 *
 * 使用方法:
 *
 * 创建普通服务代理类:
 *
 * ProxyBuilder.create().setServiceUrl("http://localhost:8080/exampleservice").setServiceExportName("hello").setClazz(HelloService.class).build()
 *
 * 创建http连接池代理类:
 *
 * ProxyBuilder.create()
 *             .useHttpClient()
 *             .setServiceUrl("http://localhost:8080/exampleservice")
 *             .setServiceExportName("hello")
 *             .setClazz(HelloService.class)
 *             .setMaxConnTotal(400)
 *             .build();
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/05/13
 * @since 2.1
 */
public class ProxyBuilder {
    private static final String HTTP                  = "http";
    private static int DEFAULT_TIMEOUT_MS = 3000;
    private static int DEFAULT_POOL_MAX_CONN = 400;
//    /**
//     * 用于连接池链接RPCServer的个数, 默认为1, 目前均用aliyun的SLB做负载均衡
//     * kylin2.1版会引入client端负载均衡, 这个值会有所变化
//     *
//     * */
//    private static int DEFAULT_NUM_OF_SERVER_URL = 1;
    private boolean useHttpClient = false;
    private String userAgent;
    private boolean systemProperties;
    private boolean contentCompressionDisabled;
    private int maxConnTotal = DEFAULT_POOL_MAX_CONN;
//    private int maxConnPerRoute = DEFAULT_POOL_MAX_CONN/DEFAULT_NUM_OF_SERVER_URL;
    private long connectTimeout = DEFAULT_TIMEOUT_MS;
    private long readTimeout = DEFAULT_TIMEOUT_MS;
    private String serviceUrl;
    private String serviceExportName;
    private Class interfaceClass;

    /**
     *
     * 创建ProxyBuilder
     *
     */
    public static ProxyBuilder create() {
        return new ProxyBuilder();
    }

    protected static void fillProperties(HessianClientInterceptor hessianClientInterceptor, String serviceUrl, String serviceExportName, Class interfaceClass) {
        hessianClientInterceptor.setOverloadEnabled(true);
        hessianClientInterceptor.setServiceInterface(interfaceClass);
        if (!serviceUrl.endsWith("/")) {
            serviceUrl = serviceUrl + "/";
        }
        hessianClientInterceptor.setServiceUrl(serviceUrl + serviceExportName);
    }

    private void check() {
        if (serviceUrl == null) {
            throw new IllegalArgumentException("Property 'serviceUrl' is required");
        }
        if (serviceExportName == null) {
            throw new IllegalArgumentException("Property 'serviceExportName' is required");
        }
        if (interfaceClass == null) {
            throw new IllegalArgumentException("Property 'interfaceClass' is required");
        }
    }

    /**
     *
     * 创建远程服务代理对象
     *
     */
    public <T> T build() {
        check();
        if (StringUtils.startsWithIgnoreCase(serviceUrl, HTTP)) {
            return this.buildNormalHPFBean();
        } else {
            return this.buildKylinHPFBean();
        }
    }

    private <T> T buildNormalHPFBean() {
        HessianProxyFactoryBean hessianProxyFactoryBean = new HessianProxyFactoryBean();
        fillProperties(hessianProxyFactoryBean, serviceUrl, serviceExportName, interfaceClass);
        initHessianClientInterceptor(hessianProxyFactoryBean);
        return (T) hessianProxyFactoryBean.getObject();
    }

    private <T> T buildKylinHPFBean() {
        ServiceUrlSelector serviceUrlSelector = (ServiceUrlSelector) SpringContext.getApplicationContext().getBean(KylinCloudServiceUrlSelector.BEAN_NAME);
        KylinHessianProxyFactoryBean hessianProxyFactoryBean = new KylinHessianProxyFactoryBean();
        hessianProxyFactoryBean.setOverloadEnabled(true);
        hessianProxyFactoryBean.setExportName(serviceExportName);
        hessianProxyFactoryBean.setServiceName(serviceUrl);
        hessianProxyFactoryBean.setServiceInterface(interfaceClass);
        hessianProxyFactoryBean.setServiceUrlSelector(serviceUrlSelector);
        initHessianClientInterceptor(hessianProxyFactoryBean);
        return (T) hessianProxyFactoryBean.getObject();
    }

    private void initHessianClientInterceptor(HessianClientInterceptor interceptor) {
        if (useHttpClient) {
            KylinHessianProxyFactory hessianProxyFactory = new KylinHessianProxyFactory();
            hessianProxyFactory.setOverloadEnabled(true);
            hessianProxyFactory.setUserAgent(userAgent);
            hessianProxyFactory.setSystemProperties(systemProperties);
            hessianProxyFactory.setContentCompressionDisabled(contentCompressionDisabled);
            hessianProxyFactory.setMaxConnPerRoute(maxConnTotal);
            hessianProxyFactory.setMaxConnTotal(maxConnTotal);
            // Set proxyFactory
            interceptor.setProxyFactory(hessianProxyFactory);
        }
        interceptor.setConnectTimeout(connectTimeout);
        interceptor.setReadTimeout(readTimeout);
        interceptor.afterPropertiesSet();
    }

    /**
     *
     * 使用httpclient替代原生httpconnector, 开启http连接池功能
     *
     */
    public ProxyBuilder useHttpClient() {
        useHttpClient = true;
        return this;
    }

    /**
     *
     * 不使用httpclient替代原生httpconnector, 无http连接池功能
     *
     */
    public ProxyBuilder noUseHttpClient() {
        useHttpClient = false;
        return this;
    }

    /**
     *
     * 注意: 该属性只有在useHttpClient=true时才有效
     * 对应httpclient中setUserAgent属性设置
     *
     */
    public ProxyBuilder setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    /**
     *
     * 注意: 该属性只有在useHttpClient=true时才有效
     * 对应httpclient中setSystemProperties属性设置
     *
     */
    public ProxyBuilder setSystemProperties(boolean systemProperties) {
        this.systemProperties = systemProperties;
        return this;
    }

    /**
     *
     * 注意: 该属性只有在useHttpClient=true时才有效
     * 对应httpclient中contentCompressionDisabled属性设置
     *
     */
    public ProxyBuilder setContentCompressionDisabled(boolean contentCompressionDisabled) {
        this.contentCompressionDisabled = contentCompressionDisabled;
        return this;
    }

    /**
     *
     * 注意: 该属性只有在useHttpClient=true时才有效
     * 对应httpclient中setMaxConnTotal属性设置
     *
     */
    public ProxyBuilder setMaxConnTotal(int maxConnTotal) {
        this.maxConnTotal = maxConnTotal;
        return this;
    }

//    /**
//     *
//     * 注意: 该属性只有在useHttpClient=true时才有效
//     * 对应httpclient中setMaxConnPerRoute属性设置
//     *
//     */
//    public ProxyBuilder setMaxConnPerRoute(int maxConnPerRoute) {
//        this.maxConnPerRoute = maxConnPerRoute;
//        return this;
//    }

    /**
     * 服务名和属性serverURL冲突, 默认serviceUrl优先
     *
     * @deprecated replace by {@link #setServiceUrl(String)}
     */
    @Deprecated
    public ProxyBuilder setServiceName(String serviceName) {
        this.setServiceUrl(serviceName);
        return this;
    }

    /**
     * serviceUrl支持：
     * <ol>
     * <li>服务名，支持负载均衡，推荐使用</li>
     * <li>正常URL（以http开头），不支持负载均衡，通常用于测试使用</li>
     * </ol>
     */
    public ProxyBuilder setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
        return this;
    }

    /**
     *
     * RPC暴露的服务名, 设置成@RemotingService中的exportPath
     *
     */
    public ProxyBuilder setServiceExportName(String serviceExportName) {
        this.serviceExportName = serviceExportName;
        return this;
    }

    public ProxyBuilder setInterfaceClass(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
        return this;
    }

    public ProxyBuilder setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public ProxyBuilder setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

//    @Deprecated
//    public static Object buildProxyService(String serviceUrl, String serviceExportName, Class clazz) {
//        return ProxyBuilder.create().setServiceExportName(serviceExportName).setServiceUrl(serviceUrl).setInterfaceClass(clazz).build();
//    }

}
