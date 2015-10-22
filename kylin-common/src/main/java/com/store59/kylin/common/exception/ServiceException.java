/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.kylin.common.exception;

@SuppressWarnings("serial")
public class ServiceException extends BaseException {

    public ServiceException() {

    }

    public ServiceException(int status, String msg) {
        super(status, msg);
    }

}
