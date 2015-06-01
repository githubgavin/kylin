package com.store59.kylin.dormapi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MyInterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(arg0.getMethod());
		sb.append(" ");
		sb.append(arg0.getRequestURI());
		String query = arg0.getQueryString();
		if (query != null && query != "") {
			sb.append("?");
			sb.append(query);
		}
		Logger logger = Logger.getLogger("controller");
		logger.info(sb.toString());
		return super.preHandle(arg0, arg1, arg2);
	}
}
