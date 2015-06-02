package com.store59.kylin.dorm.data.mapper;

import com.store59.kylin.dorm.data.model.Dorm;

public interface DormMapper {
    int deleteByPrimaryKey(Integer dormId);

    int insert(Dorm record);

    int insertSelective(Dorm record);

    Dorm selectByPrimaryKey(Integer dormId);

    int updateByPrimaryKeySelective(Dorm record);

    int updateByPrimaryKey(Dorm record);
}
