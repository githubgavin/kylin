package com.store59.kylin.dorm.data.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.store59.kylin.dorm.data.mapper.DormentryMapper;
import com.store59.kylin.dorm.data.model.Dormentry;

@Repository
public class DormentryDao {
	@Autowired
	private DormentryMapper masterDormentryMapper;
	@Autowired
	private DormentryMapper slaveDormentryMapper;

	public List<Dormentry> getDormEntryList(int dormId) {
		return slaveDormentryMapper.selectByDormID(dormId);
	}

}
