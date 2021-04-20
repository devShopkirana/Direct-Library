package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class PostBrandModel {
    @SerializedName("Keyword")
    private String Keyword;

    @SerializedName("skip")
    private int Skip;

    @SerializedName("take")
    private int Take;

    @SerializedName("categoryid")
    private int categoryid;

    public PostBrandModel(String keyword, int skip, int take, int categoryid) {
        Keyword = keyword;
        Skip = skip;
        Take = take;
        this.categoryid = categoryid;
    }
}
