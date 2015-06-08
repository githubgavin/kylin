package com.store59.kylin.dormapi.viewmodel;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.store59.kylin.dorm.data.model.Dorm;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class DormView {
	private Integer dormId;
	private String deliveryAddress;
	private String zhifubao;
	private Float balance;
	private String notice;

	public DormView() {

	}

	public DormView(Dorm dorm) {
		this.dormId = dorm.getDormId();
		this.deliveryAddress = dorm.getDeliveryAddress();
		this.zhifubao = dorm.getAliAccount();
		if (dorm.getMoney() != null) {
			this.balance = dorm.getMoney().floatValue();
		}
		this.notice = dorm.getNotice();
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getZhifubao() {
		return zhifubao;
	}

	public void setZhifubao(String zhifubao) {
		this.zhifubao = zhifubao;
	}

	public Float getBalance() {
		return balance;
	}

	public void setBalance(Float balance) {
		this.balance = balance;
	}

	public Integer getDormId() {
		return dormId;
	}

	public void setDormId(Integer dormId) {
		this.dormId = dormId;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

}
