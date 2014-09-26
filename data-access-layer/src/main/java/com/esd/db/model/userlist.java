package com.esd.db.model;

public class userlist {

	private String username;

	private String usertypeenglish;

	private String createTime;

	private String updateTime;

	public String getUsername() {
		return username;
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