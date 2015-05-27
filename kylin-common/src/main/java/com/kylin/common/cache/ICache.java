package com.kylin.common.cache;

public interface ICache {
	String get(String key);

	String set(String key, String value);

	String setex(String key, int seconds, String value);

	Boolean exists(String key);

	Long del(String key);
}
