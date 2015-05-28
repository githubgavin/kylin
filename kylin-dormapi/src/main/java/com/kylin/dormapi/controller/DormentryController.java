package com.kylin.dormapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kylin.dorm.data.model.Dormentry;
import com.kylin.dorm.service.DormentryService;
import com.kylin.dormapi.viewmodel.DormentryView;
import com.kylin.dormapi.viewmodel.Result;

@RestController
public class DormentryController {
	@Autowired
	private DormentryService dormentryService;

	@RequestMapping(value = "/dormentry/list", method = RequestMethod.GET)
	public Object getDormentryList(HttpServletRequest request, Integer dorm_id) {
		List<Dormentry> dormentries = dormentryService.getDormentryList(dorm_id);
		List<DormentryView> rlist = new ArrayList<>();
		for(Dormentry dormentry:dormentries){
			rlist.add(new DormentryView(dormentry));
		}
		Map<String,Object> data = new HashMap<>();
		data.put("dormentries", rlist);
		Result result = new Result();
		result.setData(data);
		return result;
	}

}
