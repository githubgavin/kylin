package com.store59.kylin.ext.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store59.kylin.ext.data.dao.AppVersionDao;
import com.store59.kylin.ext.data.model.AppVersion;

@Service
public class AppVersionService {
	@Autowired
	private AppVersionDao appVersionDao;

	public AppVersion getByAppType(Short appType) {
		return appVersionDao.getByAppType(appType);
	}
}
