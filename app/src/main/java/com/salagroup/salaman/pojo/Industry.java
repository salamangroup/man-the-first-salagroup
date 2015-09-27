package com.salagroup.salaman.pojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "T02_Industry", id = "_id")
public class Industry extends Model {
    @Column(name = "Code")
    private String code;
    @Column(name = "IndustryName")
    private String industryName;
    @Column(name = "IndustryNameE")
    private String industryNameE;
    @Column(name = "IndustryLevel")
    private int industryLevel;
    @Column(name = "Ordinal")
    private int ordinal;
    @Column(name = "ParentID")
    private int parentID;
    @Column(name = "Note")
    private String note;
    @Column(name = "Status")
    private boolean status;
    @Column(name = "CreatedBy")
    private long createdBy;
    @Column(name = "CreatedDateTime")
    private String createdDateTime;
    @Column(name = "LastUpdatedBy")
    private long lastUpdatedBy;
    @Column(name = "LastUpdatedDateTime")
    private String lastUpdatedDateTime;
    private boolean isChecked;

    public Industry() {
        super();
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

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
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
