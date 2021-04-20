package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public  class ChangePasswordRequestModel {

    @SerializedName("ConfirmPassword")
    private String ConfirmPassword;
    @SerializedName("Password")
    private String Password;
    @SerializedName("Otp")
    private String Otp;

    public ChangePasswordRequestModel(String confirmPassword, String password, String Otp) {
        this.ConfirmPassword = confirmPassword;
        this.Password = password;
        this.Otp = Otp;
    }
}
