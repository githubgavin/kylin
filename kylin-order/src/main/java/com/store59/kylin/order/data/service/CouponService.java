package com.store59.kylin.order.data.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.store59.kylin.order.data.dao.CouponDao;
import com.store59.kylin.order.data.model.Coupon;

@Service
public class CouponService {
	@Autowired
	private CouponDao couponDao;

	public Boolean backOrderCoupon(Long orderId) {
		return couponDao.backOrderCoupon(orderId);
	}

	public Boolean updateCoupon(Coupon coupon) {
		return couponDao.updateByPrimaryKeySelective(coupon);
	}

}
