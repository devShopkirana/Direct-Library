package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class MyOrderRequestModel {
    @SerializedName("Filter")
    private String Filter;
    @SerializedName("SellerId")
    private int SellerId;
    @SerializedName("Buyerid")
    private String buyerid;
    @SerializedName("Take")
    private int Take;
    @SerializedName("Skip")
    private int Skip;
    @SerializedName("OrderStatus")
    private String OrderStatus;
    @SerializedName("Id")
    private int Id;

    public MyOrderRequestModel(String filter, int sellerId, int take, int skip, String OrderStatus, int id,String Buyerid) {

        Filter = filter;
        SellerId = sellerId;
        buyerid = Buyerid;
        Take = take;
        Skip = skip;
        this.OrderStatus = OrderStatus;
        Id = id;
    }
}
