package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class CartItemRequestModel {
    @SerializedName("CookieValue")
    private String CookieValue;

    public CartItemRequestModel(String cookieValue) {
        CookieValue = cookieValue;
    }

    public String getCookieValue() {
        return CookieValue;
    }

    public void setCookieValue(String cookieValue) {
        CookieValue = cookieValue;
    }
}
