package com.store59.kylin.dorm.data.mapper;

import java.util.List;

import com.store59.kylin.dorm.data.model.Dormentry;

public interface DormentryMapper {
	List<Dormentry> selectByDormID(int dormId);
	
    int deleteByPrimaryKey(Integer dormentryId);

    int insert(Dormentry record);

    int insertSelective(Dormentry record);

    Dormentry selectByPrimaryKey(Integer dormentryId);

    int updateByPrimaryKeySelective(Dormentry record);

    int updateByPrimaryKey(Dormentry record);

}
