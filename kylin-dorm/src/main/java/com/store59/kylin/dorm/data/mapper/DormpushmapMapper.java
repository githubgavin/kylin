package com.store59.kylin.dorm.data.mapper;

import com.store59.kylin.dorm.data.model.Dormpushmap;

public interface DormpushmapMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Dormpushmap record);

	int insertSelective(Dormpushmap record);

	Dormpushmap selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Dormpushmap record);

	int updateByPrimaryKey(Dormpushmap record);
	
	Dormpushmap getDormpushmapByDevice(String deviceId);
}
