package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class ProductDataModel {

    @SerializedName("ResultItem")
    private ProductResultModel ResultItem;


    @SerializedName("IsSuccess")
    private Boolean IsSuccess;

    @SerializedName("SuccessMessage")
    private String SuccessMessage;


    @SerializedName("ErrorMessage")
    private String ErrorMessage;

    public ProductResultModel getResultItem() {
        return ResultItem;
    }

    public void setResultItem(ProductResultModel resultItem) {
        ResultItem = resultItem;
    }

    public Boolean getSuccess() {
        return IsSuccess;
    }

    public void setSuccess(Boolean success) {
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
}
