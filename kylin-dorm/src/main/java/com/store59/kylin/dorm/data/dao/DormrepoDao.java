package com.store59.kylin.dorm.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.store59.kylin.dorm.data.mapper.DormrepoMapper;
import com.store59.kylin.dorm.data.model.Dormrepo;

@Repository
public class DormrepoDao {
	@Autowired
	private DormrepoMapper masterDormrepoMapper;
	@Autowired
	private DormrepoMapper slaveDormrepoMapper;
	
	public Dormrepo getDormRepoByRid(Integer rid){
		return slaveDormrepoMapper.getDormRepoByRid(rid);
	}

}
