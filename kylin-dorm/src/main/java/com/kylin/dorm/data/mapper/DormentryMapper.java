package com.kylin.dorm.data.mapper;

import java.util.List;

import com.kylin.dorm.data.model.Dormentry;

public interface DormentryMapper {
	List<Dormentry> selectByDormID(int dormId);
}
