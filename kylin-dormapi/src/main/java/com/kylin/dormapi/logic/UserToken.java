package com.kylin.dormapi.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kylin.common.DESUtil;
import com.kylin.common.Util;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class UserToken {
	private Integer userId;
	private long endTime;
	private static int OUT_TIME = 1000 * 60 * 60 * 24 * 2;

	public UserToken() {
		this.setDefaultEndTime();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	@JsonIgnore
	public boolean isValid() {
		return System.currentTimeMillis() < endTime;
	}

	@JsonIgnore
	public void setDefaultEndTime() {
		this.endTime = System.currentTimeMillis() + OUT_TIME;
	}

	private static String TOKEN_KEY = "38d3fd30050211e5b5d4985aeb89a1ce";

	public static UserToken createToken(String token) {
		UserToken result = null;
		try {
			String json = DESUtil.decrypt(token, TOKEN_KEY);
			result = Util.getObjectFromJson(json, UserToken.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static String getTokenString(UserToken userToken) {
		String result = null;
		try {
			String json = Util.getJsonFromObject(userToken);
			result = DESUtil.encrypt(json, TOKEN_KEY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
