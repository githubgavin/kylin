package com.store59.kylin.dorm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store59.kylin.dorm.data.dao.DormDao;
import com.store59.kylin.dorm.data.model.Dorm;

@Service
public class DormService {
	@Autowired
	private DormDao dormDao;

	public Dorm getDorm(Integer dormId) {
		return dormDao.selectByPrimaryKey(dormId);
	}

	public Dorm getDormByUid(Integer uid) {
		return dormDao.selectByUid(uid);
	}

	public Boolean updateDorm(Dorm dorm) {
		return dormDao.updateByPrimaryKeySelective(dorm);
	}
}
