package com.skdirect.model.response;

import com.google.gson.annotations.SerializedName;

public class ApplyOfferResponse {

    @SerializedName("ErrorMessage")
    private String ErrorMessage;
    @SerializedName("SuccessMessage")
    private String SuccessMessage;
    @SerializedName("IsSuccess")
    private boolean isSuccess;
    @SerializedName("ResultItem")
    private boolean ResultItem;
    @SerializedName("ResultList")
    private String ResultList;

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }

    public String getSuccessMessage() {
        return SuccessMessage;
    }

    public void setSuccessMessage(String SuccessMessage) {
        this.SuccessMessage = SuccessMessage;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean getResultItem() {
        return ResultItem;
    }

    public void setResultItem(boolean ResultItem) {
        this.ResultItem = ResultItem;
    }

    public String getResultList() {
        return ResultList;
    }

    public void setResultList(String ResultList) {
        this.ResultList = ResultList;
    }
}
