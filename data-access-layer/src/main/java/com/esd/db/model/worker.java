package com.esd.db.model;

import java.util.Date;

public class worker {

    private Integer workerId;

    private Integer userId;

    private String workerRealName;

    private String workerIdCard;

    private String workerDisabilityCard;

    private String workerPhone;

    private String workerMac;

    private String workerBankCard;

    private String workerPaypal;

    private Integer createId;

    private Date createTime;

    private String createMethod;

    private Integer updateId;

    private Date updateTime;

    private String updateMethod;

    private int version;

    private byte[] workerImage;

	public Integer getWorkerId() {
		return workerId;
	}

	public void setWorkerId(Integer workerId) {
		this.workerId = workerId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getWorkerRealName() {
		return workerRealName;
	}

	public void setWorkerRealName(String workerRealName) {
		this.workerRealName = workerRealName;
	}

	public String getWorkerIdCard() {
		return workerIdCard;
	}

	public void setWorkerIdCard(String workerIdCard) {
		this.workerIdCard = workerIdCard;
	}

	public String getWorkerDisabilityCard() {
		return workerDisabilityCard;
	}

	public void setWorkerDisabilityCard(String workerDisabilityCard) {
		this.workerDisabilityCard = workerDisabilityCard;
	}

	public String getWorkerPhone() {
		return workerPhone;
	}

	public void setWorkerPhone(String workerPhone) {
		this.workerPhone = workerPhone;
	}

	public String getWorkerMac() {
		return workerMac;
	}

	public void setWorkerMac(String workerMac) {
		this.workerMac = workerMac;
	}

	public String getWorkerBankCard() {
		return workerBankCard;
	}

	public void setWorkerBankCard(String workerBankCard) {
		this.workerBankCard = workerBankCard;
	}

	public String getWorkerPaypal() {
		return workerPaypal;
	}

	public void setWorkerPaypal(String workerPaypal) {
		this.workerPaypal = workerPaypal;
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
		this.createMethod = createMethod;
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
		this.updateMethod = updateMethod;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public byte[] getWorkerImage() {
		return workerImage;
	}

	public void setWorkerImage(byte[] workerImage) {
		this.workerImage = workerImage;
	}
    
    
   
}