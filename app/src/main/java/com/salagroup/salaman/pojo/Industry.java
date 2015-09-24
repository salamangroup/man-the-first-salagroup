package com.salagroup.salaman.pojo;

import java.io.Serializable;

public class Industry implements Serializable {
    private long _id;
    private String code;
    private String industryName;
    private String industryNameE;
    private int industryLevel;
    private int parentID;
    private String note;
    private boolean status;
    private long createdBy;
    private String createdDateTime;
    private long lastUpdatedBy;
    private String lastUpdatedDateTime;
    private boolean isChecked;

    public Industry() {
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getIndustryNameE() {
        return industryNameE;
    }

    public void setIndustryNameE(String industryNameE) {
        this.industryNameE = industryNameE;
    }

    public int getIndustryLevel() {
        return industryLevel;
    }

    public void setIndustryLevel(int industryLevel) {
        this.industryLevel = industryLevel;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public long getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedDateTime() {
        return lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime(String lastUpdatedDateTime) {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
