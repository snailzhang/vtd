package com.esd.db.model;

import java.util.Date;

public class inspector {

    private Integer inspectorId;

    private String inspectorUsername;

    private String inspectorPassword;

    private String inspectorRealName;

    private Integer createId;

    private Date createTime;

    private String createMethod;

    private Integer updateId;

    private Date updateTime;

    private String updateMethod;

    private String version;

    public Integer getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(Integer inspectorId) {
        this.inspectorId = inspectorId;
    }

    public String getInspectorUsername() {
        return inspectorUsername;
    }

    public void setInspectorUsername(String inspectorUsername) {
        this.inspectorUsername = inspectorUsername == null ? null : inspectorUsername.trim();
    }

    public String getInspectorPassword() {
        return inspectorPassword;
    }

    public void setInspectorPassword(String inspectorPassword) {
        this.inspectorPassword = inspectorPassword == null ? null : inspectorPassword.trim();
    }

    public String getInspectorRealName() {
        return inspectorRealName;
    }

    public void setInspectorRealName(String inspectorRealName) {
        this.inspectorRealName = inspectorRealName == null ? null : inspectorRealName.trim();
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
        this.version = version;
    }
}