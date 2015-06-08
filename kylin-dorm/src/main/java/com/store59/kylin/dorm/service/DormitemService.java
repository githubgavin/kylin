package com.store59.kylin.dorm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store59.kylin.dorm.data.dao.DormitemDao;

@Service
public class DormitemService {
	@Autowired
	DormitemDao dormitemDao;

	public Boolean addDormItemStock(int dormId, int rid, int stock) {
		int rows = dormitemDao.addDormItemStock(dormId, rid, stock);
		if (rows == 0) {
			return false;
		}
		return true;
	}
}
