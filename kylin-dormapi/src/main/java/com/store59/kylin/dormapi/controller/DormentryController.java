package com.store59.kylin.dormapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.store59.kylin.dorm.data.model.Dormentry;
import com.store59.kylin.dorm.service.DormentryService;
import com.store59.kylin.dormapi.exception.ServiceException;
import com.store59.kylin.dormapi.logic.DormLogic;
import com.store59.kylin.dormapi.logic.UserToken;
import com.store59.kylin.dormapi.viewmodel.DormentryView;
import com.store59.kylin.dormapi.viewmodel.Result;

@RestController
public class DormentryController {
	@Autowired
	private DormentryService dormentryService;
	@Autowired
	private DormLogic dormLogic;

	@RequestMapping(value = "/dormentry/list", method = RequestMethod.GET)
	public Object getDormentryList(HttpServletRequest request, Integer dorm_id) {
		Object obj = request.getSession().getAttribute("usertoken");
		if (obj == null || !(obj instanceof UserToken)) {
			throw new ServiceException(2, "empty token");
		}
		UserToken token = (UserToken) obj;
		dormLogic.checkDormId(dorm_id, token);
		List<Dormentry> dormentries = dormentryService
				.getDormentryList(dorm_id);
		List<DormentryView> rlist = new ArrayList<>();
		for (Dormentry dormentry : dormentries) {
			rlist.add(new DormentryView(dormentry));
		}
		Map<String, Object> data = new HashMap<>();
		data.put("dormentries", rlist);
		Result result = new Result();
		result.setData(data);
		result.UpdateToken(token);
		return result;
	}

}
