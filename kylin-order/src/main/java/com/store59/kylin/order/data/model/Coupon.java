package com.store59.kylin.order.data.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Coupon {
	private Integer itemId;

	private Byte type;

	private Byte handout;

	private Integer addTime;

	private Integer useTime;

	private Long useUid;

	private Long useOrderId;

	private String code;

	private BigDecimal discount;

	private Integer expireDate;

	private String refId;

	private Long uid;

	private Integer threshold;

	private Byte nvshen;

	private Integer rid;

	private Integer ridNum;

	private BigDecimal ridAmount;

	private String text;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public Byte getHandout() {
		return handout;
	}

	public void setHandout(Byte handout) {
		this.handout = handout;
	}

	public Integer getAddTime() {
		return addTime;
	}

	public void setAddTime(Integer addTime) {
		this.addTime = addTime;
	}

	public Integer getUseTime() {
		return useTime;
	}

	public void setUseTime(Integer useTime) {
		this.useTime = useTime;
	}

	public Long getUseUid() {
		return useUid;
	}

	public void setUseUid(Long useUid) {
		this.useUid = useUid;
	}

	public Long getUseOrderId() {
		return useOrderId;
	}

	public void setUseOrderId(Long useOrderId) {
		this.useOrderId = useOrderId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Integer getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Integer expireDate) {
		this.expireDate = expireDate;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId == null ? null : refId.trim();
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public Byte getNvshen() {
		return nvshen;
	}

	public void setNvshen(Byte nvshen) {
		this.nvshen = nvshen;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public Integer getRidNum() {
		return ridNum;
	}

	public void setRidNum(Integer ridNum) {
		this.ridNum = ridNum;
	}

	public BigDecimal getRidAmount() {
		return ridAmount;
	}

	public void setRidAmount(BigDecimal ridAmount) {
		this.ridAmount = ridAmount;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text == null ? null : text.trim();
	}
}
