package com.esd.db.model;

public class taskTrans {
    private String taskDir;

    private String taskName;

    private Double taskMarkTime;

    private Boolean taskEffective;

    private Boolean taskUpload;

    private Integer taskLvl;

    private String taskDownloadTime;

    private String taskUploadTime;

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

    public String getTaskDownloadTime() {
        return taskDownloadTime;
    }

    public void setTaskDownloadTime(String taskDownloadTime) {
        this.taskDownloadTime = taskDownloadTime;
    }

    public String getTaskUploadTime() {
        return taskUploadTime;
    }

    public void setTaskUploadTime(String taskUploadTime) {
        this.taskUploadTime = taskUploadTime;
    }
}