/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.multidatasource.db;

/**
 *
 * @author <a href="mailto:zhongc@59store.com">士兵</a>
 * @version 1.0 16/8/7
 * @since 1.0
 */
public class DBLink extends DataSourceProperties {

    private String key;

    /**
     * mapper的路径
     */
    private String mapperPath;

    /**
     * 是否支持事务, 默认不支持
     */
    private Boolean transactionAble;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMapperPath() {
        return mapperPath;
    }

    public void setMapperPath(String mapperPath) {
        this.mapperPath = mapperPath;
    }

    public Boolean getTransactionAble() {
        return transactionAble;
    }

    public void setTransactionAble(Boolean transactionAble) {
        this.transactionAble = transactionAble;
    }
}
