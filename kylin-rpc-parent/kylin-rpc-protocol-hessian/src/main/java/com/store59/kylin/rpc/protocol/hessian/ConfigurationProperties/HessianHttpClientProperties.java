/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.protocol.hessian.ConfigurationProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 16/7/21
 * @since 1.0
 */
@ConfigurationProperties(prefix = "kylin.rpc.client")
@Component(HessianHttpClientProperties.BEAN_NAME)
public class HessianHttpClientProperties {
    public final static String BEAN_NAME = "hessianHttpClientProperties";
    public static int DEFAULT_TIMEOUT_MS              = 3000;
    public static int DEFAULT_POOL_MAX_CONN           = 400;
    public static int DEFAULT_POOL_MAX_CONN_PER_ROUTE = 50;

    private String  userAgent;
    private boolean systemProperties;
    private boolean contentCompressionDisabled;
    private int  maxConnTotal    = DEFAULT_POOL_MAX_CONN; // 最大连接数
    private int  maxConnPerRoute = DEFAULT_POOL_MAX_CONN_PER_ROUTE; // 每个route最大连接数, route这里表示连接的服务ip数
    private int connectTimeout  = DEFAULT_TIMEOUT_MS;
    private int readTimeout     = DEFAULT_TIMEOUT_MS;

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

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
}
