package com.store59.kylin.dorm.data.mapper;

import java.util.List;

import com.store59.kylin.dorm.data.model.Dormentry;

public interface DormentryMapper {
	List<Dormentry> selectByDormID(int dormId);
}
