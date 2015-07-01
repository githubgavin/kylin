package com.store59.kylin.order.data.service;

import java.math.BigDecimal;

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

	public Coupon getCouponByCode(String code) {
		return couponDao.getCouponByCode(code);
	}

	public Boolean updateByCodeSelective(Coupon coupon) {
		return couponDao.updateByCodeSelective(coupon);
	}

	public boolean isValid(String code, int uid, BigDecimal foodAmount) {
		Coupon coupon = getCouponByCode(code);

		// 校验优惠券的从属
		int belongTo = coupon.getUid();
		if (belongTo != 0 && belongTo != uid) {
			return false;
		}

		// 校验优惠券的过期时间
		int now = (int) (System.currentTimeMillis() / 1000);
		if (coupon.getExpireDate() < now) {
			return false;
		}

		// 校验优惠券的使用情况
		if (coupon.getUseOrderId() != null) {
			return false;
		}

		// 校验优惠券的使用最低价
		if (foodAmount.intValue() < coupon.getThreshold()) {
			return false;
		}

		return true;
	}
	
	public boolean useCoupon(String code, int uid, long orderId) {
		int now = (int) (System.currentTimeMillis() / 1000);
		Coupon coupon = new Coupon();
		coupon.setCode(code);
		coupon.setUseTime(now);
		coupon.setUseUid(uid);
		coupon.setUseOrderId(orderId);
		return couponDao.updateByCodeSelective(coupon);
	}

}
