package com.store59.kylin.dormapi.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.store59.kylin.dormapi.exception.ServiceException;
import com.store59.kylin.dormapi.logic.UserToken;
import com.store59.kylin.dormapi.viewmodel.Result;
import com.store59.kylin.dormapi.viewmodel.UserView;
import com.store59.kylin.user.data.model.User;
import com.store59.kylin.user.service.UserService;

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
		Result result = new Result();
		if (user != null) {
			token.setUserId(user.getUid());
			result.setToken(UserToken.getTokenString(token));
		}
		result.setData(new UserView(user));
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

	@RequestMapping(value = "/user/info", method = RequestMethod.GET)
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
