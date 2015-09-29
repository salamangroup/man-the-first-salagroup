package com.salagroup.salaman.pojo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "T02_ProductPicture", id = "_id")
public class ProductPicture extends Model {
    @Column(name = "UserID")
    private long userID;
    @Column(name = "ShopID")
    private long shopID;
    @Column(name = "Code")
    private String code;
    @Column(name = "ProductID")
    private long productID;
    @Column(name = "LocalLink")
    private String localLink;
    @Column(name = "ServerLink")
    private String serverLink;
    @Column(name = "Description")
    private String description;
    @Column(name = "IsCover")
    private boolean isCover;
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

    public ProductPicture() {
        super();
    }

    public long getUserID() {
        return userID;
    }
}
