package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UpdateTokenModel implements Serializable {
    @SerializedName("FcmId")
    private String FcmId;

    public UpdateTokenModel(String fcmId) {
        FcmId = fcmId;
    }

    public String getFcmId() {
        return FcmId;
    }

    public void setFcmId(String fcmId) {
        FcmId = fcmId;
    }


}
