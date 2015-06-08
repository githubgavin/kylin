package com.store59.kylin.dorm.data.mapper;

import org.apache.ibatis.annotations.Param;

import com.store59.kylin.dorm.data.model.Dormitem;

public interface DormitemMapper {
	int deleteByPrimaryKey(Integer itemId);

	int insert(Dormitem record);

	int insertSelective(Dormitem record);

	Dormitem selectByPrimaryKey(Integer itemId);

	int updateByPrimaryKeySelective(Dormitem record);

	int updateByPrimaryKey(Dormitem record);

	int addDormItemStock(@Param("dormId") int dormId, @Param("rid") int rid,
			@Param("stock") int stock);
}
