package com.esd.db.model;

import java.util.Date;

public class salary {

    private Integer id;

    private Integer userId;

    private Integer workerId;

    private Double markTimeTotle;

    private Date timer;

    private Integer createId;

    private Date createTime;

    private String createMethod;

    private Integer updateId;

    private Date updateTime;

    private String updateMethod;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Double getMarkTimeTotle() {
        return markTimeTotle;
    }

    public void setMarkTimeTotle(Double markTimeTotle) {
        this.markTimeTotle = markTimeTotle;
    }

    public Date getTimer() {
        return timer;
    }

    public void setTimer(Date timer) {
        this.timer = timer;
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
}