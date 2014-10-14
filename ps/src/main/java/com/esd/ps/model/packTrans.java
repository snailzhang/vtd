package com.esd.ps.model;

public class packTrans {

    private int packId;
	
	
	private String packName;

    private Boolean packStatus;

    private Integer packLockTime;

    private String createTime;

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

}