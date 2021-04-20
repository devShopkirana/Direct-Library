package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NearProductListMainModel {

    @SerializedName("IsSuccess")
    private boolean IsSuccess;

    @SerializedName("ResultItem")
    private ArrayList<NearProductListModel> ResultItem;

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

    public ArrayList<NearProductListModel> getResultItem() {
        return ResultItem;
    }

    public void setResultItem(ArrayList<NearProductListModel> resultItem) {
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
