package com.skdirect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class GenerateOtpResponseModel {



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
    private ResultItemBean ResultItem;
    @Expose
    @SerializedName("ResultList")
    private List<String> ResultList;

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public String getSuccessMessage() {
        return SuccessMessage;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }

    public ResultItemBean getResultItem() {
        return ResultItem;
    }

    public List<String> getResultList() {
        return ResultList;
    }

    public  class ResultItemBean {
        @Expose
        @SerializedName("Result")
        private boolean Result;
        @Expose
        @SerializedName("UserExists")
        private boolean UserExists;

        public boolean isResult() {
            return Result;
        }

        public boolean isUserExists() {
            return UserExists;
        }
    }
}
