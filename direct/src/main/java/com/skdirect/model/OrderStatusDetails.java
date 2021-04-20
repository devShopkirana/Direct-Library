package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderStatusDetails {
    @SerializedName("Id")
    private int Id;

    @SerializedName("Status")
    private String Status;

    @SerializedName("NextStatusId")
    private int NextStatusId;

    @SerializedName("NextStatus")
    private String NextStatus;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getNextStatusId() {
        return NextStatusId;
    }

    public void setNextStatusId(int nextStatusId) {
        NextStatusId = nextStatusId;
    }

    public String getNextStatus() {
        return NextStatus;
    }

    public void setNextStatus(String nextStatus) {
        NextStatus = nextStatus;
    }
}
