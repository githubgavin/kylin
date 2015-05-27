package com.kylin.dormapi.viewmodel;

import com.kylin.dorm.data.model.Dormentry;

public class DormentryView {
	private Integer dormentryId;
	private String name;
	private Integer status;
	
	public DormentryView(){
		
	}
	
	public DormentryView(Dormentry dormentry){
		this.dormentryId = dormentry.getDormentryId();
		this.name = dormentry.getAddress();
		this.status = dormentry.getStatus().intValue();
	}
	
	public Integer getDormentryId() {
		return dormentryId;
	}
	public void setDormentryId(Integer dormentryId) {
		this.dormentryId = dormentryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
