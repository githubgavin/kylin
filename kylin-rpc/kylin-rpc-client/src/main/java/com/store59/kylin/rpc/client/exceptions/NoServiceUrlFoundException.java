/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.client.exceptions;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/6/14
 * @since 2.1
 */
public class NoServiceUrlFoundException extends RuntimeException {

    private static final long serialVersionUID = -9019269915531937656L;

    public NoServiceUrlFoundException() {
        super();
    }

    public NoServiceUrlFoundException(String message) {
        super(message);
    }

    public NoServiceUrlFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoServiceUrlFoundException(Throwable cause) {
        super(cause);
    }

}
