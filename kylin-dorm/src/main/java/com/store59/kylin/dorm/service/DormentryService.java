package com.store59.kylin.dorm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.store59.kylin.common.Util;
import com.store59.kylin.common.cache.ICache;
import com.store59.kylin.dorm.data.dao.DormentryDao;
import com.store59.kylin.dorm.data.model.Dormentry;

@Service
public class DormentryService {
	@Autowired
	private DormentryDao dormentryDao;
	@Autowired
	private ICache cache;
	private int cacheDormentryListTimeout = 0;

	public List<Dormentry> getDormentryList(int dormId) {
		List<Dormentry> result = null;
		String cacheKey = String.format("dorm:dormentrylist:%s", dormId);
		String cacheValue = null;
		if (cacheDormentryListTimeout > 0) {
			cacheValue = cache.get(cacheKey);
		}
		if (cacheValue == null) {
			result = dormentryDao.getDormEntryList(dormId);
			cacheValue = Util.getJsonFromObject(result);
			if (cacheDormentryListTimeout > 0) {
				this.cache.setex(cacheKey, cacheDormentryListTimeout,
						cacheValue);
			}
		} else {
			result = Util.getObjectFromJson(cacheValue,
					new TypeReference<List<Dormentry>>() {
					});
		}
		return result;
	}

	public Boolean updateDormentry(Dormentry dormentry) {
		return dormentryDao.updateByPrimaryKeySelective(dormentry);
	}
}
