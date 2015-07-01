package com.store59.kylin.ext.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.store59.kylin.ext.data.mapper.AppVersionMapper;
import com.store59.kylin.ext.data.model.AppVersion;

@Repository
public class AppVersionDao {
	@Autowired
	private AppVersionMapper masterAppVersionMapper;
	@Autowired
	private AppVersionMapper slaveAppVersionMapper;
	
	public AppVersion selectByPrimaryKey(Integer id){
		return slaveAppVersionMapper.selectByPrimaryKey(id);
	}

	public AppVersion getByAppType(Short appType){
		return slaveAppVersionMapper.getByAppType(appType);
	}
}
