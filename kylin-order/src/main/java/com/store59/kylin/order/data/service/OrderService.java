package com.store59.kylin.order.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store59.kylin.order.data.dao.OrderDao;
import com.store59.kylin.order.data.filter.OrderFilter;
import com.store59.kylin.order.data.model.Order;

@Service
public class OrderService {
	@Autowired
	private OrderDao orderDao;

	public List<Order> getOrderList(OrderFilter filter) {
		List<Byte> status = filter.getStatus();
		if (status != null && status.size() == 0) {
			filter.setStatus(null);
		}
		return orderDao.selectByFilter(filter);
	}

	public Boolean updateOrder(Order order) {
		return orderDao.updateByPrimaryKeySelective(order);
	}
}
