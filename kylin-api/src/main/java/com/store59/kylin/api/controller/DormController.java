package com.store59.kylin.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.store59.kylin.api.viewmodel.Result;
import com.store59.kylin.dorm.data.model.Dorm;
import com.store59.kylin.dorm.service.DormService;

@RestController
public class DormController {
	@Autowired
	private DormService dormService;

	@RequestMapping(value = "/dorm/info", method = RequestMethod.GET)
	public Object info(HttpServletRequest request, Integer dorm_id, Long uid) {
		Dorm dorm = null;
		if (dorm_id != null) {
			dorm = dormService.getDorm(dorm_id);
		} else {
			dorm = dormService.getDormByUid(uid);
		}
		Result result = new Result();
		result.setData(dorm);
		return result;
	}
}
