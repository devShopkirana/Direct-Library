package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MallResultItemModel {

    @SerializedName("Id")
    private String Id;
    @SerializedName("ServedPincodeList")
    private ArrayList<String> ServedPincodeList;
    @SerializedName("StoreCategoryList")
    private ArrayList<StoreCategoryListModel> StoreCategoryList;
    @SerializedName("ServiceCategoryList")
    private ArrayList<ServiceCategoryListModel> ServiceCategoryList;
    @SerializedName("IsDelete")
    private boolean IsDelete;
    @SerializedName("IsActive")
    private boolean IsActive;
    @SerializedName("CreatedBy")
    private int CreatedBy;
    @SerializedName("CreatedDate")
    private String CreatedDate;
    @SerializedName("ModifiedDate")
    private String ModifiedDate;
    @SerializedName("ModifiedBy")
    private String ModifiedBy;
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public ArrayList<String> getServedPincodeList() {
        return ServedPincodeList;
    }

    public void setServedPincodeList(ArrayList<String> servedPincodeList) {
        ServedPincodeList = servedPincodeList;
    }

    public ArrayList<StoreCategoryListModel> getStoreCategoryList() {
        return StoreCategoryList;
    }

    public void setStoreCategoryList(ArrayList<StoreCategoryListModel> storeCategoryList) {
        StoreCategoryList = storeCategoryList;
    }

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

    public int getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(int createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }
}
