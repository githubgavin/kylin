package com.store59.kylin.order.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.store59.kylin.order.data.mapper.CouponMapper;
import com.store59.kylin.order.data.model.Coupon;

@Repository
public class CouponDao {
	@Autowired
	private CouponMapper masterCouponMapper;
	@Autowired
	private CouponMapper slaveCouponMapper;

	public Boolean updateByPrimaryKeySelective(Coupon coupon) {
		int rows = masterCouponMapper.updateByPrimaryKeySelective(coupon);
		return rows > 0 ? true : false;
	}

	public Boolean backOrderCoupon(Long orderId) {
		int rows = masterCouponMapper.backOrderCoupon(orderId);
		return rows > 0 ? true : false;
	}
}
