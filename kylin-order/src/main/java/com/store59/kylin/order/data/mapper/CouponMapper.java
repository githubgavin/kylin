package com.store59.kylin.order.data.mapper;

import com.store59.kylin.order.data.model.Coupon;

public interface CouponMapper {

	int deleteByPrimaryKey(Integer itemId);

	int insert(Coupon record);

	int insertSelective(Coupon record);

	Coupon selectByPrimaryKey(Integer itemId);

	int updateByPrimaryKeySelective(Coupon record);

	int updateByPrimaryKey(Coupon record);
	
	int backOrderCoupon(Long orderId);

}
