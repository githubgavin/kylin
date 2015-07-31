package com.store59.kylin.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store59.kylin.user.data.dao.UserDao;
import com.store59.kylin.user.data.model.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	public User getUserForLogin(String name, String password) {
		return userDao.getUserForLogin(name, password);
	}

	public User getUser(Long uid) {
		return userDao.selectByPrimaryKey(uid);
	}

	public Boolean resetPassword(Long uid, String oldPassword, String password) {
		User user = getUser(uid);
		if (user == null || !user.getPasswd().equals(oldPassword)) {
			return false;
		}
		user.setPasswd(password);
		return userDao.updateByPrimaryKeySelective(user);
	}
}
