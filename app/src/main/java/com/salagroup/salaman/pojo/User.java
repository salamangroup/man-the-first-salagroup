package com.salagroup.salaman.pojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

@Table(name = "T00_User", id = "_id")
public class User extends Model {

    @Column(name = "Status")
    @Expose
    public int status;
    @Column(name = "Code")
    @Expose
    private String code;
    @Column(name = "UserName")
    @Expose
    private String username;
    @Column(name = "Password")
    @Expose
    private String password;
    @Column(name = "DisplayName")
    @Expose
    private String displayName;
    @Column(name = "Gender")
    @Expose
    private int gender;
    @Column(name = "Birthday")
    @Expose
    private String birthDay;
    @Column(name = "Email")
    @Expose
    private String email;
    @Column(name = "Phone")
    @Expose
    private String phone;
    @Column(name = "Address")
    @Expose
    private String address;
    @Column(name = "LastLogin")
    @Expose
    private String lastLogin;
    @Column(name = "DeviceToken")
    @Expose
    private String deviceToken;
    @Column(name = "FacebookToken")
    @Expose
    private String facebookToken;
    @Column(name = "FacebookID")
    @Expose
    private String facebookID;
    @Column(name = "GoogleToken")
    @Expose
    private String googleToken;
    @Column(name = "GoogleID")
    @Expose
    private String googleId;
    @Column(name = "LastIP")
    @Expose
    private String lastIP;
    @Column(name = "IsShop")
    @Expose
    private boolean isShop;
    @Column(name = "Latitude")
    @Expose
    private double latitude;
    @Column(name = "Longitude")
    @Expose
    private double longitude;
    @Column(name = "SettingID")
    @Expose
    private long settingID;
    @Column(name = "Note")
    @Expose
    private String note;
    @Column(name = "CreatedBy")
    @Expose
    private long createdBy;
    @Column(name = "CreatedDateTime")
    @Expose
    private String createdDateTime;
    @Column(name = "LastUpdatedBy")
    @Expose
    private long lastUpdatedBy;
    @Column(name = "LastUpdatedDateTime")
    @Expose
    private String lastUpdatedDateTime;

    public User() {
        super();
    }

    public User(String username, String password, String displayName, int gender, String birthDay, String email, String phone, String address) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.gender = gender;
        this.birthDay = birthDay;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public static User getUserById(int _id) {
        return new Select().from(User.class).where("_id = ?", _id).executeSingle();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getFacebookToken() {
        return facebookToken;
    }

    public void setFacebookToken(String facebookToken) {
        this.facebookToken = facebookToken;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getGoogleToken() {
        return googleToken;
    }

    public void setGoogleToken(String googleToken) {
        this.googleToken = googleToken;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getLastIP() {
        return lastIP;
    }

    public void setLastIP(String lastIP) {
        this.lastIP = lastIP;
    }

    public boolean isShop() {
        return isShop;
    }

    public void setIsShop(boolean isShop) {
        this.isShop = isShop;
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

    public long getSettingID() {
        return settingID;
    }

    public void setSettingID(long settingID) {
        this.settingID = settingID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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
