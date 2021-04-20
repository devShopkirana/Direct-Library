package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public  class SellerInfoModel {


    @SerializedName("MinOrderValue")
    private double MinOrderValue;
    @SerializedName("RadialDistance")
    private double RadialDistance;
    @SerializedName("BusinessType")
    private int BusinessType;
    @SerializedName("FirstName")
    private String FirstName;
    @SerializedName("ImagePath")
    private String ImagePath;
    @SerializedName("SellerMobileNumber")
    private String SellerMobileNumber;
    @SerializedName("Rating")
    private double Rating;
    @SerializedName("ShopName")
    private String ShopName;
    @SerializedName("Gst")
    private String Gst;

    public double getMinOrderValue() {
        return MinOrderValue;
    }

    public void setMinOrderValue(double minOrderValue) {
        MinOrderValue = minOrderValue;
    }

    public double getRadialDistance() {
        return RadialDistance;
    }

    public void setRadialDistance(double radialDistance) {
        RadialDistance = radialDistance;
    }

    public int getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(int businessType) {
        BusinessType = businessType;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getSellerMobileNumber() {
        return SellerMobileNumber;
    }

    public void setSellerMobileNumber(String sellerMobileNumber) {
        SellerMobileNumber = sellerMobileNumber;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getGst() {
        return Gst;
    }

    public void setGst(String gst) {
        Gst = gst;
    }
}
