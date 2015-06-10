package com.store59.kylin.order.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store59.kylin.dorm.service.DormitemService;
import com.store59.kylin.order.data.dao.OrderDao;
import com.store59.kylin.order.data.dao.OrderfoodDao;
import com.store59.kylin.order.data.filter.OrderFilter;
import com.store59.kylin.order.data.model.Order;
import com.store59.kylin.order.data.model.Orderfood;

@Service
public class OrderService {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderfoodDao orderfoodDao;
	@Autowired
	private DormitemService dormitemService;
	@Autowired
	private CouponService couponService;

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

	public Order getOrder(Long orderId) {
		return orderDao.selectByPrimaryKey(orderId);
	}

	public Boolean setStatus(Long orderId, Byte status) {
		Order order = orderDao.selectByPrimaryKey(orderId);
		if (status == null || order == null) {
			throw new RuntimeException("请求参数有误");
		}
		Byte oldStatus = order.getStatus();
		if (oldStatus == 4 || oldStatus == 5) {
			throw new RuntimeException("订单不允许更新状态");
		}
		Order updateOrder = new Order();
		updateOrder.setOrderId(orderId);
		updateOrder.setStatus(status);
		orderDao.updateByPrimaryKeySelective(updateOrder);
		// 取消订单，需要返回库存
		if (status == 5) {
			backDormItemStock(orderId, order.getDormId());
			couponService.backOrderCoupon(orderId);
		}
		return true;
	}

	private void backDormItemStock(Long orderId, int dormId) {
		List<Orderfood> foodList = orderfoodDao.selectByOrderId(orderId);
		if (foodList == null) {
			return;
		}
		for (Orderfood food : foodList) {
			// 要增加的库存数量
			dormitemService.addDormItemStock(dormId, food.getRid(),
					food.getQuantity());
		}
	}
}
