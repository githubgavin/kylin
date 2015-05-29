package com.kylin.user.data.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class User {
	private Integer uid;

	private Byte role;

	private Byte gender;

	private Byte level;

	private Integer credit;

	private Integer historycredit;

	private String uname;

	private String passwd;

	private String email;

	private String portrait;

	private String bigPortrait;

	private String outid;

	private String outsrc;

	private String phone;

	private Integer fansNr;

	private Integer watchNr;

	private Integer likeNr;

	private Integer regTime;

	private Integer loginNum;

	private Integer lastLogin;

	private String lastIp;

	private String province;

	private String city;

	private Short miaoshaTimes;

	private Byte status;

	private String thirdRenren;

	private String thirdQq;

	private String thirdWeibo;

	private String thirdWeixin;

	private String thirdPassword;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Byte getRole() {
		return role;
	}

	public void setRole(Byte role) {
		this.role = role;
	}

	public Byte getGender() {
		return gender;
	}

	public void setGender(Byte gender) {
		this.gender = gender;
	}

	public Byte getLevel() {
		return level;
	}

	public void setLevel(Byte level) {
		this.level = level;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getHistorycredit() {
		return historycredit;
	}

	public void setHistorycredit(Integer historycredit) {
		this.historycredit = historycredit;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname == null ? null : uname.trim();
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd == null ? null : passwd.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait == null ? null : portrait.trim();
	}

	public String getBigPortrait() {
		return bigPortrait;
	}

	public void setBigPortrait(String bigPortrait) {
		this.bigPortrait = bigPortrait == null ? null : bigPortrait.trim();
	}

	public String getOutid() {
		return outid;
	}

	public void setOutid(String outid) {
		this.outid = outid == null ? null : outid.trim();
	}

	public String getOutsrc() {
		return outsrc;
	}

	public void setOutsrc(String outsrc) {
		this.outsrc = outsrc == null ? null : outsrc.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public Integer getFansNr() {
		return fansNr;
	}

	public void setFansNr(Integer fansNr) {
		this.fansNr = fansNr;
	}

	public Integer getWatchNr() {
		return watchNr;
	}

	public void setWatchNr(Integer watchNr) {
		this.watchNr = watchNr;
	}

	public Integer getLikeNr() {
		return likeNr;
	}

	public void setLikeNr(Integer likeNr) {
		this.likeNr = likeNr;
	}

	public Integer getRegTime() {
		return regTime;
	}

	public void setRegTime(Integer regTime) {
		this.regTime = regTime;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public Integer getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Integer lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp == null ? null : lastIp.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public Short getMiaoshaTimes() {
		return miaoshaTimes;
	}

	public void setMiaoshaTimes(Short miaoshaTimes) {
		this.miaoshaTimes = miaoshaTimes;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getThirdRenren() {
		return thirdRenren;
	}

	public void setThirdRenren(String thirdRenren) {
		this.thirdRenren = thirdRenren == null ? null : thirdRenren.trim();
	}

	public String getThirdQq() {
		return thirdQq;
	}

	public void setThirdQq(String thirdQq) {
		this.thirdQq = thirdQq == null ? null : thirdQq.trim();
	}

	public String getThirdWeibo() {
		return thirdWeibo;
	}

	public void setThirdWeibo(String thirdWeibo) {
		this.thirdWeibo = thirdWeibo == null ? null : thirdWeibo.trim();
	}

	public String getThirdWeixin() {
		return thirdWeixin;
	}

	public void setThirdWeixin(String thirdWeixin) {
		this.thirdWeixin = thirdWeixin == null ? null : thirdWeixin.trim();
	}

	public String getThirdPassword() {
		return thirdPassword;
	}

	public void setThirdPassword(String thirdPassword) {
		this.thirdPassword = thirdPassword == null ? null : thirdPassword
				.trim();
	}
}
