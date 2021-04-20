package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class GenerateOtpModel {
    @SerializedName("MobileNumber")
    private final String MobileNumber;
    @SerializedName("DeviceId")
    private final String DeviceId;
    @SerializedName("Fcmid")
    private String Fcmid;

    public GenerateOtpModel(String mobileNumber, String deviceId) {
        this.MobileNumber = mobileNumber;
        this.DeviceId = deviceId;
    }

    public GenerateOtpModel(String mobileNumber, String deviceId, String fcmid) {
        MobileNumber = mobileNumber;
        DeviceId = deviceId;
        Fcmid = fcmid;
    }
}
