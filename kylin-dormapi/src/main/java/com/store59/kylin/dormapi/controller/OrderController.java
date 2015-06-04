package com.store59.kylin.dormapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.store59.kylin.dormapi.exception.ServiceException;
import com.store59.kylin.dormapi.logic.DormLogic;
import com.store59.kylin.dormapi.logic.UserToken;
import com.store59.kylin.dormapi.viewmodel.OrderView;
import com.store59.kylin.dormapi.viewmodel.Result;
import com.store59.kylin.order.data.filter.OrderFilter;
import com.store59.kylin.order.data.model.Order;
import com.store59.kylin.order.data.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private DormLogic dormLogic;

	@RequestMapping(value = "/order/list", method = RequestMethod.GET)
	public Object getOrderList(HttpServletRequest request, Integer dorm_id,
			Integer type) {
		Object obj = request.getSession().getAttribute("usertoken");
		if (obj == null || !(obj instanceof UserToken)) {
			throw new ServiceException(2, "invalid token");
		}
		UserToken token = (UserToken) obj;
		dormLogic.checkDormId(dorm_id, token);
		OrderFilter filter = new OrderFilter();
		List<Byte> status = new ArrayList<Byte>();
		filter.setDormId(dorm_id);
		if (type == null) {
			// 获取所以订单时加入limit限制返回量
			filter.setLimit(50);
		} else if (type == 1) {
			status.add((byte) 0);
			status.add((byte) 1);
			status.add((byte) 2);
			filter.setStatus(status);
		} else if (type == 2) {
			status.add((byte) 4);
			status.add((byte) 5);
			filter.setStatus(status);
		}
		List<Order> orders = orderService.getOrderList(filter);
		List<OrderView> orderViews = new ArrayList<>();
		for (Order order : orders) {
			orderViews.add(new OrderView(order));
		}
		Map<String, Object> data = new HashMap<>();
		data.put("orders", orderViews);
		Result result = new Result();
		result.setData(data);
		result.UpdateToken(token);
		return result;
	}

}
