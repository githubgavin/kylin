package com.store59.kylin.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.store59.kylin.api.viewmodel.Result;
import com.store59.kylin.common.exception.ServiceException;
import com.store59.kylin.dorm.data.model.Dormentry;
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

	@RequestMapping(value = "/dormentry/list", method = RequestMethod.GET)
	public Object getList(HttpServletRequest request, Integer dorm_id) {
		Object lists = dormentryService.getDormentryList(dorm_id);
		Map<String, Object> map = new HashMap<>();
		map.put("dormentries", lists);
		Result result = new Result();
		result.setData(map);
		return result;
	}

	@RequestMapping(value = "/dormentry/setstatus", method = RequestMethod.POST)
	public Object setStatus(HttpServletRequest request, Integer dormentry_id,Integer dorm_id,
			Byte status) {
		Dormentry dormentry = dormentryService.getDormentry(dormentry_id);
		if (dormentry == null) {
			throw new ServiceException(3, "请求参数有误");
		}
		if (dormentry.getStatus() == (byte) 1
				&& !dormentry.getDormId().equals(dorm_id)) {
			throw new ServiceException(1300, "其他楼主正在营业,不允许开关店");
		}
		dormentry = new Dormentry();
		dormentry.setDormentryId(dormentry_id);
		dormentry.setStatus(status);
		dormentry.setDormId(dorm_id);
		Boolean updateStatus = dormentryService.updateDormentry(dormentry);
		Map<String, Object> data = new HashMap<String, Object>();
		if (updateStatus) {
			data.put("status", 1);
		} else {
			data.put("status", 0);
		}
		Result result = new Result();
		result.setData(data);
		return result;
	}
}
