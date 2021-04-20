package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TopNearByItemModel implements Serializable {

    @SerializedName("Id")
    private int Id;

    @SerializedName("ProductName")
    private String ProductName;

    @SerializedName("SellingPrice")
    private double SellingPrice;

    @SerializedName("Mrp")
    private double Mrp;

    @SerializedName("ImagePath")
    private String ImagePath;

    @SerializedName("SellerName")
    private String SellerName;

    @SerializedName("UOM")
    private String UOM;

    @SerializedName("Measurement")
    private int Measurement;

    @SerializedName("NoofView")
    private int NoofView;

    @SerializedName("OffPercentage")
    private double OffPercentage;

    @SerializedName("DiscountAmount")
    private double DiscountAmount;

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public int getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(int measurement) {
        Measurement = measurement;
    }

    public int getNoofView() {
        return NoofView;
    }

    public void setNoofView(int noofView) {
        NoofView = noofView;
    }

    public double getOffPercentage() {
        return OffPercentage;
    }

    public void setOffPercentage(double offPercentage) {
        OffPercentage = offPercentage;
    }

    public double getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        DiscountAmount = discountAmount;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public double getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public double getMrp() {
        return Mrp;
    }

    public void setMrp(double mrp) {
        Mrp = mrp;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getSellerName() {
        return SellerName;
    }

    public void setSellerName(String sellerName) {
        SellerName = sellerName;
    }
}
