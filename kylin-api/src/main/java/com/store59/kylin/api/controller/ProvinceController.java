package com.store59.kylin.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.store59.kylin.api.viewmodel.Result;
import com.store59.kylin.location.data.service.ProvinceService;

@RestController
public class ProvinceController {
	@Autowired
	private ProvinceService provinceService;

	@RequestMapping(value = "/province/list", method = RequestMethod.GET)
	public Object info(HttpServletRequest request) {
		Map<String,Object> data = new HashMap<>();
		Object list = provinceService.getProvinceList();
		data.put("provinces", list);
		Result result = new Result();
		result.setData(data);
		return result;
	}

}
