package com.store59.kylin.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.store59.kylin.api.viewmodel.Result;

@RestController
public class OrderController {
	@RequestMapping(value = "/order/hello", method = RequestMethod.GET)
	public Object hello(HttpServletRequest request){
		Result result = new Result();
		result.setData("hello");
		return result;
	}
}
