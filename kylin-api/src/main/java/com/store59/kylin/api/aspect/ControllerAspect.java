package com.store59.kylin.api.aspect;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAspect {
	private static Logger logger = Logger.getLogger("controller");

	@Before("execution(* com.store59.kylin.api.controller.*.*(javax.servlet.http.HttpServletRequest, ..))")
	public void preController(JoinPoint point) {
		Object[] args = point.getArgs();
		if (args.length > 0 && args[0] instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) args[0];
			StringBuilder sb = new StringBuilder();
			sb.append(request.getMethod());
			sb.append(" ");
			sb.append(request.getRequestURI());
			String query = request.getQueryString();
			if (query != null && query.length() > 0) {
				sb.append("?");
				sb.append(query);
			}
			logger.info(sb.toString());
		}
	}
}
