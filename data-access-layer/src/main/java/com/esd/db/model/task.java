package com.esd.db.model;

import java.util.Date;

public class task {

    private Integer taskId;

    private Integer packId;

    private Integer inspectorId;

    private Integer faultId;

    private Integer workerId;

    private String taskDir;

    private String taskName;

    private Double taskMarkTime;

    private Boolean taskEffective;

    private Boolean taskUpload;

    private Integer taskLvl;

    private Date taskDownloadTime;

    private Date taskUploadTime;

    private Integer createId;

    private Date createTime;

    private String createMethod;

    private Integer updateId;

    private Date updateTime;

    private String updateMethod;

    private String version;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getPackId() {
        return packId;
    }

    public void setPackId(Integer packId) {
        this.packId = packId;
    }

    public Integer getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(Integer inspectorId) {
        this.inspectorId = inspectorId;
    }

    public Integer getFaultId() {
        return faultId;
    }

    public void setFaultId(Integer faultId) {
        this.faultId = faultId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public String getTaskDir() {
        return taskDir;
    }

    public void setTaskDir(String taskDir) {
        this.taskDir = taskDir == null ? null : taskDir.trim();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
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

    public Boolean getTaskUpload() {
        return taskUpload;
    }

    public void setTaskUpload(Boolean taskUpload) {
        this.taskUpload = taskUpload;
    }

    public Integer getTaskLvl() {
        return taskLvl;
    }

    public void setTaskLvl(Integer taskLvl) {
        this.taskLvl = taskLvl;
    }

    public Date getTaskDownloadTime() {
        return taskDownloadTime;
    }

    public void setTaskDownloadTime(Date taskDownloadTime) {
        this.taskDownloadTime = taskDownloadTime;
    }

    public Date getTaskUploadTime() {
        return taskUploadTime;
    }

    public void setTaskUploadTime(Date taskUploadTime) {
        this.taskUploadTime = taskUploadTime;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }
}