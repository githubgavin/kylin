package com.kylin.user.data.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kylin.datasource.factory.DaoReadFactory;
import com.kylin.datasource.factory.DaoWriteFactory;
import com.kylin.user.data.mapper.UserMapper;
import com.kylin.user.data.model.User;

@Repository
public class UserDao {
	@Autowired
	private DaoReadFactory daoReadFactory;
	@Autowired
	private DaoWriteFactory daoWriteFactory;

	public User selectByPrimaryKey(int uid) {
		SqlSession session = null;
		try {
			session = daoReadFactory.getSqlSessionFactory().openSession();
			UserMapper userMapper = session.getMapper(UserMapper.class);
			return userMapper.selectByPrimaryKey(uid);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public User getUserForLogin(String name, String password) {
		if (name == null || name.isEmpty() || password == null
				|| password.isEmpty()) {
			return null;
		}
		SqlSession session = null;
		try {
			session = daoReadFactory.getSqlSessionFactory().openSession();
			UserMapper userMapper = session.getMapper(UserMapper.class);
			return userMapper.getUserForLogin(name, password);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public Boolean updateByPrimaryKeySelective(User record) {
		SqlSession session = null;
		try {
			session = daoWriteFactory.getSqlSessionFactory().openSession();
			UserMapper userMapper = session.getMapper(UserMapper.class);
			int count = userMapper.updateByPrimaryKeySelective(record);
			if (count == 1) {
				return true;
			}
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
