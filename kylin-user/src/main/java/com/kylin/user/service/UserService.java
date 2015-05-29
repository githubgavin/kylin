package com.kylin.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kylin.user.data.dao.UserDao;
import com.kylin.user.data.model.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	public User getUserForLogin(String name, String password) {
		return userDao.getUserForLogin(name, password);
	}

	public User getUser(int uid) {
		return userDao.selectByPrimaryKey(uid);
	}

	public Boolean resetPassword(int uid, String oldPassword, String password) {
		User user = getUser(uid);
		if (user == null || !user.getPasswd().equals(oldPassword)) {
			return false;
		}
		user.setPasswd(password);
		return userDao.updateByPrimaryKeySelective(user);
	}
}
