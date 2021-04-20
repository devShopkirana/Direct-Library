package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class OrderItemModel {

    @SerializedName("ShopAddressState")
    private String ShopAddressState;
    @SerializedName("ShopAddressCity")
    private String ShopAddressCity;
    @SerializedName("ShopAddressPincode")
    private String ShopAddressPincode;
    @SerializedName("Shopaddressthree")
    private String Shopaddressthree;
    @SerializedName("Shopaddresstwo")
    private String Shopaddresstwo;
    @SerializedName("Shopaddressone")
    private String Shopaddressone;
    @SerializedName("Measurement")
    private int Measurement;
    @SerializedName("UnitofMeasurement")
    private String UnitofMeasurement;
    @SerializedName("CreatedBy")
    private int CreatedBy;
    @SerializedName("TotalDiscountAmount")
    private double TotalDiscountAmount;
    @SerializedName("TotalSaving")
    private double TotalSaving;
    @SerializedName("TotalPrice")
    private double TotalPrice;
    @SerializedName("Mrp")
    private double Mrp;
    @SerializedName("SellingPrice")
    private double SellingPrice;
    @SerializedName("Quantity")
    private int Quantity;
    @SerializedName("ImagePath")
    private String ImagePath;
    @SerializedName("ProductName")
    private String ProductName;
    @SerializedName("ProductMasterId")
    private int ProductMasterId;
    @SerializedName("OrderId")
    private int OrderId;
    @SerializedName("Id")
    private int Id;

    public String getShopAddressState() {
        return ShopAddressState;
    }

    public void setShopAddressState(String shopAddressState) {
        ShopAddressState = shopAddressState;
    }

    public String getShopAddressCity() {
        return ShopAddressCity;
    }

    public void setShopAddressCity(String shopAddressCity) {
        ShopAddressCity = shopAddressCity;
    }

    public String getShopAddressPincode() {
        return ShopAddressPincode;
    }

    public void setShopAddressPincode(String shopAddressPincode) {
        ShopAddressPincode = shopAddressPincode;
    }

    public String getShopaddressthree() {
        return Shopaddressthree;
    }

    public void setShopaddressthree(String shopaddressthree) {
        Shopaddressthree = shopaddressthree;
    }

    public String getShopaddresstwo() {
        return Shopaddresstwo;
    }

    public void setShopaddresstwo(String shopaddresstwo) {
        Shopaddresstwo = shopaddresstwo;
    }

    public String getShopaddressone() {
        return Shopaddressone;
    }

    public void setShopaddressone(String shopaddressone) {
        Shopaddressone = shopaddressone;
    }

    public int getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(int measurement) {
        Measurement = measurement;
    }

    public String getUnitofMeasurement() {
        return UnitofMeasurement;
    }

    public void setUnitofMeasurement(String unitofMeasurement) {
        UnitofMeasurement = unitofMeasurement;
    }

    public int getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(int createdBy) {
        CreatedBy = createdBy;
    }

    public double getTotalDiscountAmount() {
        return TotalDiscountAmount;
    }

    public void setTotalDiscountAmount(double totalDiscountAmount) {
        TotalDiscountAmount = totalDiscountAmount;
    }

    public double getTotalSaving() {
        return TotalSaving;
    }

    public void setTotalSaving(double totalSaving) {
        TotalSaving = totalSaving;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public double getMrp() {
        return Mrp;
    }

    public void setMrp(double mrp) {
        Mrp = mrp;
    }

    public double getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getProductMasterId() {
        return ProductMasterId;
    }

    public void setProductMasterId(int productMasterId) {
        ProductMasterId = productMasterId;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
