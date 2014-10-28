package com.esd.db.model;

import java.util.Date;

public class pack {

	private Integer packId;

	private Integer employerId;

	private String packName;

	private Boolean packStatus;

	private Integer packLockTime;

	private Integer unzip;

	private Integer createId;

	private Date createTime;

	private String createMethod;

	private Integer updateId;

	private Date updateTime;

	private String updateMethod;

	private Integer downCount;

	private Integer version;

	public Integer getPackId() {
		return packId;
	}

	public void setPackId(Integer packId) {
		this.packId = packId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pack.employer_id
	 * 
	 * @return the value of pack.employer_id
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public Integer getEmployerId() {
		return employerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pack.employer_id
	 * 
	 * @param employerId
	 *            the value for pack.employer_id
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public void setEmployerId(Integer employerId) {
		this.employerId = employerId;
	}

	public String getPackName() {
		return packName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pack.pack_name
	 * 
	 * @param packName
	 *            the value for pack.pack_name
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public void setPackName(String packName) {
		this.packName = packName == null ? null : packName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pack.pack_status
	 * 
	 * @return the value of pack.pack_status
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public Boolean getPackStatus() {
		return packStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pack.pack_status
	 * 
	 * @param packStatus
	 *            the value for pack.pack_status
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public void setPackStatus(Boolean packStatus) {
		this.packStatus = packStatus;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pack.pack_lock_time
	 * 
	 * @return the value of pack.pack_lock_time
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
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

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pack.create_id
	 * 
	 * @return the value of pack.create_id
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public Integer getCreateId() {
		return createId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pack.create_id
	 * 
	 * @param createId
	 *            the value for pack.create_id
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pack.create_time
	 * 
	 * @return the value of pack.create_time
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pack.create_time
	 * 
	 * @param createTime
	 *            the value for pack.create_time
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pack.create_method
	 * 
	 * @return the value of pack.create_method
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public String getCreateMethod() {
		return createMethod;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pack.create_method
	 * 
	 * @param createMethod
	 *            the value for pack.create_method
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public void setCreateMethod(String createMethod) {
		this.createMethod = createMethod == null ? null : createMethod.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pack.update_id
	 * 
	 * @return the value of pack.update_id
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public Integer getUpdateId() {
		return updateId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pack.update_id
	 * 
	 * @param updateId
	 *            the value for pack.update_id
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pack.update_time
	 * 
	 * @return the value of pack.update_time
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pack.update_time
	 * 
	 * @param updateTime
	 *            the value for pack.update_time
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pack.update_method
	 * 
	 * @return the value of pack.update_method
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public String getUpdateMethod() {
		return updateMethod;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column pack.update_method
	 * 
	 * @param updateMethod
	 *            the value for pack.update_method
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public void setUpdateMethod(String updateMethod) {
		this.updateMethod = updateMethod == null ? null : updateMethod.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column pack.version
	 * 
	 * @return the value of pack.version
	 * 
	 * @mbggenerated Mon Aug 25 13:54:58 CST 2014
	 */
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getDownCount() {
		return downCount;
	}

	public void setDownCount(Integer downCount) {
		this.downCount = downCount;
	}

}