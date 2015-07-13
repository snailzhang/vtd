package com.esd.ps;

import java.io.Serializable;

public class Wav implements Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 8530019607101410925L;

	private String name = null;
	private byte[] data = null;
	private String data_str = null;
	private String secretKey  = null;
	private double lockTime = 0.00;
	private int taskId = 0;
	private int workerId = 0;
	private String listStr = null;
	private int taskeffect = 0;

	public Wav() {

	}

	public Wav(String name,byte[] data,double lockTime) {
		this.name = name;
		this.data = data;
		this.lockTime = lockTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public double getLockTime() {
		return lockTime;
	}

	public void setLockTime(double lockTime) {
		this.lockTime = lockTime;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getWorkerId() {
		return workerId;
	}

	public void setWorkerId(int workerId) {
		this.workerId = workerId;
	}

	public String getData_str() {
		return data_str;
	}

	public void setData_str(String data_str) {
		this.data_str = data_str;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getListStr() {
		return listStr;
	}

	public void setListStr(String listStr) {
		this.listStr = listStr;
	}

	public int getTaskeffect() {
		return taskeffect;
	}

	public void setTaskeffect(int taskeffect) {
		this.taskeffect = taskeffect;
	}
	
}
