package com.store59.kylin.location.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store59.kylin.location.data.dao.ProvinceDao;
import com.store59.kylin.location.data.model.Province;

@Service
public class ProvinceService {
	@Autowired
	private ProvinceDao provinceDao;

	public List<Province> getProvinceList() {
		return provinceDao.getProvinceList();
	}

}
