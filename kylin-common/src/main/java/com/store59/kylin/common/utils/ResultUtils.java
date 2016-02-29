/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.kylin.common.utils;

import com.store59.kylin.common.model.Result;

/**
 * 结果对象工具类.
 * 
 * @author <a href="mailto:zhuzm@59store.com">天河</a>
 * @version 1.0 2016年2月29日
 * @since 1.0
 */
public abstract class ResultUtils {

    /**
     * 判断业务处理是否成功.
     * 
     * @param result {@link Result}
     * @return true：处理成功，false：处理失败
     */
    public static <T> boolean isSuccess(Result<T> result) {
        if (result == null || result.getStatus() != 0) {
            return false;
        }

        return true;
    }

}
