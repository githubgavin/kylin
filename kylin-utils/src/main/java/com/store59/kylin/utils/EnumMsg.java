/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.utils;

/**
 * 枚举信息.
 * 
 * @author <a href="mailto:zhuzm@59store.com">天河</a>
 * @version 1.0 2016年3月25日
 * @since 1.0
 */
public interface EnumMsg<T> extends EnumCode<T> {

    /**
     * 获取枚举信息.
     * 
     * @return 枚举信息
     */
    public String getMsg();

}
