package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class OrderPlaceRequestModel {

    @SerializedName("PaymentMode")
    private final String PaymentMode;
    @SerializedName("DeliveryOption")
    private final int DeliveryOption;
    @SerializedName("MongoId")
    private final String MongoId;
    @SerializedName("UserLocationId")
    private final Integer UserLocationId;
    @SerializedName("MallId")
    private final String MallId;

    public OrderPlaceRequestModel(String paymentMode, int deliveryOption, String mongoId, Integer userLocationId, String mallId) {
        PaymentMode = paymentMode;
        DeliveryOption = deliveryOption;
        MongoId = mongoId;
        UserLocationId = userLocationId;
        MallId = mallId;
    }
}
