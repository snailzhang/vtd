package com.esd.db.model;

import java.util.Date;

public class workerRecord {

    private Integer recordId;

    private Integer workerId;

    private Integer packId;

    private Integer taskId;
    
    private String userName;

    private String taskName;
    
    private String realName;

    private String packName;
    
    private String downPackName;
    
    private String downUrl;

    private Date taskDownTime;

    private Date taskUploadTime;
    
    private Date taskOverTime;

    private Integer taskStatu;

    private Integer taskLockTime;

    private Double taskMarkTime;

    private Date createTime;

    private String createMethod;

    private Date updateTime;

    private String updateMethod;

    private int taskEffective;
    
    private int inspectorId;
    
    private int inspectorrecordId;
    
    private int version;
    
    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Integer getPackId() {
        return packId;
    }

    public void setPackId(Integer packId) {
        this.packId = packId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
    
    
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }
    
    public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName == null ? null : packName.trim();
    }

    public Date getTaskDownTime() {
        return taskDownTime;
    }

    public void setTaskDownTime(Date taskDownTime) {
        this.taskDownTime = taskDownTime;
    }

    public Date getTaskUploadTime() {
        return taskUploadTime;
    }

    public void setTaskUploadTime(Date taskUploadTime) {
        this.taskUploadTime = taskUploadTime;
    }
      
    public Date getTaskOverTime() {
		return taskOverTime;
	}

	public void setTaskOverTime(Date taskOverTime) {
		this.taskOverTime = taskOverTime;
	}

	public Integer getTaskStatu() {
        return taskStatu;
    }

    public void setTaskStatu(Integer taskStatu) {
        this.taskStatu = taskStatu;
    }

    public Integer getTaskLockTime() {
        return taskLockTime;
    }

    public void setTaskLockTime(Integer taskLockTime) {
        this.taskLockTime = taskLockTime;
    }

    public Double getTaskMarkTime() {
        return taskMarkTime;
    }

    public void setTaskMarkTime(Double taskMarkTime) {
        this.taskMarkTime = taskMarkTime;
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

    public int getTaskEffective() {
        return taskEffective;
    }

    public void setTaskEffective(int taskEffective) {
        this.taskEffective = taskEffective;
    }

	public String getDownPackName() {
		return downPackName;
	}

	public void setDownPackName(String downPackName) {
		this.downPackName = downPackName;
	}

	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}

	public int getInspectorId() {
		return inspectorId;
	}

	public void setInspectorId(int inspectorId) {
		this.inspectorId = inspectorId;
	}
	
	public int getInspectorrecordId() {
		return inspectorrecordId;
	}

	public void setInspectorrecordId(int inspectorrecordId) {
		this.inspectorrecordId = inspectorrecordId;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
    
}