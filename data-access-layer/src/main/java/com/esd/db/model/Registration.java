package com.esd.db.model;

import java.util.Date;

public class Registration {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column registration.id
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column registration.name
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column registration.card
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    private String card;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column registration.district_id
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    private Integer districtId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column registration.phone
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column registration.qq
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    private String qq;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column registration.address
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    private String address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column registration.des
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    private String des;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column registration.create_time
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column registration.create_method
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    private String createMethod;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column registration.update_time
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column registration.update_method
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    private String updateMethod;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column registration.id
     *
     * @return the value of registration.id
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column registration.id
     *
     * @param id the value for registration.id
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column registration.name
     *
     * @return the value of registration.name
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column registration.name
     *
     * @param name the value for registration.name
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column registration.card
     *
     * @return the value of registration.card
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public String getCard() {
        return card;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column registration.card
     *
     * @param card the value for registration.card
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public void setCard(String card) {
        this.card = card;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column registration.district_id
     *
     * @return the value of registration.district_id
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public Integer getDistrictId() {
        return districtId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column registration.district_id
     *
     * @param districtId the value for registration.district_id
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column registration.phone
     *
     * @return the value of registration.phone
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column registration.phone
     *
     * @param phone the value for registration.phone
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column registration.qq
     *
     * @return the value of registration.qq
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public String getQq() {
        return qq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column registration.qq
     *
     * @param qq the value for registration.qq
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column registration.address
     *
     * @return the value of registration.address
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column registration.address
     *
     * @param address the value for registration.address
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column registration.des
     *
     * @return the value of registration.des
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public String getDes() {
        return des;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column registration.des
     *
     * @param des the value for registration.des
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column registration.create_time
     *
     * @return the value of registration.create_time
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column registration.create_time
     *
     * @param createTime the value for registration.create_time
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column registration.create_method
     *
     * @return the value of registration.create_method
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public String getCreateMethod() {
        return createMethod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column registration.create_method
     *
     * @param createMethod the value for registration.create_method
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public void setCreateMethod(String createMethod) {
        this.createMethod = createMethod == null ? null : createMethod.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column registration.update_time
     *
     * @return the value of registration.update_time
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column registration.update_time
     *
     * @param updateTime the value for registration.update_time
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column registration.update_method
     *
     * @return the value of registration.update_method
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public String getUpdateMethod() {
        return updateMethod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column registration.update_method
     *
     * @param updateMethod the value for registration.update_method
     *
     * @mbggenerated Tue Oct 14 13:54:31 CST 2014
     */
    public void setUpdateMethod(String updateMethod) {
        this.updateMethod = updateMethod == null ? null : updateMethod.trim();
    }
}