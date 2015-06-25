package com.store59.kylin.order.data.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Order {
	private Long orderId;

	private Byte status;

	private Byte type;

	private Byte paytype;

	private Byte paystatus;

	private Byte source;

	private Integer sid;

	private Integer siteId;

	private String payTradeNo;

	private Integer dormId;

	private Integer dormentryId;

	private Integer uid;

	private Byte serviceEva;

	private Byte deliveryEva;

	private Byte foodEva;

	private Short foodNum;

	private BigDecimal foodAmount;

	private BigDecimal shipFee;

	private BigDecimal couponDiscount;

	private BigDecimal promotionDiscount;

	private BigDecimal discount;

	private BigDecimal orderAmount;

	private Integer deliveryId;

	private Integer addTime;

	private Integer confirmTime;

	private Integer sendTime;

	private Integer expectDate;

	private Byte deliveryType;

	private Integer expectTime;

	private String expectTimeslot;

	private Integer orderMark;

	private String uname;

	private String portrait;

	private String phone;

	private String phoneAddr;

	private Integer buyTimes;

	private String address1;

	private String address2;

	private String dormitory;

	private Byte timeDeliver;

	private Short credit;

	private String ip;

	private String couponCode;

	private String feature;

	private String remark;

	private String evaluation;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Byte getPaytype() {
		return paytype;
	}

	public void setPaytype(Byte paytype) {
		this.paytype = paytype;
	}

	public Byte getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(Byte paystatus) {
		this.paystatus = paystatus;
	}

	public Byte getSource() {
		return source;
	}

	public void setSource(Byte source) {
		this.source = source;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getPayTradeNo() {
		return payTradeNo;
	}

	public void setPayTradeNo(String payTradeNo) {
		this.payTradeNo = payTradeNo == null ? null : payTradeNo.trim();
	}

	public Integer getDormId() {
		return dormId;
	}

	public void setDormId(Integer dormId) {
		this.dormId = dormId;
	}

	public Integer getDormentryId() {
		return dormentryId;
	}

	public void setDormentryId(Integer dormentryId) {
		this.dormentryId = dormentryId;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Byte getServiceEva() {
		return serviceEva;
	}

	public void setServiceEva(Byte serviceEva) {
		this.serviceEva = serviceEva;
	}

	public Byte getDeliveryEva() {
		return deliveryEva;
	}

	public void setDeliveryEva(Byte deliveryEva) {
		this.deliveryEva = deliveryEva;
	}

	public Byte getFoodEva() {
		return foodEva;
	}

	public void setFoodEva(Byte foodEva) {
		this.foodEva = foodEva;
	}

	public Short getFoodNum() {
		return foodNum;
	}

	public void setFoodNum(Short foodNum) {
		this.foodNum = foodNum;
	}

	public BigDecimal getFoodAmount() {
		return foodAmount;
	}

	public void setFoodAmount(BigDecimal foodAmount) {
		this.foodAmount = foodAmount;
	}

	public BigDecimal getShipFee() {
		return shipFee;
	}

	public void setShipFee(BigDecimal shipFee) {
		this.shipFee = shipFee;
	}

	public BigDecimal getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(BigDecimal couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public BigDecimal getPromotionDiscount() {
		return promotionDiscount;
	}

	public void setPromotionDiscount(BigDecimal promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Integer deliveryId) {
		this.deliveryId = deliveryId;
	}

	public Integer getAddTime() {
		return addTime;
	}

	public void setAddTime(Integer addTime) {
		this.addTime = addTime;
	}

	public Integer getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Integer confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Integer getSendTime() {
		return sendTime;
	}

	public void setSendTime(Integer sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getExpectDate() {
		return expectDate;
	}

	public void setExpectDate(Integer expectDate) {
		this.expectDate = expectDate;
	}

	public Byte getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Byte deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Integer getExpectTime() {
		return expectTime;
	}

	public void setExpectTime(Integer expectTime) {
		this.expectTime = expectTime;
	}

	public String getExpectTimeslot() {
		return expectTimeslot;
	}

	public void setExpectTimeslot(String expectTimeslot) {
		this.expectTimeslot = expectTimeslot == null ? null : expectTimeslot
				.trim();
	}

	public Integer getOrderMark() {
		return orderMark;
	}

	public void setOrderMark(Integer orderMark) {
		this.orderMark = orderMark;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname == null ? null : uname.trim();
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait == null ? null : portrait.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public String getPhoneAddr() {
		return phoneAddr;
	}

	public void setPhoneAddr(String phoneAddr) {
		this.phoneAddr = phoneAddr == null ? null : phoneAddr.trim();
	}

	public Integer getBuyTimes() {
		return buyTimes;
	}

	public void setBuyTimes(Integer buyTimes) {
		this.buyTimes = buyTimes;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1 == null ? null : address1.trim();
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2 == null ? null : address2.trim();
	}

	public String getDormitory() {
		return dormitory;
	}

	public void setDormitory(String dormitory) {
		this.dormitory = dormitory == null ? null : dormitory.trim();
	}

	public Byte getTimeDeliver() {
		return timeDeliver;
	}

	public void setTimeDeliver(Byte timeDeliver) {
		this.timeDeliver = timeDeliver;
	}

	public Short getCredit() {
		return credit;
	}

	public void setCredit(Short credit) {
		this.credit = credit;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip == null ? null : ip.trim();
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode == null ? null : couponCode.trim();
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature == null ? null : feature.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation == null ? null : evaluation.trim();
	}

}
