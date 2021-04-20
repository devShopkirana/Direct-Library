package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class StoreListModel {

    @SerializedName("StoreView")
    private int StoreView;
    @SerializedName("ShopName")
    private String ShopName;
    @SerializedName("ImagePath")
    private String ImagePath;
    @SerializedName("SellerId")
    private int SellerId;
    @SerializedName("SellerName")
    private String SellerName;

    public int getStoreView() {
        return StoreView;
    }

    public void setStoreView(int storeView) {
        StoreView = storeView;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public int getSellerId() {
        return SellerId;
    }

    public void setSellerId(int sellerId) {
        SellerId = sellerId;
    }

    public String getSellerName() {
        return SellerName;
    }

    public void setSellerName(String sellerName) {
        SellerName = sellerName;
    }
}
