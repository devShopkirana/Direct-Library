package com.skdirect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginWithPasswordModel {
    @Expose
    @SerializedName("access_token")
    private String access_token;

    @Expose
    @SerializedName("token_type")
    private String token_type;

    @Expose
    @SerializedName("userName")
    private String userName;

    @Expose
    @SerializedName("error")
    private String error;

    @Expose
    @SerializedName("error_description")
    private String error_description;

    @Expose
    @SerializedName("IsRegistrationComplete")
    private boolean IsRegistrationComplete;

    @Expose
    @SerializedName("IscontactRead")
    private boolean IscontactRead;

    @Expose
    @SerializedName("IsSuperAdmin")
    private boolean IsSuperAdmin;


    @Expose
    @SerializedName("Latitiute")
    private double Latitiute;

    @Expose
    @SerializedName("Longitude")
    private double Longitude;

    public boolean isIscontactRead() {
        return IscontactRead;
    }

    public void setIscontactRead(boolean iscontactRead) {
        IscontactRead = iscontactRead;
    }

    public boolean isSuperAdmin() {
        return IsSuperAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        IsSuperAdmin = superAdmin;
    }

    public double getLatitiute() {
        return Latitiute;
    }

    public void setLatitiute(double latitiute) {
        Latitiute = latitiute;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isRegistrationComplete() {
        return IsRegistrationComplete;
    }

    public void setRegistrationComplete(boolean registrationComplete) {
        IsRegistrationComplete = registrationComplete;
    }
}
