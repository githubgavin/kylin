package com.store59.kylin.order.data.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.store59.kylin.order.data.filter.OrderFilter;
import com.store59.kylin.order.data.mapper.OrderMapper;
import com.store59.kylin.order.data.model.Order;

@Repository
public class OrderDao {
	@Autowired
	private OrderMapper masterOrderMapper;
	@Autowired
	private OrderMapper slaveOrderMapper;

	public List<Order> selectByFilter(OrderFilter filter) {
		return slaveOrderMapper.selectByFilter(filter);
	}

	public Boolean updateByPrimaryKeySelective(Order order) {
		int rows = masterOrderMapper.updateByPrimaryKeySelective(order);
		if (rows > 0) {
			return true;
		}
		return false;
	}

}
