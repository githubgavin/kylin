package com.store59.kylin.api.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.store59.kylin.api.viewmodel.Result;
import com.store59.kylin.common.exception.BaseException;

@ControllerAdvice
public class ServiceExceptionHandler {
	static Logger logger = Logger.getLogger("ServiceExceptionHandler");

	/**
	 * 全局异常捕获，返回异常状态及信息 TODO 异常日志纪录
	 * 
	 * @param req
	 *            发生异常的请求
	 * @param ex
	 *            发生的异常
	 * @return 已知异常返回定义的状态码和信息，未知异常返回状态码－1和异常信息
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Result handleBadRequest(HttpServletRequest req, Exception ex) {
		Result r = new Result();
		System.out.println(ex.getClass());
		if (ex instanceof BaseException) {
			BaseException e = (BaseException) ex;
			r.setStatus(e.getStatus());
			r.setMsg(e.getMsg());
		} else {
			r.setStatus(-1);
			r.setMsg(ex.getMessage());
		}
		logger.error(r.getMsg());
		return r;
	}

}
