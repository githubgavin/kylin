package com.store59.kylin.dorm.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.store59.kylin.dorm.data.mapper.DormMapper;
import com.store59.kylin.dorm.data.model.Dorm;

@Repository
public class DormDao {
	@Autowired
	private DormMapper masterDormMapper;
	@Autowired
	private DormMapper slaveDormMapper;

	public Dorm selectByPrimaryKey(Integer dormId) {
		return slaveDormMapper.selectByPrimaryKey(dormId);
	}

	public Boolean updateByPrimaryKeySelective(Dorm dorm) {
		int rows = masterDormMapper.updateByPrimaryKeySelective(dorm);
		if (rows > 0) {
			return true;
		}
		return false;
	}

}
