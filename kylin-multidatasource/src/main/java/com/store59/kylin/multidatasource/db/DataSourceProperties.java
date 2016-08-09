/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.multidatasource.db;

/**
 * DB连接的基本属性
 *
 * @author <a href="mailto:zhongc@59store.com">士兵</a>
 * @version 1.0 16/7/14
 * @since 1.0
 */
public abstract class DataSourceProperties {
    /**
     * 连接地址
     */
    protected String host;

    /**
     * 端口号
     */
    protected int port;

    /**
     * 数据库名
     */
    protected String name;

    /**
     * 用户名
     */
    protected String username;

    /**
     * 密码
     */
    protected String password;

    protected int maxActive = 20;
    protected int minIdle = 2;
    protected int maxWait = 10 * 1000;
    protected int validationInterval = 30000;
    protected int validationQueryTimeout = 5;
    protected int timeBetweenEvictionRunsMillis = 60 * 1000;
    protected int minEvictableIdleTimeMillis = 300000;
    protected int initialSize = 1;
    protected int removeAbandonedTimeout = 60;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getValidationInterval() {
        return validationInterval;
    }

    public void setValidationInterval(int validationInterval) {
        this.validationInterval = validationInterval;
    }

    public int getValidationQueryTimeout() {
        return validationQueryTimeout;
    }

    public void setValidationQueryTimeout(int validationQueryTimeout) {
        this.validationQueryTimeout = validationQueryTimeout;
    }

    public int getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout;
    }

    public void setRemoveAbandonedTimeout(int removeAbandonedTimeout) {
        this.removeAbandonedTimeout = removeAbandonedTimeout;
    }
}
