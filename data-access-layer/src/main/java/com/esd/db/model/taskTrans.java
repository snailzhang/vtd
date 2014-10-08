package com.esd.db.model;

public class taskTrans {

    private String taskName;

    private Double taskMarkTime;

    private Boolean taskEffective;

    private String taskDownloadTime;

    private String taskUploadTime;
    
    private String createTime;

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

    public String getTaskDownloadTime() {
        return taskDownloadTime;
    }

    public void setTaskDownloadTime(String taskDownloadTime) {
        this.taskDownloadTime = taskDownloadTime;
    }

    public String getTaskUploadTime() {
        return taskUploadTime;
    }

    public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setTaskUploadTime(String taskUploadTime) {
        this.taskUploadTime = taskUploadTime;
    }
}