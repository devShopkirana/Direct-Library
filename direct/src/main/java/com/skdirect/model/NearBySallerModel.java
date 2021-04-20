package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class NearBySallerModel {

    @SerializedName("Id")
    private int Id;

    @SerializedName("EncryptedId")
    private String EncryptedId;

    @SerializedName("FirstName")
    private String FirstName;

    @SerializedName("LastName")
    private String LastName;

    @SerializedName("MiddleName")
    private String MiddleName;

    @SerializedName("Pincode")
    private String Pincode;

    @SerializedName("State")
    private String State;

    @SerializedName("City")
    private String City;

    @SerializedName("AddressOne")
    private String AddressOne;

    @SerializedName("AddressTwo")
    private String AddressTwo;

    @SerializedName("AddressThree")
    private String AddressThree;

    @SerializedName("ImagePath")
    private String ImagePath;

    @SerializedName("Rating")
    private double Rating;

    @SerializedName("ShopName")
    private String ShopName;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEncryptedId() {
        return EncryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        EncryptedId = encryptedId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddressOne() {
        return AddressOne;
    }

    public void setAddressOne(String addressOne) {
        AddressOne = addressOne;
    }

    public String getAddressTwo() {
        return AddressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        AddressTwo = addressTwo;
    }

    public String getAddressThree() {
        return AddressThree;
    }

    public void setAddressThree(String addressThree) {
        AddressThree = addressThree;
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

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }
}
