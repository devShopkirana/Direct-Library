package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderStatusDC implements Serializable {

    @SerializedName("CreatedDate")
    private String CreatedDate;
    @SerializedName("Status")
    private String Status;

    public OrderStatusDC(String createdDate, String status) {
        CreatedDate = createdDate;
        Status = status;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
