package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserLocationModel implements Serializable {

    @SerializedName("IsRegistrationComplete")
    private boolean IsRegistrationComplete;
    @SerializedName("City")
    private String City;
    @SerializedName("State")
    private String State;
    @SerializedName("Pincode")
    private String Pincode;
    @SerializedName("Longitude")
    private double Longitude;
    @SerializedName("Latitiute")
    private double Latitiute;
    @SerializedName("IsDelete")
    private boolean IsDelete;
    @SerializedName("IsActive")
    private boolean IsActive;
    @SerializedName("Id")
    private int Id;
    @SerializedName("IsPrimaryAddress")
    private boolean IsPrimaryAddress;
    @SerializedName("LocationType")
    private String LocationType;
    @SerializedName("PinCodeMasterId")
    private int PinCodeMasterId;
    @SerializedName("AddressThree")
    private String AddressThree;
    @SerializedName("AddressTwo")
    private String AddressTwo;
    @SerializedName("AddressOne")
    private String AddressOne;
    @SerializedName("UserDetailId")
    private int UserDetailId;

    boolean isSelected = false;

    public UserLocationModel(boolean isRegistrationComplete, String city, String state, String pincode, double longitude, double latitiute, boolean isDelete, boolean isActive, int id, boolean isPrimaryAddress, String locationType, int pinCodeMasterId, String addressThree, String addressTwo, String addressOne, int userDetailId) {
        IsRegistrationComplete = isRegistrationComplete;
        City = city;
        State = state;
        Pincode = pincode;
        Longitude = longitude;
        Latitiute = latitiute;
        IsDelete = isDelete;
        IsActive = isActive;
        Id = id;
        IsPrimaryAddress = isPrimaryAddress;
        LocationType = locationType;
        PinCodeMasterId = pinCodeMasterId;
        AddressThree = addressThree;
        AddressTwo = addressTwo;
        AddressOne = addressOne;
        UserDetailId = userDetailId;
    }

    public boolean isRegistrationComplete() {
        return IsRegistrationComplete;
    }

    public void setRegistrationComplete(boolean registrationComplete) {
        IsRegistrationComplete = registrationComplete;
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

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isPrimaryAddress() {
        return IsPrimaryAddress;
    }

    public void setPrimaryAddress(boolean primaryAddress) {
        IsPrimaryAddress = primaryAddress;
    }

    public String getLocationType() {
        return LocationType;
    }

    public void setLocationType(String locationType) {
        LocationType = locationType;
    }

    public int getPinCodeMasterId() {
        return PinCodeMasterId;
    }

    public void setPinCodeMasterId(int pinCodeMasterId) {
        PinCodeMasterId = pinCodeMasterId;
    }

    public String getAddressThree() {
        return AddressThree;
    }

    public void setAddressThree(String addressThree) {
        AddressThree = addressThree;
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

    public int getUserDetailId() {
        return UserDetailId;
    }

    public void setUserDetailId(int userDetailId) {
        UserDetailId = userDetailId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
