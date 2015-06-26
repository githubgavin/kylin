package com.store59.kylin.common;

import java.util.Calendar;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
	private static ObjectMapper JSON = new ObjectMapper();

	public static String getJsonFromObject(Object obj) {
		try {
			return JSON.writeValueAsString(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
	}

	public static <T> T getObjectFromJson(String json, Class<T> valueType) {
		try {
			return JSON.readValue(json, valueType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
	}

	public static <T> T getObjectFromJson(String json,
			TypeReference<T> valueTupeRef) {
		try {
			return JSON.readValue(json, valueTupeRef);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 简单验证手机号码的格式及是否存在
	 * 
	 * @param phone
	 *            待验证的手机号码
	 * @return 手机号码格式正确且存在一定返回true. 手机号码格式错误返回false, 手机号码不存在极大概率返回false
	 */
	public static boolean isPhoneNumber(String phone) {
		String phoneRegex = "1[34578]\\d{9}";
		return phone.matches(phoneRegex);
	}

	/**
	 * 根据Unix时间戳计算那一天开始的时间戳
	 * 
	 * @param time
	 *            给定任意一点时间, 精确到毫秒
	 * @return 那一天开始的时间, 精确到秒
	 */
	public static int getStartTimeOfOneDay(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return (int) (cal.getTimeInMillis() / 1000);
	}
}
