package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyOrderModel implements Serializable {


    @SerializedName("ShopName")
    private String ShopName;
    @SerializedName("ProductName")
    private String ProductName;
    @SerializedName("ModifiedDate")
    private String ModifiedDate;
    @SerializedName("Status")
    private int Status;
    @SerializedName("Rating")
    private int Rating;
    @SerializedName("SellerName")
    private String SellerName;
    @SerializedName("DeliveryOption")
    private String DeliveryOption;
    @SerializedName("ImagePath")
    private String ImagePath;
    @SerializedName("ItemCount")
    private int ItemCount;
    @SerializedName("Pincode")
    private String Pincode;
    @SerializedName("BuyerName")
    private String BuyerName;
    @SerializedName("CreatedDate")
    private String CreatedDate;
    @SerializedName("PhoneNumber")
    private String PhoneNumber;
    @SerializedName("DeliveryCharges")
    private double DeliveryCharges;
    @SerializedName("TotalSavingAmount")
    private double TotalSavingAmount;
    @SerializedName("TotalDiscountAmount")
    private double TotalDiscountAmount;
    @SerializedName("TotalPrice")
    private double TotalPrice;
    @SerializedName("OrderStatus")
    private String OrderStatus;
    @SerializedName("Id")
    private int Id;

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public String getSellerName() {
        return SellerName;
    }

    public void setSellerName(String sellerName) {
        SellerName = sellerName;
    }

    public String getDeliveryOption() {
        return DeliveryOption;
    }

    public void setDeliveryOption(String deliveryOption) {
        DeliveryOption = deliveryOption;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public int getItemCount() {
        return ItemCount;
    }

    public void setItemCount(int itemCount) {
        ItemCount = itemCount;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getBuyerName() {
        return BuyerName;
    }

    public void setBuyerName(String buyerName) {
        BuyerName = buyerName;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public double getDeliveryCharges() {
        return DeliveryCharges;
    }

    public void setDeliveryCharges(double deliveryCharges) {
        DeliveryCharges = deliveryCharges;
    }

    public double getTotalSavingAmount() {
        return TotalSavingAmount;
    }

    public void setTotalSavingAmount(double totalSavingAmount) {
        TotalSavingAmount = totalSavingAmount;
    }

    public double getTotalDiscountAmount() {
        return TotalDiscountAmount;
    }

    public void setTotalDiscountAmount(double totalDiscountAmount) {
        TotalDiscountAmount = totalDiscountAmount;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
