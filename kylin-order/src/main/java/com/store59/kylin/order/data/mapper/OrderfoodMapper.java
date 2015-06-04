package com.store59.kylin.order.data.mapper;

import java.util.List;

import com.store59.kylin.order.data.model.Orderfood;

public interface OrderfoodMapper {
	int deleteByPrimaryKey(Integer itemId);

	int insert(Orderfood record);

	int insertSelective(Orderfood record);

	Orderfood selectByPrimaryKey(Integer itemId);

	int updateByPrimaryKeySelective(Orderfood record);

	int updateByPrimaryKey(Orderfood record);

    List<Orderfood> selectByOrderId(Long orderId);
}
