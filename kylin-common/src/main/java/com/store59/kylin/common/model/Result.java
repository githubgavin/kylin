/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.kylin.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.store59.kylin.common.dto.BaseDTO;

/**
 * 公用返回值结构
 *
 * @author heqingpan
 *
 * @param <T>
 */
public class Result<T> extends BaseDTO {
    private static final long serialVersionUID = -4159843077341007128L;
    public static final int   SUCCESS          = 0;

    private int               status;
    private String            msg;
    private T                 data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 判断结果是否成功.
     *
     * @return true：成功，false：失败
     */
    @JsonIgnore
    public boolean isSuccess() {
        return getStatus() == SUCCESS;
    }

}
