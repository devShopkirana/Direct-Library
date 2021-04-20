package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class SellerDetailsModel {


    @SerializedName("IsSuccess")
    private boolean IsSuccess;

    @SerializedName("ResultItem")
    private SellerInfoModel sellerInfoModel;

    @SerializedName("SuccessMessage")
    private String SuccessMessage;

    @SerializedName("ErrorMessage")
    private String ErrorMessage;

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }

    public SellerInfoModel getSellerInfoModel() {
        return sellerInfoModel;
    }

    public void setSellerInfoModel(SellerInfoModel sellerInfoModel) {
        this.sellerInfoModel = sellerInfoModel;
    }

    public String getSuccessMessage() {
        return SuccessMessage;
    }

    public void setSuccessMessage(String successMessage) {
        SuccessMessage = successMessage;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }
}
