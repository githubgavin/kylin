package com.kylin.user.data.mapper;

import org.apache.ibatis.annotations.Param;

import com.kylin.user.data.model.User;

public interface UserMapper {

    int deleteByPrimaryKey(Integer uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uid);
    
    User getUserForLogin(@Param("name")String name,@Param("password")String password);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
