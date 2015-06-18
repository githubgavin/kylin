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
import com.store59.kylin.dormapi.viewmodel.OrderfoodView;
import com.store59.kylin.dormapi.viewmodel.Result;
import com.store59.kylin.order.data.filter.OrderFilter;
import com.store59.kylin.order.data.model.Order;
import com.store59.kylin.order.data.model.Orderfood;
import com.store59.kylin.order.data.service.OrderService;
import com.store59.kylin.order.data.service.OrderfoodService;
import com.store59.kylin.user.data.model.User;
import com.store59.kylin.user.service.UserService;

@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private DormLogic dormLogic;
	@Autowired
	private OrderfoodService orderfoodService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/order/list", method = RequestMethod.GET)
	public Object getOrderList(HttpServletRequest request, Integer dorm_id,
			Integer type, Integer page_size, Integer page) {
		Object obj = request.getSession().getAttribute("usertoken");
		if (obj == null || !(obj instanceof UserToken)) {
			throw new ServiceException(2, "invalid token");
		}
		UserToken token = (UserToken) obj;
		dormLogic.checkDormId(dorm_id, token);
		OrderFilter filter = new OrderFilter();
		List<Byte> status = new ArrayList<Byte>();
		filter.setDormId(dorm_id);
		if (type != null && type == 1) {
			status.add((byte) 0);
			status.add((byte) 1);
		} else if (type != null && type == 2) {
			status.add((byte) 2);
			status.add((byte) 4);
			status.add((byte) 5);
		}
		filter.setStatus(status);
		page_size = page_size == null ? 50 : Math.max(page_size, 1);
		Integer pageIndex = page == null ? 0 : Math.max(page - 1, 0);
		filter.setOffset(pageIndex * page_size);
		filter.setLimit(page_size);
		List<Order> orders = orderService.getOrderList(filter);
		List<OrderView> orderViews = new ArrayList<>();
		for (Order order : orders) {
			OrderView orderView = new OrderView(order);
			Integer uid = order.getUid();
			orderView.setUserName("游客");
			if (uid != null && uid > 0) {
				User user = userService.getUser(uid);
				if (user != null) {
					orderView.setUserName(user.getUname());
				}
			}
			orderViews.add(orderView);
		}
		Map<String, Object> data = new HashMap<>();
		data.put("orders", orderViews);
		Result result = new Result();
		result.setData(data);
		result.UpdateToken(token);
		return result;
	}

	@RequestMapping(value = "/order/foods", method = RequestMethod.GET)
	public Object getFoodList(HttpServletRequest request, Long order_id) {
		Object obj = request.getSession().getAttribute("usertoken");
		if (obj == null || !(obj instanceof UserToken)) {
			throw new ServiceException(2, "invalid token");
		}
		UserToken token = (UserToken) obj;
		List<Orderfood> foods = orderfoodService.getOrderfoodList(order_id);
		List<OrderfoodView> foodViews = new ArrayList<>();
		for (Orderfood food : foods) {
			foodViews.add(new OrderfoodView(food));
		}
		Map<String, Object> data = new HashMap<>();
		data.put("foods", foodViews);
		Result result = new Result();
		result.setData(data);
		result.UpdateToken(token);
		return result;
	}

	@RequestMapping(value = "/order/cancel", method = RequestMethod.POST)
	public Object cancelStatus(HttpServletRequest request, Long order_id) {
		Object obj = request.getSession().getAttribute("usertoken");
		if (obj == null || !(obj instanceof UserToken)) {
			throw new ServiceException(2, "invalid token");
		}
		UserToken token = (UserToken) obj;
		Byte status = (byte) 5;
		return updateStatus(order_id, status, token);
	}

	@RequestMapping(value = "/order/process", method = RequestMethod.POST)
	public Object processStatus(HttpServletRequest request, Long order_id) {
		Object obj = request.getSession().getAttribute("usertoken");
		if (obj == null || !(obj instanceof UserToken)) {
			throw new ServiceException(2, "invalid token");
		}
		UserToken token = (UserToken) obj;
		Byte status = (byte) 1;
		return updateStatus(order_id, status, token);
	}

	@RequestMapping(value = "/order/confirm", method = RequestMethod.POST)
	public Object confirmStatus(HttpServletRequest request, Long order_id) {
		Object obj = request.getSession().getAttribute("usertoken");
		if (obj == null || !(obj instanceof UserToken)) {
			throw new ServiceException(2, "invalid token");
		}
		UserToken token = (UserToken) obj;
		Byte status = (byte) 2;
		return updateStatus(order_id, status, token);
	}

	private Object updateStatus(Long order_id, Byte status, UserToken token) {
		Boolean updateStatus = orderService.setStatus(order_id, status);
		Map<String, Object> data = new HashMap<String, Object>();
		if (updateStatus) {
			data.put("status", 1);
		} else {
			data.put("status", 0);
		}
		Result result = new Result();
		result.setData(data);
		result.UpdateToken(token);
		return result;
	}
}
