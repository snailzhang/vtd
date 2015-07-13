package com.esd.db.model;

public class userLvl {

    private Integer userLvl;

    private String userLvlName;

    private String note;

    public Integer getUserLvl() {
        return userLvl;
    }

    public void setUserLvl(Integer userLvl) {
        this.userLvl = userLvl;
    }

    public String getUserLvlName() {
        return userLvlName;
    }

    public void setUserLvlName(String userLvlName) {
        this.userLvlName = userLvlName == null ? null : userLvlName.trim();
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}