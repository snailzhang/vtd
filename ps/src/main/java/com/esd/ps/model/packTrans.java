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

	private Integer downCount;

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

	public Integer getDownCount() {
		return downCount;
	}

	public void setDownCount(Integer downCount) {
		this.downCount = downCount;
	}

}