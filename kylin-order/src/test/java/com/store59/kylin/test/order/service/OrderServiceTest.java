package com.store59.kylin.test.order.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;

import com.store59.kylin.common.Application;
import com.store59.kylin.order.data.filter.OrderFilter;
import com.store59.kylin.order.data.model.Order;
import com.store59.kylin.order.data.service.OrderService;

public class OrderServiceTest extends TestCase {

	public void testGetOrderList() {
		ApplicationContext ctx = Application.getContext();
		OrderService orderService = ctx.getBean(OrderService.class);
		Integer dormId = 3;
		OrderFilter filter = new OrderFilter();
		filter.setDormId(dormId);
		List<Byte> status = new ArrayList<Byte>();
		// status.add((byte) 1);
		filter.setStatus(status);
		filter.setLimit(20);
		List<Order> orders = orderService.getOrderList(filter);
		assertTrue(orders != null);
		System.out.println("--------- testGetOrderList ----------");
		System.out.println(orders.size());
	}

}
