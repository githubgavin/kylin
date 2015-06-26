package com.store59.kylin.dorm.data.mapper;

import com.store59.kylin.dorm.data.model.Dormrepo;

public interface DormrepoMapper {
    int deleteByPrimaryKey(Integer itemId);

    int insert(Dormrepo record);

    int insertSelective(Dormrepo record);

    Dormrepo selectByPrimaryKey(Integer itemId);

    int updateByPrimaryKeySelective(Dormrepo record);

    int updateByPrimaryKey(Dormrepo record);
    
    Dormrepo getDormRepoByRid(Integer rid);
}
