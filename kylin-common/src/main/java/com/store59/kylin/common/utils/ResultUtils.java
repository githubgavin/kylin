/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.kylin.common.utils;

import com.store59.kylin.common.model.Result;
/**
 * 结果对象工具类.
 * 该类移动到kylin-utils中
 *
 * @author <a href="mailto:zhuzm@59store.com">天河</a>
 * @version 2.1 2016年2月29日
 * @since 2.1
 */
@Deprecated
public abstract class ResultUtils {

    /**
     * 判断结果是否成功.
     *
     * @param result {@link Result}
     * @return true：成功，false：失败
     */
    public static <T> boolean isSuccess(Result<T> result) {
        return result != null && result.isSuccess();
    }

}
