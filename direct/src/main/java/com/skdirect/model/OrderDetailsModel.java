package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class OrderDetailsModel {

    @SerializedName("OrderStatusDC")
    private List<OrderStatusDC> OrderStatusDC;
    @SerializedName("TotalItemAmount")
    private double TotalItemAmount;
    @SerializedName("TotalDeliveryCharge")
    private double TotalDeliveryCharge;
    @SerializedName("ShopName")
    private String ShopName;
    @SerializedName("SellerName")
    private String SellerName;
    @SerializedName("ModifiedDate")
    private String ModifiedDate;
    @SerializedName("OrderDate")
    private String OrderDate;
    @SerializedName("Status")
    private int Status;
    @SerializedName("TotalSavingAmount")
    private double TotalSavingAmount;
    @SerializedName("TotalDiscountAmount")
    private double TotalDiscountAmount;
    @SerializedName("TotalPrice")
    private double TotalPrice;
    @SerializedName("PaymentMode")
    private String PaymentMode;
    @SerializedName("Pincode")
    private String Pincode;
    @SerializedName("State")
    private String State;
    @SerializedName("City")
    private String City;
    @SerializedName("AddressThree")
    private String AddressThree;
    @SerializedName("AddressTwo")
    private String AddressTwo;
    @SerializedName("AddressOne")
    private String AddressOne;
    @SerializedName("LastName")
    private String LastName;
    @SerializedName("MiddleName")
    private String MiddleName;
    @SerializedName("FirstName")
    private String FirstName;
    @SerializedName("DeliveryOption")
    private String DeliveryOption;
    @SerializedName("Id")
    private int Id;

    public List<OrderStatusDC> getOrderStatusDC() {
        return OrderStatusDC;
    }

    public void setOrderStatusDC(List<OrderStatusDC> orderStatusDC) {
        OrderStatusDC = orderStatusDC;
    }

    public double getTotalItemAmount() {
        return TotalItemAmount;
    }

    public void setTotalItemAmount(double totalItemAmount) {
        TotalItemAmount = totalItemAmount;
    }

    public double getTotalDeliveryCharge() {
        return TotalDeliveryCharge;
    }

    public void setTotalDeliveryCharge(double totalDeliveryCharge) {
        TotalDeliveryCharge = totalDeliveryCharge;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getSellerName() {
        return SellerName;
    }

    public void setSellerName(String sellerName) {
        SellerName = sellerName;
    }

    public String getModifiedDate() {
        return ModifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        ModifiedDate = modifiedDate;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
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

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddressThree() {
        return AddressThree;
    }

    public void setAddressThree(String addressThree) {
        AddressThree = addressThree;
    }

    public String getAddressTwo() {
        return AddressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        AddressTwo = addressTwo;
    }

    public String getAddressOne() {
        return AddressOne;
    }

    public void setAddressOne(String addressOne) {
        AddressOne = addressOne;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getDeliveryOption() {
        return DeliveryOption;
    }

    public void setDeliveryOption(String deliveryOption) {
        DeliveryOption = deliveryOption;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

}
