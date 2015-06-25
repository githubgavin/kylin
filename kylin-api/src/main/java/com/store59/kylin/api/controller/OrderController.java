package com.store59.kylin.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.store59.kylin.api.viewmodel.Result;
import com.store59.kylin.order.data.model.Orderfood;
import com.store59.kylin.order.data.service.OrderfoodService;

@RestController
public class OrderController {
	@Autowired
	private OrderfoodService orderfoodService;
	
	@RequestMapping(value = "/order/foods", method = RequestMethod.GET)
	public Object getFoodList(HttpServletRequest request, Long order_id) {
		List<Orderfood> foods = orderfoodService.getOrderfoodList(order_id);
		Map<String, Object> data = new HashMap<>();
		data.put("foods", foods);
		Result result = new Result();
		result.setData(data);
		return result;
	}
}
