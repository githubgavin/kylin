package com.store59.kylin.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.store59.kylin.api.viewmodel.Result;
import com.store59.kylin.dorm.service.DormentryService;

@RestController
public class DormentryController {
	@Autowired
	private DormentryService dormentryService;

	@RequestMapping(value = "/dormentry/info", method = RequestMethod.GET)
	public Object info(HttpServletRequest request, Integer dormentry_id) {
		Object data = dormentryService.getDormentry(dormentry_id);
		Result result = new Result();
		result.setData(data);
		return result;
	}
}
