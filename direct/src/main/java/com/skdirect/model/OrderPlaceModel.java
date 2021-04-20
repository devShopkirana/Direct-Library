package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class OrderPlaceModel {

    @SerializedName("Playstorelink")
    private String Playstorelink;
    @SerializedName("OrderId")
    private int OrderId;
    @SerializedName("TotalAmount")
    private double TotalAmount;
    @SerializedName("receiverName")
    private String receiverName;
    @SerializedName("receiverMobile")
    private String receiverMobile;
    @SerializedName("senderName")
    private String senderName;
    @SerializedName("senderMobile")
    private String senderMobile;

    public String getPlaystorelink() {
        return Playstorelink;
    }

    public void setPlaystorelink(String playstorelink) {
        Playstorelink = playstorelink;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderMobile() {
        return senderMobile;
    }

    public void setSenderMobile(String senderMobile) {
        this.senderMobile = senderMobile;
    }
}
