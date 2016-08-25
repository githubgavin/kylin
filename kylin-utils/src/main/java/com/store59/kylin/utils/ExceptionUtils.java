/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.utils;

import com.store59.kylin.common.exception.BaseException;
import com.store59.kylin.common.model.Result;

/**
 * 异常工具类
 * 用于传递远程调用的异常
 *
 * @author <a href="mailto:zhongc@59store.com">士兵</a>
 * @version 1.0 16/8/24
 * @since 1.0
 */
public class ExceptionUtils {

    public static BaseException genWithEnum(EnumMsg<Integer> enumMsg) {
        return new BaseException(enumMsg.getCode(), enumMsg.getMsg());
    }

    public static BaseException genWithResult(Result result) {
        if(result != null) {
            return new BaseException(result.getStatus(), result.getMsg());
        } else {
            return new BaseException(-1, "invoke hessian error");
        }
    }
}
