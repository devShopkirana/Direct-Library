package com.skdirect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserDetailResponseModel {
    @Expose
    @SerializedName("IsSuccess")
    private boolean IsSuccess;
    @Expose
    @SerializedName("ResultItem")
    private ResultItemEntity ResultItem;

    public boolean getIsSuccess() {
        return IsSuccess;
    }

    public void setIsSuccess(boolean IsSuccess) {
        this.IsSuccess = IsSuccess;
    }

    public ResultItemEntity getResultItem() {
        return ResultItem;
    }

    public void setResultItem(ResultItemEntity ResultItem) {
        this.ResultItem = ResultItem;
    }

    public  class ResultItemEntity {
        @Expose
        @SerializedName("UserDeliveryDC")
        private ArrayList<UserDeliveryDCEntity> UserDeliveryDC;
        @Expose
        @SerializedName("Longitude")
        private double Longitude;
        @Expose
        @SerializedName("Latitiute")
        private double Latitiute;
        @Expose
        @SerializedName("IsB2B")
        private boolean IsB2B;
        @Expose
        @SerializedName("IsRegistrationComplete")
        private boolean IsRegistrationComplete;
        @Expose
        @SerializedName("City")
        private String City;
        @Expose
        @SerializedName("State")
        private String State;
        @Expose
        @SerializedName("PinCodeMasterId")
        private int PinCodeMasterId;
        @Expose
        @SerializedName("Pincode")
        private String Pincode;
        @Expose
        @SerializedName("Email")
        private String Email;
        @Expose
        @SerializedName("MobileNo")
        private String MobileNo;
        @Expose
        @SerializedName("FirstName")
        private String FirstName;
        @Expose
        @SerializedName("IsDelete")
        private boolean IsDelete;
        @Expose
        @SerializedName("IsActive")
        private boolean IsActive;
        @Expose
        @SerializedName("Id")
        private int Id;

        public ArrayList<UserDeliveryDCEntity> getUserDeliveryDC() {
            return UserDeliveryDC;
        }

        public void setUserDeliveryDC(ArrayList<UserDeliveryDCEntity> UserDeliveryDC) {
            this.UserDeliveryDC = UserDeliveryDC;
        }

        public double getLongitude() {
            return Longitude;
        }

        public void setLongitude(double Longitude) {
            this.Longitude = Longitude;
        }

        public double getLatitiute() {
            return Latitiute;
        }

        public void setLatitiute(double Latitiute) {
            this.Latitiute = Latitiute;
        }

        public boolean getIsB2B() {
            return IsB2B;
        }

        public void setIsB2B(boolean IsB2B) {
            this.IsB2B = IsB2B;
        }

        public boolean getIsRegistrationComplete() {
            return IsRegistrationComplete;
        }

        public void setIsRegistrationComplete(boolean IsRegistrationComplete) {
            this.IsRegistrationComplete = IsRegistrationComplete;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public int getPinCodeMasterId() {
            return PinCodeMasterId;
        }

        public void setPinCodeMasterId(int PinCodeMasterId) {
            this.PinCodeMasterId = PinCodeMasterId;
        }

        public String getPincode() {
            return Pincode;
        }

        public void setPincode(String Pincode) {
            this.Pincode = Pincode;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String Email) {
            this.Email = Email;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public void setMobileNo(String MobileNo) {
            this.MobileNo = MobileNo;
        }

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String FirstName) {
            this.FirstName = FirstName;
        }

        public boolean getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(boolean IsDelete) {
            this.IsDelete = IsDelete;
        }

        public boolean getIsActive() {
            return IsActive;
        }

        public void setIsActive(boolean IsActive) {
            this.IsActive = IsActive;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }

    public  class UserDeliveryDCEntity {
        @Expose
        @SerializedName("IsActive")
        private boolean IsActive;
        @Expose
        @SerializedName("IsDelete")
        private boolean IsDelete;
        @Expose
        @SerializedName("Delivery")
        private String Delivery;
        @Expose
        @SerializedName("UserId")
        private int UserId;
        @Expose
        @SerializedName("Id")
        private int Id;

        public boolean getIsActive() {
            return IsActive;
        }

        public void setIsActive(boolean IsActive) {
            this.IsActive = IsActive;
        }

        public boolean getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(boolean IsDelete) {
            this.IsDelete = IsDelete;
        }

        public String getDelivery() {
            return Delivery;
        }

        public void setDelivery(String Delivery) {
            this.Delivery = Delivery;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }
    }
}
