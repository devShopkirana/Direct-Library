package com.skdirect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenModel {

    @SerializedName(".expires")
    private String expires;
    @SerializedName(".issued")
    private String issued;
    @SerializedName("IsSuperAdmin")
    private boolean IsSuperAdmin;
    @SerializedName("IscontactRead")
    private boolean IscontactRead;
    @SerializedName("BusinessType")
    private String BusinessType;
    @SerializedName("Longitude")
    private String Longitude;
    @SerializedName("Latitiute")
    private String Latitiute;
    @SerializedName("IsRegistrationComplete")
    private boolean IsRegistrationComplete;
    @SerializedName("AspNetuserId")
    private String AspNetuserId;
    @SerializedName("EncodeId")
    private String EncodeId;
    @SerializedName("EncId")
    private String EncId;
    @SerializedName("userName")
    private String userName;
    @SerializedName("expires_in")
    private int expires_in;
    @SerializedName("token_type")
    private String token_type;
    @SerializedName("access_token")
    private String access_token;
    @Expose
    @SerializedName("UserDetail")
    private String UserDetail;

    public String getUserDetail() {
        return UserDetail;
    }

    public void setUserDetail(String userDetail) {
        UserDetail = userDetail;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getIssued() {
        return issued;
    }

    public void setIssued(String issued) {
        this.issued = issued;
    }

    public boolean getIsSuperAdmin() {
        return IsSuperAdmin;
    }

    public void setIsSuperAdmin(boolean isSuperAdmin) {
        IsSuperAdmin = isSuperAdmin;
    }

    public boolean getIscontactRead() {
        return IscontactRead;
    }

    public void setIscontactRead(boolean iscontactRead) {
        IscontactRead = iscontactRead;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitiute() {
        return Latitiute;
    }

    public void setLatitiute(String latitiute) {
        Latitiute = latitiute;
    }

    public boolean getIsRegistrationComplete() {
        return IsRegistrationComplete;
    }

    public void setIsRegistrationComplete(boolean isRegistrationComplete) {
        IsRegistrationComplete = isRegistrationComplete;
    }

    public String getAspNetuserId() {
        return AspNetuserId;
    }

    public void setAspNetuserId(String aspNetuserId) {
        AspNetuserId = aspNetuserId;
    }

    public String getEncodeId() {
        return EncodeId;
    }

    public void setEncodeId(String encodeId) {
        EncodeId = encodeId;
    }

    public String getEncId() {
        return EncId;
    }

    public void setEncId(String encId) {
        EncId = encId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
