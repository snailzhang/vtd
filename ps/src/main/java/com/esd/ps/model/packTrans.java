package com.esd.ps.model;

public class packTrans {

	private int packId;

	private String packName;

	private int packStatus;

	private Integer packLockTime;

	private Integer unzip;

	private String createTime;

	private Integer taskCount;

	private Integer finishTaskCount;
	
	private Integer invalid;
	
	private Integer wavZero;

	private Integer downCount;
	
	private Integer taskLvl;
	
	private Double taskMarkTime;
	
	private String markTimeMethodName;
	
	private int markTimeMethodId;

	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName == null ? null : packName.trim();
	}

	public int getPackStatus() {
		return packStatus;
	}

	public void setPackStatus(int packStatus) {
		this.packStatus = packStatus;
	}

	public Integer getPackLockTime() {
		return packLockTime;
	}

	public Integer getUnzip() {
		return unzip;
	}

	public void setUnzip(Integer unzip) {
		this.unzip = unzip;
	}

	public void setPackLockTime(Integer packLockTime) {
		this.packLockTime = packLockTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getPackId() {
		return packId;
	}

	public void setPackId(int packId) {
		this.packId = packId;
	}

	public Integer getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(Integer taskCount) {
		this.taskCount = taskCount;
	}

	public Integer getFinishTaskCount() {
		return finishTaskCount;
	}

	public void setFinishTaskCount(Integer finishTaskCount) {
		this.finishTaskCount = finishTaskCount;
	}
	
	public Integer getInvalid() {
		return invalid;
	}

	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}

	public Integer getWavZero() {
		return wavZero;
	}

	public void setWavZero(Integer wavZero) {
		this.wavZero = wavZero;
	}

	public Integer getDownCount() {
		return downCount;
	}

	public void setDownCount(Integer downCount) {
		this.downCount = downCount;
	}
	
	public Integer getTaskLvl() {
		return taskLvl;
	}

	public void setTaskLvl(Integer taskLvl) {
		this.taskLvl = taskLvl;
	}

	public Double getTaskMarkTime() {
		return taskMarkTime;
	}

	public void setTaskMarkTime(Double taskMarkTime) {
		this.taskMarkTime = taskMarkTime;
	}

	public String getMarkTimeMethodName() {
		return markTimeMethodName;
	}

	public void setMarkTimeMethodName(String markTimeMethodName) {
		this.markTimeMethodName = markTimeMethodName;
	}

	public int getMarkTimeMethodId() {
		return markTimeMethodId;
	}

	public void setMarkTimeMethodId(int markTimeMethodId) {
		this.markTimeMethodId = markTimeMethodId;
	}
	
}