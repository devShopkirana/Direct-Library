package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SellerProductModel {

    @SerializedName("SellerProductDC")
    private ArrayList<SellerProductList> sellerProductLists;

    @SerializedName("UserDetailDC")
    private UserDetailModel userDetailModel;

    @SerializedName("DeliveryOptionDC")
    private ArrayList<SellerDeliveryModel> sellerDeliveryModel;

    public ArrayList<SellerDeliveryModel> getSellerDeliveryModel() {
        return sellerDeliveryModel;
    }

    public void setSellerDeliveryModel(ArrayList<SellerDeliveryModel> sellerDeliveryModel) {
        this.sellerDeliveryModel = sellerDeliveryModel;
    }

    public UserDetailModel getUserDetailModel() {
        return userDetailModel;
    }

    public void setUserDetailModel(UserDetailModel userDetailModel) {
        this.userDetailModel = userDetailModel;
    }



    public ArrayList<SellerProductList> getSellerProductLists() {
        return sellerProductLists;
    }

    public void setSellerProductLists(ArrayList<SellerProductList> sellerProductLists) {
        this.sellerProductLists = sellerProductLists;
    }


}
