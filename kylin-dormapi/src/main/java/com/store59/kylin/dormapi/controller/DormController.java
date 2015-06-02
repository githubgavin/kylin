package com.store59.kylin.dormapi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.store59.kylin.dorm.data.model.Dorm;
import com.store59.kylin.dorm.service.DormService;
import com.store59.kylin.dormapi.exception.ServiceException;
import com.store59.kylin.dormapi.logic.UserToken;
import com.store59.kylin.dormapi.viewmodel.DormView;
import com.store59.kylin.dormapi.viewmodel.Result;

@RestController
public class DormController {
	@Autowired
	private DormService dormService;

	@RequestMapping(value = "/dorm/info", method = RequestMethod.GET)
	public Object getDormInfo(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute("usertoken");
		if (obj == null || !(obj instanceof UserToken)) {
			throw new ServiceException(2, "invalid token");
		}
		UserToken token = (UserToken) obj;
		Dorm dorm = dormService.getDormByUid(token.getUserId());
		if (dorm == null) {
			throw new ServiceException(1000, "不存在该楼主");
		}
		int dormStatus = dorm.getStatus().intValue();
		if (dormStatus == 1) {
			throw new ServiceException(1001, "楼主账号已被删除");
		} else if (dormStatus == 2) {
			throw new ServiceException(1002, "楼主账号已被限制登录");
		} else if (dormStatus != 0) {
			throw new ServiceException(-1);
		}
		Result result = new Result();
		result.setData(new DormView(dorm));
		result.UpdateToken(token);
		return result;
	}

	@RequestMapping(value = "/dorm/updateinfo", method = RequestMethod.POST)
	public Object updateDormInfo(HttpServletRequest request, Integer dorm_id,
			String delivery_address, String zhifubao) {
		Object obj = request.getSession().getAttribute("usertoken");
		if (obj == null || !(obj instanceof UserToken)) {
			throw new ServiceException(2, "invalid token");
		}
		UserToken token = (UserToken) obj;
		Dorm dorm = dormService.getDormByUid(token.getUserId());
		if (dorm == null || !dorm.getDormId().equals(dorm_id)) {
			throw new ServiceException(1000, "不存在该楼主");
		}
		Dorm updateDorm = new Dorm();
		updateDorm.setDormId(dorm_id);
		updateDorm.setAliAccount(zhifubao);
		updateDorm.setDeliveryAddress(delivery_address);
		Boolean status = dormService.updateDorm(updateDorm);
		Map<String, Object> data = new HashMap<String, Object>();
		if (status) {
			data.put("status", 1);
		} else {
			data.put("status", 0);
		}
		Result result = new Result();
		result.setData(data);
		result.UpdateToken(token);
		return result;
	}
}
