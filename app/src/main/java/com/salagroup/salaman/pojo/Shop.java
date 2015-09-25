package com.salagroup.salaman.pojo;

import java.io.Serializable;

public class Shop implements Serializable {

    private long _id;
    private String code;
    private String shopName;
    private long userID;
    private String description;
    private String invoicePrefix;
    private String taxCode;
    private String fax;
    private String email;
    private String phone;
    private String phone2;
    private String address;
    private int regionL1;
    private int regionL2;
    private int regionL3;
    private int regionL4;
    private int regionL5;
    private int regionL6;
    private int regionL7;
    private double latitude;
    private double longitude;
    private String contactName;
    private String contactJobTitle;
    private String contactEmail;
    private String contactPhone;
    private String contactAddress;
    private String note;
    private boolean status;
    private long createdBy;
    private String createdDateTime;
    private long lastUpdatedBy;
    private String lastUpdatedDateTime;

    public Shop() {
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInvoicePrefix() {
        return invoicePrefix;
    }

    public void setInvoicePrefix(String invoicePrefix) {
        this.invoicePrefix = invoicePrefix;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRegionL1() {
        return regionL1;
    }

    public void setRegionL1(int regionL1) {
        this.regionL1 = regionL1;
    }

    public int getRegionL2() {
        return regionL2;
    }

    public void setRegionL2(int regionL2) {
        this.regionL2 = regionL2;
    }

    public int getRegionL3() {
        return regionL3;
    }

    public void setRegionL3(int regionL3) {
        this.regionL3 = regionL3;
    }

    public int getRegionL4() {
        return regionL4;
    }

    public void setRegionL4(int regionL4) {
        this.regionL4 = regionL4;
    }

    public int getRegionL5() {
        return regionL5;
    }

    public void setRegionL5(int regionL5) {
        this.regionL5 = regionL5;
    }

    public int getRegionL6() {
        return regionL6;
    }

    public void setRegionL6(int regionL6) {
        this.regionL6 = regionL6;
    }

    public int getRegionL7() {
        return regionL7;
    }

    public void setRegionL7(int regionL7) {
        this.regionL7 = regionL7;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactJobTitle() {
        return contactJobTitle;
    }

    public void setContactJobTitle(String contactJobTitle) {
        this.contactJobTitle = contactJobTitle;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
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
}
