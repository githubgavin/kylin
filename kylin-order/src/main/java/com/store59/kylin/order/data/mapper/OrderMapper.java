package com.store59.kylin.order.data.mapper;

import java.util.List;

import com.store59.kylin.order.data.filter.OrderFilter;
import com.store59.kylin.order.data.model.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
    List<Order> selectByFilter(OrderFilter filter);
}
