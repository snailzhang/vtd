package com.esd.ps.model;

public class WorkerRecordTrans {
	
	private int taskId;
	
	private String taskName;

	private String packName;

	private String downPackName;

	private String taskDownTime;

	private String taskUploadTime;

	private String taskStatu;
	
	private int taskStatus;

	private Integer taskLockTime;

	private Double taskMarkTime;

	private String taskEffective;
	
	

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName == null ? null : taskName.trim();
	}

	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName == null ? null : packName.trim();
	}

	public String getTaskDownTime() {
		return taskDownTime;
	}

	public void setTaskDownTime(String taskDownTime) {
		this.taskDownTime = taskDownTime;
	}

	public String getTaskUploadTime() {
		return taskUploadTime;
	}

	public void setTaskUploadTime(String taskUploadTime) {
		this.taskUploadTime = taskUploadTime;
	}

	public String getTaskStatu() {
		return taskStatu;
	}
	
	public int getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}

	public void setTaskStatu(String taskStatu) {
		this.taskStatu = taskStatu;
	}

	public Integer getTaskLockTime() {
		return taskLockTime;
	}

	public void setTaskLockTime(Integer taskLockTime) {
		this.taskLockTime = taskLockTime;
	}

	public Double getTaskMarkTime() {
		return taskMarkTime;
	}

	public void setTaskMarkTime(Double taskMarkTime) {
		this.taskMarkTime = taskMarkTime;
	}

	public String getTaskEffective() {
		return taskEffective;
	}

	public void setTaskEffective(String taskEffective) {
		this.taskEffective = taskEffective;
	}

	public String getDownPackName() {
		return downPackName;
	}

	public void setDownPackName(String downPackName) {
		this.downPackName = downPackName;
	}
}