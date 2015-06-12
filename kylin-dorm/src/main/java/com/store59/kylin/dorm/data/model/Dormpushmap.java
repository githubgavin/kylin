package com.store59.kylin.dorm.data.model;

public class Dormpushmap {
	private Integer id;

	private Integer dormId;

	private String targetId;

	private Short mapType;

	private String deviceId;

	private String deviceToken;

	private String appVersion;

	private String systemVersion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDormId() {
		return dormId;
	}

	public void setDormId(Integer dormId) {
		this.dormId = dormId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId == null ? null : targetId.trim();
	}

	public Short getMapType() {
		return mapType;
	}

	public void setMapType(Short mapType) {
		this.mapType = mapType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId == null ? null : deviceId.trim();
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken == null ? null : deviceToken.trim();
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion == null ? null : appVersion.trim();
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion == null ? null : systemVersion
				.trim();
	}
}
