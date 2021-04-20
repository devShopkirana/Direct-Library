package com.skdirect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpVerificationModel {
    @Expose
    @SerializedName("MobileNumber")
    private String MobileNumber;

    @Expose
    @SerializedName("Otp")
    private String Otp;
    @Expose
    @SerializedName("DeviceId")
    private String DeviceId;

    public OtpVerificationModel(String mobileNumber, String otp, String DeviceId) {
        this.MobileNumber = mobileNumber;
        this.Otp = otp;
        this.DeviceId = DeviceId;
    }


}
