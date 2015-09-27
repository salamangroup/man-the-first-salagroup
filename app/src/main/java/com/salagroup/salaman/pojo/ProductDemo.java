package com.salagroup.salaman.pojo;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class ProductDemo {

    @Expose
    private Long productId;
    @Expose
    private String productName;

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Expose
    private List<String> images = new ArrayList<String>();

    /**
     * @return The productId
     */
    public Long getProductId() {
        return productId;
    }

    /**
     * @param productId The productId
     */
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
     * @return The productName
     */
    public String getProductName() {
        return productName;
    }
}
