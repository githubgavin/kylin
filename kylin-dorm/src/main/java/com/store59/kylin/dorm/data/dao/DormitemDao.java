package com.store59.kylin.dorm.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.store59.kylin.dorm.data.mapper.DormitemMapper;

@Repository
public class DormitemDao {
	@Autowired
	private DormitemMapper masterDormitemMapper;
	@Autowired
	private DormitemMapper slaveDormitemMapper;

	public int addDormItemStock(int dormId, int rid, int stock) {
		return masterDormitemMapper.addDormItemStock(dormId, rid, stock);
	}

}
