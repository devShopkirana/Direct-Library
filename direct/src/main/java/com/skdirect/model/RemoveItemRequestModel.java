package com.skdirect.model;

public class RemoveItemRequestModel {

    private String CookieValue;
    private int  Id;

    public RemoveItemRequestModel(String cookieValue, int id) {
        CookieValue = cookieValue;
        Id = id;
    }
}
