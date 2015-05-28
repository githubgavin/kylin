package com.kylin.dormapi.exception;

@SuppressWarnings("serial")
public class ServiceException extends Exception {
	private int status;
	private String msg;

	public ServiceException() {
		this(-1);
	}

	public ServiceException(int status) {
		this(status, null);
	}

	public ServiceException(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public String getMsg() {
		if (msg != null) {
			return msg;
		} else {
			return getMessage();
		}
	}
}
