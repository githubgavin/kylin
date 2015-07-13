package com.store59.kylin.location.data.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.store59.kylin.location.data.mapper.ProvinceMapper;
import com.store59.kylin.location.data.model.Province;

@Repository
public class ProvinceDao {
	@Autowired
	private ProvinceMapper masterProvinceMapper;
	@Autowired
	private ProvinceMapper slaveProvinceMapper;

	public List<Province> getProvinceList() {
		return slaveProvinceMapper.getProvinceList();
	}

}
