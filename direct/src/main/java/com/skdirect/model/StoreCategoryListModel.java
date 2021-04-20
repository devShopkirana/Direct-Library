package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public  class StoreCategoryListModel {
    @SerializedName("StoreList")
    private ArrayList<StoreListModel> StoreList;
    @SerializedName("CategoryName")
    private String CategoryName;
    @SerializedName("CategoryId")
    private int CategoryId;

    public ArrayList<StoreListModel> getStoreList() {
        return StoreList;
    }

    public void setStoreList(ArrayList<StoreListModel> storeList) {
        StoreList = storeList;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }
}
