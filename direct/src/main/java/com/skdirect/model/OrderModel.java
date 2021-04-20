package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderModel implements Serializable {

    @SerializedName("IsSuccess")
    private boolean IsSuccess;

    @SerializedName("SuccessMessage")
    private String SuccessMessage;

    @SerializedName("ErrorMessage")
    private String ErrorMessage;

    @SerializedName("ResultItem")
    private ArrayList<MyOrderModel> myOrderModelsList;

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
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

    public ArrayList<MyOrderModel> getMyOrderModelsList() {
        return myOrderModelsList;
    }

    public void setMyOrderModelsList(ArrayList<MyOrderModel> myOrderModelsList) {
        this.myOrderModelsList = myOrderModelsList;
    }
}
