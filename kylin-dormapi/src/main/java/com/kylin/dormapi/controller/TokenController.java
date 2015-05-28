package com.kylin.dormapi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kylin.dormapi.logic.UserToken;
import com.kylin.dormapi.viewmodel.Result;

@RestController
public class TokenController {
	@RequestMapping(value = "/token/new", method = RequestMethod.POST)
	public Object create(HttpServletRequest request, String device_id) {
		UserToken userToken = new UserToken();
		String token = UserToken.getTokenString(userToken);
		Map<String, Object> data = new HashMap<>();
		data.put("token", token);
		Result result = new Result();
		result.setData(data);
		return result;
	}

}
