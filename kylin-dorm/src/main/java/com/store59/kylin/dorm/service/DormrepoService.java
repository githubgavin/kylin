package com.store59.kylin.dorm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store59.kylin.dorm.data.dao.DormrepoDao;
import com.store59.kylin.dorm.data.model.Dormrepo;

@Service
public class DormrepoService {
	@Autowired
	DormrepoDao dormrepoDao;
	
	public Dormrepo getDormRepoByRid(Integer rid){
		return dormrepoDao.getDormRepoByRid(rid);
	}

}
