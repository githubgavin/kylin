package com.store59.kylin.order.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store59.kylin.order.data.dao.OrderfoodDao;
import com.store59.kylin.order.data.model.Orderfood;

@Service
public class OrderfoodService {
	@Autowired
	private OrderfoodDao orderfoodDao;

	public List<Orderfood> getOrderfoodList(Integer orderId) {
		return orderfoodDao.selectByOrderId(orderId);
	}
}
