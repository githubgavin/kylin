package com.store59.kylin.dormapi.viewmodel;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.store59.kylin.order.data.model.Order;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class OrderView {
	private String orderId;
	private BigDecimal orderAmount;
	private Byte status;
	private Integer buyTimes;
	private Integer addTime;
	private String address;
	private String phone;
	private String remark;
	private Integer isVisitor;

	public OrderView() {

	}

	public OrderView(Order order) {
		this.orderId = order.getOrderId().toString();
		this.orderAmount = order.getOrderAmount();
		this.status = order.getStatus();
		this.buyTimes = order.getBuyTimes();
		this.addTime = order.getAddTime();
		this.address = String.format("%s %s", order.getAddress1(),
				order.getAddress2());
		this.phone = order.getPhone();
		this.remark = order.getRemark();
		this.isVisitor = 1;
		if (order.getUid() != null && order.getUid() > 0) {
			this.isVisitor = 0;
		}
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Integer getBuyTimes() {
		return buyTimes;
	}

	public void setBuyTimes(Integer buyTimes) {
		this.buyTimes = buyTimes;
	}

	public Integer getAddTime() {
		return addTime;
	}

	public void setAddTime(Integer addTime) {
		this.addTime = addTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsVisitor() {
		return isVisitor;
	}

	public void setIsVisitor(Integer isVisitor) {
		this.isVisitor = isVisitor;
	}
}
