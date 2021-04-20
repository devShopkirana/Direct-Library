package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FilterPostModel {

    @SerializedName("categoryid")
    private int categoryid;

    @SerializedName("isB2b")
    private boolean isB2b;

    @SerializedName("skip")
    private int skip;

    @SerializedName("take")
    private int take;

    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;

    @SerializedName("brandList")
    private ArrayList<String> brandList;

    @SerializedName("priceRange")
    private ArrayList<Integer> priceRange;

    @SerializedName("discount")
    private String  discount;

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public FilterPostModel(int categoryid, boolean isB2b, int skip, int take, double lat, double lng, ArrayList<String> brandList, ArrayList<Integer> priceRange, String discount) {
        this.categoryid = categoryid;
        this.isB2b = isB2b;
        this.skip = skip;
        this.take = take;
        this.lat = lat;
        this.lng = lng;
        this.brandList = brandList;
        this.priceRange = priceRange;
        this.discount = discount;
    }
}
