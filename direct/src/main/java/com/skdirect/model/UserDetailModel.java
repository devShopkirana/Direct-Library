package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class UserDetailModel {

    @SerializedName("MobileNumber")
    private String MobileNumber;
    @SerializedName("FcmId")
    private String FcmId;
    @SerializedName("StoreView")
    private int StoreView;
    @SerializedName("City")
    private String City;
    @SerializedName("State")
    private String State;
    @SerializedName("SellerMobileNumber")
    private String SellerMobileNumber;
    @SerializedName("ImagePath")
    private String ImagePath;
    @SerializedName("Rating")
    private double Rating;
    @SerializedName("Longitude")
    private double Longitude;
    @SerializedName("Latitiute")
    private double Latitiute;
    @SerializedName("Pincode")
    private String Pincode;
    @SerializedName("AddressTwo")
    private String AddressTwo;
    @SerializedName("AddressOne")
    private String AddressOne;
    @SerializedName("ShopName")
    private String ShopName;
    @SerializedName("FirstName")
    private String FirstName;

    @SerializedName("MiddleName")
    private String MiddleName;

    @SerializedName("LastName")
    private String LastName;

    @SerializedName("Distance")
    private double Distance;

    public double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getFcmId() {
        return FcmId;
    }

    public void setFcmId(String fcmId) {
        FcmId = fcmId;
    }

    public int getStoreView() {
        return StoreView;
    }

    public void setStoreView(int storeView) {
        StoreView = storeView;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getSellerMobileNumber() {
        return SellerMobileNumber;
    }

    public void setSellerMobileNumber(String sellerMobileNumber) {
        SellerMobileNumber = sellerMobileNumber;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitiute() {
        return Latitiute;
    }

    public void setLatitiute(double latitiute) {
        Latitiute = latitiute;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getAddressTwo() {
        return AddressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        AddressTwo = addressTwo;
    }

    public String getAddressOne() {
        return AddressOne;
    }

    public void setAddressOne(String addressOne) {
        AddressOne = addressOne;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
