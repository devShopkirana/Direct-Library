package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NearByMainModel {

    @SerializedName("IsSuccess")
    private boolean IsSuccess;

    @SerializedName("ResultItem")
    private ArrayList<TopNearByItemModel> ResultItem;

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

    public ArrayList<TopNearByItemModel> getResultItem() {
        return ResultItem;
    }

    public void setResultItem(ArrayList<TopNearByItemModel> resultItem) {
        ResultItem = resultItem;
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
