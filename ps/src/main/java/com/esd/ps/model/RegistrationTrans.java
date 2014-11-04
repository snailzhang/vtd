/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.esd.ps.model;

public class RegistrationTrans {

	    private Integer id;

	    private String name;

	    private String card;

	    private Integer districtId;

	    private String phone;

	    private String qq;

	    private String address;

	    private String des;

	    private String createTime;

	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name == null ? null : name.trim();
	    }

	    public String getCard() {
	        return card;
	    }

	    public void setCard(String card) {
	        this.card = card;
	    }

	    public Integer getDistrictId() {
	        return districtId;
	    }

	    public void setDistrictId(Integer districtId) {
	        this.districtId = districtId;
	    }

	    public String getPhone() {
	        return phone;
	    }

	    public void setPhone(String phone) {
	        this.phone = phone;
	    }

	    public String getQq() {
	        return qq;
	    }

	    public void setQq(String qq) {
	        this.qq = qq == null ? null : qq.trim();
	    }

	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address == null ? null : address.trim();
	    }

	    public String getDes() {
	        return des;
	    }

	    public void setDes(String des) {
	        this.des = des == null ? null : des.trim();
	    }

	    public String getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(String createTime) {
	        this.createTime = createTime;
	    }

	}

