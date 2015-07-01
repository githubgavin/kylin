package com.store59.kylin.dorm.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.store59.kylin.dorm.data.mapper.DormpushmapMapper;
import com.store59.kylin.dorm.data.model.Dormpushmap;

@Repository
public class DormpushmapDao {
	@Autowired
	private DormpushmapMapper masterDormpushmapMapper;
	@Autowired
	private DormpushmapMapper slaveDormpushmapMapper;

	public Boolean updateByPrimaryKeySelective(Dormpushmap dormpushmap) {
		int rows = masterDormpushmapMapper
				.updateByPrimaryKeySelective(dormpushmap);
		if (rows > 0) {
			return true;
		}
		return false;
	}

	public Dormpushmap getDormpushmapByDevice(String deviceId) {
		return slaveDormpushmapMapper.getDormpushmapByDevice(deviceId);
	}

	public Boolean insertSelective(Dormpushmap record) {
		int rows = masterDormpushmapMapper.insertSelective(record);
		if (rows > 0) {
			return true;
		}
		return false;
	}
}
