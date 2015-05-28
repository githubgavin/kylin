package com.store59.kylin.dormapi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kylin.dormapi.exception.ServiceException;
import com.kylin.dormapi.logic.UserToken;

public class MyInterceptor extends HandlerInterceptorAdapter {
	static Boolean DEUBG_TOKEN = false;

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

		String token = arg0.getParameter("token");
		if (token != null) {
			UserToken userToken = UserToken.createToken(token);
			if (userToken == null || !userToken.isValid()) {
				throw new ServiceException(2, "invalid token");
			}
			arg0.getSession().setAttribute("usertoken", userToken);
		} else if (!(DEUBG_TOKEN || arg0.getRequestURI().contains("/token/new"))) {
			throw new ServiceException(2, "empty token");
		}

		return super.preHandle(arg0, arg1, arg2);
	}

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		arg0.getSession().removeAttribute("usertoken");
	}
}
