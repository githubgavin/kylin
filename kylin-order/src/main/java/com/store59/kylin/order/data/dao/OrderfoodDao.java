package com.store59.kylin.order.data.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.store59.kylin.order.data.mapper.OrderfoodMapper;
import com.store59.kylin.order.data.model.Orderfood;

@Repository
public class OrderfoodDao {
	@Autowired
	private OrderfoodMapper masterOrderfoodMapper;
	@Autowired
	private OrderfoodMapper slaveOrderfoodMapper;

	public List<Orderfood> selectByOrderId(Long orderId) {
		return slaveOrderfoodMapper.selectByOrderId(orderId);
	}
	
	public Boolean insertOrderfoodList(List<Orderfood> orderfoodList){
		int count=masterOrderfoodMapper.insertOrderfoodList(orderfoodList);
		if(count!=orderfoodList.size()){
			return false;
		}
		return true;
	}

}
