package com.store59.kylin.user.data.mapper;

import org.apache.ibatis.annotations.Param;

import com.store59.kylin.user.data.model.User;

public interface UserMapper {

    int deleteByPrimaryKey(Long uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long uid);
    
    User getUserForLogin(@Param("name")String name,@Param("password")String password);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
