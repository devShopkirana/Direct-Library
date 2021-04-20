package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class AddViewModel {

    @SerializedName("DecryptSellerId")
    private int  SellerId;

    public AddViewModel(int productId) {
        SellerId = productId;
    }
}
