package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class PriceDetails {

    @SerializedName("min")
    private int min;

    @SerializedName("max")
    private int max;

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
