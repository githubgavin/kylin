package com.store59.kylin.aspect;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.store59.kylin.dormapi.exception.ServiceException;
import com.store59.kylin.dormapi.logic.UserToken;

@Aspect
@Component
public class ControllerAspect {
	private static Logger logger = Logger.getLogger("controller");
	static Boolean DEUBG_TOKEN = false;

	@Before("execution(* com.store59.kylin.dormapi.controller.*.*(javax.servlet.http.HttpServletRequest, ..))")
	public void preController(JoinPoint point) throws ServiceException {
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

			String token = request.getParameter("token");
			if (token != null) {
				token = token.replace(" ", "+");
				UserToken userToken = UserToken.createToken(token);
				if (userToken == null || !userToken.isValid()) {
					throw new ServiceException(2, "invalid token");
				}
				request.getSession().setAttribute("usertoken", userToken);
			} else if (!(DEUBG_TOKEN || request.getRequestURI().contains(
					"/token/new"))) {
				throw new ServiceException(2, "empty token");
			}
		}
	}
}
