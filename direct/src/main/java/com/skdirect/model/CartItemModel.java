package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CartItemModel implements Serializable {
    @SerializedName("Cart")
    private ArrayList<CartModel> Cart;
    @SerializedName("EncryptSellerId")
    private String EncryptSellerId;
    @SerializedName("SellerId")
    private int SellerId;
    @SerializedName("CreatedBy")
    private int CreatedBy;
    @SerializedName("CreatedDate")
    private String CreatedDate;
    @SerializedName("IsDelete")
    private boolean IsDelete;
    @SerializedName("TotalQuantity")
    private double TotalQuantity;
    @SerializedName("TotalItems")
    private int TotalItems;
    @SerializedName("TotalDiscount")
    private double TotalDiscount;
    @SerializedName("TotalSavingAmount")
    private double TotalSavingAmount;
    @SerializedName("TotalAmount")
    private double TotalAmount;
    @SerializedName("TotalMrp")
    private double TotalMrp;
    @SerializedName("DeliveryChargePerOrder")
    private double DeliveryChargePerOrder;
    @SerializedName("Id")
    private String Id;

    public CartItemModel() {
    }

    public ArrayList<CartModel> getCart() {
        return Cart;
    }

    public void setCart(ArrayList<CartModel> cart) {
        Cart = cart;
    }

    public String getEncryptSellerId() {
        return EncryptSellerId;
    }

    public void setEncryptSellerId(String encryptSellerId) {
        EncryptSellerId = encryptSellerId;
    }

    public int getSellerId() {
        return SellerId;
    }

    public void setSellerId(int sellerId) {
        SellerId = sellerId;
    }

    public int getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(int createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public boolean isDelete() {
        return IsDelete;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
    }

    public double getTotalQuantity() {
        return TotalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        TotalQuantity = totalQuantity;
    }

    public int getTotalItems() {
        return TotalItems;
    }

    public void setTotalItems(int totalItems) {
        TotalItems = totalItems;
    }

    public double getTotalDiscount() {
        return TotalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        TotalDiscount = totalDiscount;
    }

    public double getTotalSavingAmount() {
        return TotalSavingAmount;
    }

    public void setTotalSavingAmount(double totalSavingAmount) {
        TotalSavingAmount = totalSavingAmount;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }

    public double getTotalMrp() {
        return TotalMrp;
    }

    public void setTotalMrp(double totalMrp) {
        TotalMrp = totalMrp;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public double getDeliveryChargePerOrder() {
        return DeliveryChargePerOrder;
    }
}
