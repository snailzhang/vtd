package com.esd.db.model;

import java.util.Date;

public class pack {

	private Integer packId;

	private Integer employerId;
	
	private String noteId;
	
	private int taskMarkTimeId;
	
	private String taskMarkTimeName;

	private String packName;

	private Integer packStatus;

	private Integer packLockTime;

	private Integer unzip;
	
	private Date unzipTime;
	
	private Integer packType;
	
	private Integer packLvl;

	private Integer createId;

	private Date createTime;

	private String createMethod;

	private Integer updateId;

	private Date updateTime;

	private String updateMethod;

	private Integer downCount;

	private int version;

	public Integer getPackId() {
		return packId;
	}

	public void setPackId(Integer packId) {
		this.packId = packId;
	}

	public Integer getEmployerId() {
		return employerId;
	}

	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}
	
	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	
	public int getTaskMarkTimeId() {
		return taskMarkTimeId;
	}

	public void setTaskMarkTimeId(int taskMarkTimeId) {
		this.taskMarkTimeId = taskMarkTimeId;
	}

	public String getTaskMarkTimeName() {
		return taskMarkTimeName;
	}

	public void setTaskMarkTimeName(String taskMarkTimeName) {
		this.taskMarkTimeName = taskMarkTimeName;
	}

	public String getPackName() {
		return packName;
	}

	public void setPackName(String packName) {
		this.packName = packName == null ? null : packName.trim();
	}

	public Integer getPackStatus() {
		return packStatus;
	}

	public void setPackStatus(Integer packStatus) {
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
	
	public Date getUnzipTime() {
		return unzipTime;
	}

	public void setUnzipTime(Date unzipTime) {
		this.unzipTime = unzipTime;
	}

	public Integer getPackLvl() {
		return packLvl;
	}

	public void setPackLvl(Integer packLvl) {
		this.packLvl = packLvl;
	}

	public void setPackLockTime(Integer packLockTime) {
		this.packLockTime = packLockTime;
	}

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateMethod() {
		return createMethod;
	}

	public void setCreateMethod(String createMethod) {
		this.createMethod = createMethod == null ? null : createMethod.trim();
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateMethod() {
		return updateMethod;
	}

	public void setUpdateMethod(String updateMethod) {
		this.updateMethod = updateMethod == null ? null : updateMethod.trim();
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Integer getDownCount() {
		return downCount;
	}

	public void setDownCount(Integer downCount) {
		this.downCount = downCount;
	}

	public Integer getPackType() {
		return packType;
	}

	public void setPackType(Integer packType) {
		this.packType = packType;
	}

}