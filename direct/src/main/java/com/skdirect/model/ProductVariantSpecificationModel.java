package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class ProductVariantSpecificationModel {


    @SerializedName("IsDelete")
    private boolean IsDelete;
    @SerializedName("IsActive")
    private boolean IsActive;
    @SerializedName("AttributeValue")
    private String AttributeValue;
    @SerializedName("AttributeId")
    private int AttributeId;
    @SerializedName("ProductMasterId")
    private int ProductMasterId;
    @SerializedName("AttributeName")
    private String AttributeName;
    @SerializedName("Id")
    private int Id;

    public boolean isDelete() {
        return IsDelete;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public String getAttributeValue() {
        return AttributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        AttributeValue = attributeValue;
    }

    public int getAttributeId() {
        return AttributeId;
    }

    public void setAttributeId(int attributeId) {
        AttributeId = attributeId;
    }

    public int getProductMasterId() {
        return ProductMasterId;
    }

    public void setProductMasterId(int productMasterId) {
        ProductMasterId = productMasterId;
    }

    public String getAttributeName() {
        return AttributeName;
    }

    public void setAttributeName(String attributeName) {
        AttributeName = attributeName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
