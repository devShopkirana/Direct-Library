package com.skdirect.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class LoginResponseModel {
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
    private ResultItemEntity ResultItem;
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

    public ResultItemEntity getResultItem() {
        return ResultItem;
    }

    public List<String> getResultList() {
        return ResultList;
    }

    public  class ResultItemEntity {
        @Expose
        @SerializedName("AspNetUserId")
        private String AspNetUserId;
        @Expose
        @SerializedName("IsUserExist")
        private boolean IsUserExist;
        @Expose
        @SerializedName("Userid")
        private String Userid;

        public String getAspNetUserId() {
            return AspNetUserId;
        }

        public boolean getIsUserExist() {
            return IsUserExist;
        }

        public String getUserid() {
            return Userid;
        }
    }
}
