package com.kylin.dorm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kylin.common.Util;
import com.kylin.common.cache.ICache;
import com.kylin.dorm.data.dao.DormentryDao;
import com.kylin.dorm.data.model.Dormentry;

@Service
public class DormentryService {
	@Autowired
	private DormentryDao dormentryDao;
	private ICache cache;
	private int cacheDormentryListTimeout = 10;

	public void setDormentryDao(DormentryDao dormentryDao) {
		this.dormentryDao = dormentryDao;
	}

	public void setCache(ICache cache) {
		this.cache = cache;
	}

	public List<Dormentry> getDormentryList(int dormId) {
		List<Dormentry> result = null;
		String cacheKey = String.format("dorm:dormentrylist:%s", dormId);
		String cacheValue = null;
		cacheValue = cache.get(cacheKey);
		if (cacheValue == null) {
			result = dormentryDao.getDormEntryList(dormId);
			cacheValue = Util.getJsonFromObject(result);
			this.cache.setex(cacheKey, cacheDormentryListTimeout, cacheValue);
		} else {
			result = Util.getObjectFromJson(cacheValue,
					new TypeReference<List<Dormentry>>() {
					});
		}
		return result;
	}
}
