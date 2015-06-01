package com.store59.kylin.user.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.store59.kylin.user.data.mapper.UserMapper;
import com.store59.kylin.user.data.model.User;

@Repository
public class UserDao {
	@Autowired
	private UserMapper masterUserMapper;
	@Autowired
	private UserMapper slaveUserMapper;

	public User selectByPrimaryKey(int uid) {
		return slaveUserMapper.selectByPrimaryKey(uid);
	}

	public User getUserForLogin(String name, String password) {
		if (name == null || name.isEmpty() || password == null
				|| password.isEmpty()) {
			return null;
		}
		return slaveUserMapper.getUserForLogin(name, password);
	}

	public Boolean updateByPrimaryKeySelective(User record) {
		int count = masterUserMapper.updateByPrimaryKeySelective(record);
		if (count == 1) {
			return true;
		}
		return false;
	}

}
