/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.utils;

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
     * 判断结果是否成功.
     * 
     * @param result {@link Result}
     * @return true：成功，false：失败
     */
    public static <T> boolean isSuccess(Result<T> result) {
        return result != null && result.isSuccess();
    }

    /**
     * 将枚举信息转为Result对象.
     * 
     * @param enumMsg {@link EnumMsg}
     * @return {@link Result}
     */
    public static <T> Result<T> toResult(EnumMsg<Integer> enumMsg) {
        return toResult(enumMsg, null);
    }

    /**
     * 将枚举信息转为Result对象.
     * 
     * @param enumMsg {@link EnumMsg}
     * @param data 数据
     * @return {@link Result}
     */
    public static <T> Result<T> toResult(EnumMsg<Integer> enumMsg, T data) {
        Result<T> result = new Result<>();
        if (enumMsg != null) {
            result.setStatus(enumMsg.getCode());
            result.setMsg(enumMsg.getMsg());
        }

        result.setData(data);
        return result;
    }

}
