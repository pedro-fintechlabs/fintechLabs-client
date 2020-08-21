package com.javalabs.shared.dto;

import java.util.Date;

import javax.ws.rs.QueryParam;

public class Audit implements Model {

	private static final long serialVersionUID = 1L;

	@QueryParam("id")
	private Long id;
	@QueryParam("user_type")
	private String userType;
	@QueryParam("user_id")
	private Long userId;
	@QueryParam("user_email")
	private String userEmail;
	@QueryParam("event")
	private String event;
	@QueryParam("auditable")
	private Boolean auditable;
	@QueryParam("old_values")
	private String oldValues;
	@QueryParam("new_values")
	private String newValues;
	@QueryParam("url")
	private String url;
	@QueryParam("ip_address")
	private String ipAddress;
	@QueryParam("user_agent")
	private String userAgent;
	@QueryParam("tags")
	private String tags;
	@QueryParam("time_stamp")
	private String timeStamp;
	
	public Audit() {
		this(null, null, null, null, null, null, null, null, null, null, null, null);
	}
	
	public Audit(String userType, Long userId, String userEmail, String event, Boolean auditable, String oldValues, String newValues,
			String url, String ipAddress, String userAgent, String tags, String timeStamp) {
		super();
		this.userType = userType;
		this.userId = userId;
		this.userEmail = userEmail;
		this.event = event;
		this.auditable = auditable;
		this.oldValues = oldValues;
		this.newValues = newValues;
		this.url = url;
		this.ipAddress = ipAddress;
		this.userAgent = userAgent;
		this.tags = tags;
		this.timeStamp = timeStamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Boolean getAuditable() {
		return auditable;
	}

	public void setAuditable(Boolean auditable) {
		this.auditable = auditable;
	}

	public String getOldValues() {
		return oldValues;
	}

	public void setOldValues(String oldValues) {
		this.oldValues = oldValues;
	}

	public String getNewValues() {
		return newValues;
	}

	public void setNewValues(String newValues) {
		this.newValues = newValues;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
		
}
