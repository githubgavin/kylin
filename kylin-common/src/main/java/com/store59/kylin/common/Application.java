package com.store59.kylin.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath:applicationContext_common.xml")
public class Application {
	static ApplicationContext ctx = null;

	/**
	 * 获取spring context,可以用于测试用例
	 * 
	 * @return
	 */
	public static ApplicationContext getContext() {
	    return null;
	}
}
