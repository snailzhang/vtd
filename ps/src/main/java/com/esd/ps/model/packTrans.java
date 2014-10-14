package com.esd.ps.model;

public class packTrans {

    private int packId;
    
	private String packName;

    private Boolean packStatus;

    private Integer packLockTime;

    private String createTime;
    
    private Integer taskCount;
    
    private Integer doTaskCount;
    
    private Integer undoTaskCount;
    
    private Integer downCount;

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName == null ? null : packName.trim();
    }

    public Boolean getPackStatus() {
        return packStatus;
    }

    public void setPackStatus(Boolean packStatus) {
        this.packStatus = packStatus;
    }

    public Integer getPackLockTime() {
        return packLockTime;
    }

    public void setPackLockTime(Integer packLockTime) {
        this.packLockTime = packLockTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public int getPackId() {
		return packId;
	}

	public void setPackId(int packId) {
		this.packId = packId;
	}

	public Integer getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(Integer taskCount) {
		this.taskCount = taskCount;
	}

	public Integer getDoTaskCount() {
		return doTaskCount;
	}

	public void setDoTaskCount(Integer doTaskCount) {
		this.doTaskCount = doTaskCount;
	}

	public Integer getUndoTaskCount() {
		return undoTaskCount;
	}

	public void setUndoTaskCount(Integer undoTaskCount) {
		this.undoTaskCount = undoTaskCount;
	}

	public Integer getDownCount() {
		return downCount;
	}

	public void setDownCount(Integer downCount) {
		this.downCount = downCount;
	}
	
}