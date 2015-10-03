package com.salagroup.salaman.pojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "T02_Customer", id = "_id")
public class Customer extends Model {

    @Column(name = "Code")
    private String code;
    @Column(name = "UserID")
    private String userID;
    @Column(name = "ShopID")
    private String shopID;
    @Column(name = "CustomerName")
    private String customerName;
    @Column(name = "CustomerGroupID")
    private long customerGroupID;
    @Column(name = "Email")
    private String email;
    @Column(name = "Gender")
    private int gender;
    @Column(name = "Birthday")
    private String birthday;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Address")
    private String address;
    @Column(name = "RegionL1")
    private long regionL1;
    @Column(name = "RegionL2")
    private long regionL2;
    @Column(name = "RegionL3")
    private long regionL3;
    @Column(name = "RegionL4")
    private long regionL4;
    @Column(name = "RegionL5")
    private long regionL5;
    @Column(name = "RegionL6")
    private long regionL6;
    @Column(name = "Latitude")
    private double latitude;
    @Column(name = "Longitude")
    private double longitude;
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

    public Customer() {
        super();
        this.status = true;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getShopID() {
        return shopID;
    }

    public void setShopID(String shopID) {
        this.shopID = shopID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getCustomerGroupID() {
        return customerGroupID;
    }

    public void setCustomerGroupID(long customerGroupID) {
        this.customerGroupID = customerGroupID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getRegionL1() {
        return regionL1;
    }

    public void setRegionL1(long regionL1) {
        this.regionL1 = regionL1;
    }

    public long getRegionL2() {
        return regionL2;
    }

    public void setRegionL2(long regionL2) {
        this.regionL2 = regionL2;
    }

    public long getRegionL3() {
        return regionL3;
    }

    public void setRegionL3(long regionL3) {
        this.regionL3 = regionL3;
    }

    public long getRegionL4() {
        return regionL4;
    }

    public void setRegionL4(long regionL4) {
        this.regionL4 = regionL4;
    }

    public long getRegionL5() {
        return regionL5;
    }

    public void setRegionL5(long regionL5) {
        this.regionL5 = regionL5;
    }

    public long getRegionL6() {
        return regionL6;
    }

    public void setRegionL6(long regionL6) {
        this.regionL6 = regionL6;
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

    public List<Customer> getAll() {
        return new Select()
                .from(Customer.class)
                .where("Status = ?", 1)
                .orderBy("CustomerName ASC")
                .execute();
    }

    public static Customer getCustomerById(long _id) {

        return new Select()
                .from(Customer.class)
                .where("_id = ?", _id)
                .executeSingle();
    }

    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
