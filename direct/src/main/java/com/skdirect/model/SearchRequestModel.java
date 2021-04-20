package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class SearchRequestModel {


    @SerializedName("Skip")
    private int Skip;

    @SerializedName("Take")
    private int Take;

    @SerializedName("Keyword")
    private String Keyword;

    @SerializedName("CateogryId")
    private String CateogryId;

    @SerializedName("Pincode")
    private String Pincode;



    public SearchRequestModel(String cateogryId,int skip,int take,String keyword,String pinCode){
        CateogryId =cateogryId;
        Skip= skip;
        Take = take;
        Keyword = keyword;
        Pincode = pinCode;

    }



}
