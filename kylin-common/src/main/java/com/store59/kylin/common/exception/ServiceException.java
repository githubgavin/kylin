package com.store59.kylin.common.exception;

@SuppressWarnings("serial")
public class ServiceException extends BaseException {
	public ServiceException() {

	}

	public ServiceException(int status, String msg) {
		this.setStatus(status);
		this.setMsg(msg);
	}
}
