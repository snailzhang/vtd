package com.esd.db.model;

public class userTrans {
	
	private Boolean userStatus;
	
	private int userId;
	
	private String username;

	private String usertypeenglish;
	
	private Double taskMarkTimeMonth;

	private String createTime;

	private String updateTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}
	
	public Boolean getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Boolean userStatus) {
		this.userStatus = userStatus;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getUsertypeenglish() {
		return usertypeenglish;
	}

	public void setUsertypeenglish(String usertypeenglish) {
		this.usertypeenglish = usertypeenglish;
	}
	
	public Double getTaskMarkTimeMonth() {
		return taskMarkTimeMonth;
	}

	public void setTaskMarkTimeMonth(Double taskMarkTimeMonth) {
		this.taskMarkTimeMonth = taskMarkTimeMonth;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}