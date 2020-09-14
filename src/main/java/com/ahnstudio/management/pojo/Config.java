package com.ahnstudio.management.pojo;

import java.util.Date;

public class Config {
    private Integer id;

    private String cnfCode;

    private String cnfValue;

    private String cnfValueChange;

    private String cnfNote;

    private Date createtime;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCnfCode() {
        return cnfCode;
    }

    public void setCnfCode(String cnfCode) {
        this.cnfCode = cnfCode == null ? null : cnfCode.trim();
    }

    public String getCnfValue() {
        return cnfValue;
    }

    public void setCnfValue(String cnfValue) {
        this.cnfValue = cnfValue == null ? null : cnfValue.trim();
    }

    public String getCnfValueChange() {
        return cnfValueChange;
    }

    public void setCnfValueChange(String cnfValueChange) {
        this.cnfValueChange = cnfValueChange == null ? null : cnfValueChange.trim();
    }

    public String getCnfNote() {
        return cnfNote;
    }

    public void setCnfNote(String cnfNote) {
        this.cnfNote = cnfNote == null ? null : cnfNote.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}