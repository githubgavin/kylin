package com.kylin.dormapi.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.kylin.common.Util;
import com.kylin.dormapi.viewmodel.Result;

public class MyExceptionHandler implements HandlerExceptionResolver {
	static Logger logger = Logger.getLogger("MyExceptionHandler");

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView result = new ModelAndView("error");
		Result r = new Result();
		if (ex instanceof ServiceException) {
			ServiceException e = (ServiceException) ex;
			r.setStatus(e.getStatus());
			r.setMsg(e.getMsg());
		} else {
			r.setStatus(-1);
			r.setMsg(ex.getMessage());
		}
		logger.error(r.getMsg());
		String error = Util.getJsonFromObject(r);
		result.addObject("error", error);
		return result;
	}
}
