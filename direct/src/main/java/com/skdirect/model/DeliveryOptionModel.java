package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class DeliveryOptionModel {

    @SerializedName("DeliveryChage")
    private double DeliveryChage;
    @SerializedName("Delivery")
    private String Delivery;
    @SerializedName("Id")
    private int Id;

    public double getDeliveryChage() {
        return DeliveryChage;
    }

    public void setDeliveryChage(double deliveryChage) {
        DeliveryChage = deliveryChage;
    }

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
