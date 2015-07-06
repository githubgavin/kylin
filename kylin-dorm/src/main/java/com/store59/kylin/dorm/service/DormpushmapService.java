package com.store59.kylin.dorm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store59.kylin.dorm.data.dao.DormpushmapDao;
import com.store59.kylin.dorm.data.model.Dormpushmap;

@Service
public class DormpushmapService {
	@Autowired
	private DormpushmapDao dormpushmapDao;

	public Boolean updateDormpushmap(Dormpushmap dormpushmap) {
		if (dormpushmap == null) {
			return false;
		}
		String deviceId = dormpushmap.getDeviceId();
		if (deviceId == null || deviceId.isEmpty()) {
			return false;
		}
		Dormpushmap old = dormpushmapDao.getDormpushmapByDevice(deviceId);
		if (old != null) {
			dormpushmap.setId(old.getId());
			return dormpushmapDao.updateByPrimaryKeySelective(dormpushmap);
		}
		return dormpushmapDao.insertSelective(dormpushmap);
	}
	
	public Dormpushmap getDormpushmapByDevice(String deviceId){
		return dormpushmapDao.getDormpushmapByDevice(deviceId);
	}

}
