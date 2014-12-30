package com.esd.db.model;

import java.util.Date;

public class inspectorrecord {

    private Integer id;

    private String note;

    private Date createtime;

    private Integer inspectorid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getInspectorid() {
        return inspectorid;
    }

    public void setInspectorid(Integer inspectorid) {
        this.inspectorid = inspectorid;
    }
}