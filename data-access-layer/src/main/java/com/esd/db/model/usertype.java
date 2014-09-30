package com.esd.db.model;

public class usertype {
 
    private Integer userTypeId;

    private String userTypeName;
    
    private String userTypeNameEnglish;

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName == null ? null : userTypeName.trim();
    }

	public String getUserTypeNameEnglish() {
		return userTypeNameEnglish;
	}

	public void setUserTypeNameEnglish(String userTypeNameEnglish) {
		this.userTypeNameEnglish = userTypeNameEnglish;
	}
    
}