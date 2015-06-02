package com.store59.kylin.dormapi.viewmodel;

import com.store59.kylin.dormapi.logic.UserToken;

public class Result {
	private int status;
	private String msg;
	private Object data;
	private String token;

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void UpdateToken(UserToken userToken) {
		long nowTime = System.currentTimeMillis();
		long limitTime = nowTime + 1000 * 60 * 60 * 24;
		if (userToken.getEndTime() > nowTime
				&& userToken.getEndTime() < limitTime) {
			userToken.setDefaultEndTime();
			this.token = UserToken.getTokenString(userToken);
		}
	}

}
