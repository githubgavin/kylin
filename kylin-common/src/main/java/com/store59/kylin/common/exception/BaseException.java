package com.store59.kylin.common.exception;

@SuppressWarnings("serial")
public class BaseException extends RuntimeException {
    private int status;
    private String msg;

    public BaseException() {
        this(-1);
    }

    public BaseException(int status) {
        this(status, null);
    }

    public BaseException(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(int status, String msg, Throwable cause) {
        super(cause);
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        if (msg != null) {
            return msg;
        } else {
            return getMessage();
        }
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
