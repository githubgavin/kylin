package com.store59.kylin.api.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.store59.kylin.api.viewmodel.Result;
import com.store59.kylin.common.Util;
import com.store59.kylin.common.exception.ServiceException;
import com.store59.kylin.order.data.model.CartItem;
import com.store59.kylin.order.data.model.Order;
import com.store59.kylin.order.data.model.Orderfood;
import com.store59.kylin.order.data.service.OrderService;
import com.store59.kylin.order.data.service.OrderfoodService;

@RestController
public class OrderController {
	@Autowired
	private OrderfoodService orderfoodService;
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/order/foods", method = RequestMethod.GET)
	public Object getFoodList(HttpServletRequest request, Long order_id) {
		List<Orderfood> foods = orderfoodService.getOrderfoodList(order_id);
		Map<String, Object> data = new HashMap<>();
		data.put("foods", foods);
		Result result = new Result();
		result.setData(data);
		return result;
	}

	@RequestMapping(value = "/order/info", method = RequestMethod.GET)
	public Object info(HttpServletRequest request, Long order_id) {
		Order order = orderService.getOrder(order_id);
		Result result = new Result();
		result.setData(order);
		return result;
	}

	@RequestMapping(value = "/order/update", method = RequestMethod.POST)
	public Object update(HttpServletRequest request, Long order_id,
			Byte status, Byte paytype, Byte paystatus, String pay_trade_no,
			Integer send_time, Integer confirm_time) {
		Order order = new Order();
		order.setOrderId(order_id);
		order.setStatus(status);
		order.setPaytype(paytype);
		order.setPaystatus(paystatus);
		order.setPayTradeNo(pay_trade_no);
		order.setSendTime(send_time);
		order.setConfirmTime(confirm_time);
		Boolean updateStatus = orderService.updateOrder(order);
		Map<String, Object> data = new HashMap<String, Object>();
		if (updateStatus) {
			data.put("status", 1);
		} else {
			data.put("status", 0);
		}
		Result result = new Result();
		result.setData(data);
		return result;
	}

	@RequestMapping(value = "/order/create", method = RequestMethod.POST)
	public Result placeOrder(HttpServletRequest request, Byte type,
			Byte paytype, Byte source, Integer dormentry_id, Integer uid,
			String items, String coupon_code, BigDecimal promotion_discount,
			BigDecimal food_amount, Integer food_num, BigDecimal order_amount,
			String phone, String dormitory, String ip, String remark) {
		if (type == null || paytype == null || source == null
				|| dormentry_id == null || uid == null || items == null
				|| food_amount == null || food_num == null
				|| order_amount == null || phone == null || dormitory == null
				|| ip == null) {
			throw new ServiceException(4, "参数错误");
		}

		List<CartItem> cartItemList = Util.getObjectFromJson(items,
				new TypeReference<List<CartItem>>() {
				});

		Order order = orderService.placeOrder(type, paytype, source,
				dormentry_id, uid, cartItemList, coupon_code,
				promotion_discount, food_amount, food_num, order_amount, phone,
				dormitory, ip, remark);
		Result result = new Result();
		result.setData(order);
		return result;
	}
}
