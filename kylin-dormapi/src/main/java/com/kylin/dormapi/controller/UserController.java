package com.kylin.dormapi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kylin.dormapi.exception.ServiceException;
import com.kylin.dormapi.logic.UserToken;
import com.kylin.dormapi.viewmodel.Result;
import com.kylin.dormapi.viewmodel.UserView;
import com.kylin.user.data.model.User;
import com.kylin.user.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public Object login(HttpServletRequest request, String username,
			String password) throws ServiceException {
		Object obj = request.getSession().getAttribute("usertoken");
		if (obj == null || !(obj instanceof UserToken)) {
			throw new ServiceException(2, "empty token");
		}
		UserToken token = (UserToken) obj;
		User user = userService.getUserForLogin(username, password);
		Map<String, Object> data = new HashMap<String, Object>();
		Result result = new Result();
		if (user == null) {
			data.put("status", 0);
		} else {
			token.setUserId(user.getUid());
			data.put("status", 1);
			result.setToken(UserToken.getTokenString(token));
		}
		result.setData(data);
		return result;
	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.POST)
	public Object logout(HttpServletRequest request) throws ServiceException {
		Object obj = request.getSession().getAttribute("usertoken");
		if (obj == null || !(obj instanceof UserToken)) {
			throw new ServiceException(2, "empty token");
		}
		UserToken token = (UserToken) obj;
		token.setUserId(null);
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("status", 1);
		Result result = new Result();
		result.setToken(UserToken.getTokenString(token));
		result.setData(data);
		return result;
	}

	@RequestMapping(value = "/user/info", method = RequestMethod.POST)
	public Object info(HttpServletRequest request) throws ServiceException {
		Object obj = request.getSession().getAttribute("usertoken");
		if (obj == null || !(obj instanceof UserToken)) {
			throw new ServiceException(2, "empty token");
		}
		UserToken token = (UserToken) obj;
		User user = userService.getUser(token.getUserId());
		Result result = new Result();
		result.setData(new UserView(user));
		result.UpdateToken(token);
		return result;
	}
}
