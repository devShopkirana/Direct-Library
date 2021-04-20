package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class SellerDeliveryModel {

    @SerializedName("Delivery")
    private String Delivery;
    @SerializedName("Id")
    private int Id;


    public String getDelivery() {
        return Delivery;
    }

    public void setDelivery(String delivery) {
        Delivery = delivery;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


}
