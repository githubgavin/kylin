package com.store59.kylin.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.store59.kylin.api.viewmodel.Result;
import com.store59.kylin.user.data.model.User;
import com.store59.kylin.user.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping(value = "/user/info", method = RequestMethod.GET)
	public Object info(HttpServletRequest request, Integer uid) {
		User user = userService.getUser(uid);
		Result result = new Result();
		result.setData(user);
		return result;
	}

}
