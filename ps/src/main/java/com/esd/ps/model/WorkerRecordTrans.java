package com.esd.ps.model;

public class WorkerRecordTrans {

	private String taskName;

	private String packName;

	private String downPackName;

	private String taskDownTime;

	private String taskUploadTime;

	private Integer taskStatu;

	private Integer taskLockTime;

	private Double taskMarkTime;

	private Boolean taskEffective;

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

	public Integer getTaskStatu() {
		return taskStatu;
	}

	public void setTaskStatu(Integer taskStatu) {
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

	public Boolean getTaskEffective() {
		return taskEffective;
	}

	public void setTaskEffective(Boolean taskEffective) {
		this.taskEffective = taskEffective;
	}

	public String getDownPackName() {
		return downPackName;
	}

	public void setDownPackName(String downPackName) {
		this.downPackName = downPackName;
	}
}