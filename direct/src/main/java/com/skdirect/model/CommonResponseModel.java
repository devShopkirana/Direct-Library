package com.skdirect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class CommonResponseModel {
    @Expose
    @SerializedName("ErrorMessage")
    private String ErrorMessage;
    @Expose
    @SerializedName("SuccessMessage")
    private String SuccessMessage;
    @Expose
    @SerializedName("IsSuccess")
    private boolean IsSuccess;
    @Expose
    @SerializedName("ResultItem")
    private boolean ResultItem;
    @Expose
    @SerializedName("ResultList")
    private String ResultList;

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public String getSuccessMessage() {
        return SuccessMessage;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }

    public boolean isResultItem() {
        return ResultItem;
    }

    public String getResultList() {
        return ResultList;
    }
}
