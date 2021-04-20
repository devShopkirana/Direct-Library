package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomerDataModel {

    @SerializedName("Id")
    private int Id;

    @SerializedName("IsActive")
    private boolean IsActive;

    @SerializedName("IsDelete")
    private boolean IsDelete;

    @SerializedName("FirstName")
    private String FirstName;

    @SerializedName("MiddleName")
    private String MiddleName;

    @SerializedName("MobileNo")
    private String MobileNo;

    @SerializedName("UserId")
    private String UserId;

    @SerializedName("LastName")
    private String LastName;

    @SerializedName("Email")
    private String Email;

    @SerializedName("Pincode")
    private String Pincode;

    @SerializedName("PinCodeMasterId")
    private int PinCodeMasterId;

    @SerializedName("ShopName")
    private String ShopName;

    @SerializedName("ImagePath")
    private String ImagePath;

    @SerializedName("State")
    private String State;

    @SerializedName("City")
    private String City;

    @SerializedName("UserName")
    private String UserName;

    @SerializedName("EncryptedId")
    private String EncryptedId;

    @SerializedName("IsRegistrationComplete")
    private boolean IsRegistrationComplete;

    @SerializedName("UserDeliveryDC")
    private ArrayList<UserDeliveryModel> UserDeliveryDC;

    public CustomerDataModel(int id, boolean isActive, boolean isDelete, String firstName, String middleName, String mobileNo, String userId, String lastName, String email, String pincode, int pinCodeMasterId, String shopName, String imagePath, String state, String city, String userName, String encryptedId, boolean isRegistrationComplete, ArrayList<UserDeliveryModel> userDeliveryDC) {
        Id = id;
        IsActive = isActive;
        IsDelete = isDelete;
        FirstName = firstName;
        MiddleName = middleName;
        MobileNo = mobileNo;
        UserId = userId;
        LastName = lastName;
        Email = email;
        Pincode = pincode;
        PinCodeMasterId = pinCodeMasterId;
        ShopName = shopName;
        ImagePath = imagePath;
        State = state;
        City = city;
        UserName = userName;
        EncryptedId = encryptedId;
        IsRegistrationComplete = isRegistrationComplete;
        UserDeliveryDC = userDeliveryDC;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public boolean isDelete() {
        return IsDelete;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
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

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public int getPinCodeMasterId() {
        return PinCodeMasterId;
    }

    public void setPinCodeMasterId(int pinCodeMasterId) {
        PinCodeMasterId = pinCodeMasterId;
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

    public boolean isRegistrationComplete() {
        return IsRegistrationComplete;
    }

    public void setRegistrationComplete(boolean registrationComplete) {
        IsRegistrationComplete = registrationComplete;
    }

    public ArrayList<UserDeliveryModel> getUserDeliveryDC() {
        return UserDeliveryDC;
    }

    public void setUserDeliveryDC(ArrayList<UserDeliveryModel> userDeliveryDC) {
        UserDeliveryDC = userDeliveryDC;
    }

    public String getEncryptedId() {
        return EncryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        EncryptedId = encryptedId;
    }


    public class UserDeliveryModel {

        @SerializedName("Id")
        private int Id;

        @SerializedName("UserId")
        private int UserId;

        @SerializedName("Delivery")
        private String Delivery;

        @SerializedName("IsDelete")
        private boolean IsDelete;

        @SerializedName("IsActive")
        private boolean IsActive;

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int userId) {
            UserId = userId;
        }

        public String getDelivery() {
            return Delivery;
        }

        public void setDelivery(String delivery) {
            Delivery = delivery;
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
    }
}
