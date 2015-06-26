package com.store59.kylin.order.data.filter;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

public class OrderFilter {
	private Integer dormId;
	private Integer dormentryId;
	private Integer startTime;
	private Integer endTime;
	private String phone;
	private String dormitory;
	private List<Byte> status;
	private Integer offset;
	private Integer limit;
	private Integer uid;

	public OrderFilter() {
	}

	public OrderFilter(Integer dormId, Integer startTime, Integer endTime,
			String phone, String dormitory, List<Byte> status, Integer offset,
			Integer limit) {
		this.dormId = dormId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.phone = phone;
		this.dormitory = dormitory;
		this.status = status;
		this.offset = offset;
		this.limit = limit;
	}

	public String getHashKey() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.dormId).append("_");
		sb.append(this.startTime).append("_");
		sb.append(this.endTime).append("_");
		sb.append(this.phone).append("_");
		sb.append(this.dormitory).append("_");
		sb.append(this.status).append("_");
		sb.append(this.uid).append("_");
		sb.append(this.offset).append("_");
		sb.append(this.limit).append("_");
		String key = sb.toString();
		return String.format("%s#%d", DigestUtils.md5Hex(key), key.length());
	}

	public Integer getDormId() {
		return dormId;
	}

	public void setDormId(Integer dormId) {
		this.dormId = dormId;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDormitory() {
		return dormitory;
	}

	public void setDormitory(String dormitory) {
		this.dormitory = dormitory;
	}

	public List<Byte> getStatus() {
		return status;
	}

	public void setStatus(List<Byte> status) {
		this.status = status;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getDormentryId() {
		return dormentryId;
	}

	public void setDormentryId(Integer dormentryId) {
		this.dormentryId = dormentryId;
	}
}
