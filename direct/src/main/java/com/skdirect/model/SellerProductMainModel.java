package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class SellerProductMainModel {

    @SerializedName("IsSuccess")
    private boolean IsSuccess;

    @SerializedName("ResultItem")
    private SellerProductModel sellerProductModel;

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

    public SellerProductModel getSellerProductModel() {
        return sellerProductModel;
    }

    public void setSellerProductModel(SellerProductModel sellerProductModel) {
        this.sellerProductModel = sellerProductModel;
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
