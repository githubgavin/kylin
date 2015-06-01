package com.store59.kylin.dormapi.viewmodel;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.store59.kylin.user.data.model.User;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class UserView {
	private Integer uid;
	private String uname;
	private String phone;
	
	public UserView(){
		
	}
	
	public UserView(User user){
		this.uid = user.getUid();
		this.uname = user.getUname();
		this.phone = user.getPhone();
	}
	
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
