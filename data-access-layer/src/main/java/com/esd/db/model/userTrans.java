package com.esd.db.model;

public class userTrans {

	private int userStatus;

	private int userId;

	private String username;

	private String realName;
	
	private String phone;

	private String usertypeenglish;

	private Double taskMarkTimeMonth;
	
	private Double salary;
	
	private Double waitingMarkTime;

	private String createTime;

	private String updateTime;
	//放弃
	private int giveUpCount;
	//超时
	private int oldCount;
	//下载
	private int downCount;
	//待审核
	private int waitingCount;
	//完成
	private int finishCount;
	//未上传
	private int unUploadCount;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
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
	
	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getGiveUpCount() {
		return giveUpCount;
	}

	public void setGiveUpCount(int giveUpCount) {
		this.giveUpCount = giveUpCount;
	}

	public int getOldCount() {
		return oldCount;
	}

	public void setOldCount(int oldCount) {
		this.oldCount = oldCount;
	}

	public int getDownCount() {
		return downCount;
	}

	public void setDownCount(int downCount) {
		this.downCount = downCount;
	}

	public int getWaitingCount() {
		return waitingCount;
	}

	public void setWaitingCount(int waitingCount) {
		this.waitingCount = waitingCount;
	}

	public int getFinishCount() {
		return finishCount;
	}

	public void setFinishCount(int finishCount) {
		this.finishCount = finishCount;
	}

	public int getUnUploadCount() {
		return unUploadCount;
	}

	public void setUnUploadCount(int unUploadCount) {
		this.unUploadCount = unUploadCount;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getWaitingMarkTime() {
		return waitingMarkTime;
	}

	public void setWaitingMarkTime(Double waitingMarkTime) {
		this.waitingMarkTime = waitingMarkTime;
	}
	
}