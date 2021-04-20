package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public  class TopSimilatSellerModel {


    @SerializedName("NoOfView")
    private int NoOfView;
    @SerializedName("Measurement")
    private String Measurement;
    @SerializedName("OffPercentage")
    private String OffPercentage;
    @SerializedName("Uom")
    private String Uom;
    @SerializedName("SellingPrice")
    private double SellingPrice;
    @SerializedName("Mrp")
    private double Mrp;
    @SerializedName("ImagePath")
    private String ImagePath;
    @SerializedName("ProductName")
    private String ProductName;
    @SerializedName("Id")
    private int Id;

    public int getNoOfView() {
        return NoOfView;
    }

    public void setNoOfView(int noOfView) {
        NoOfView = noOfView;
    }

    public String getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(String measurement) {
        Measurement = measurement;
    }

    public String getOffPercentage() {
        return OffPercentage;
    }

    public void setOffPercentage(String offPercentage) {
        OffPercentage = offPercentage;
    }

    public String getUom() {
        return Uom;
    }

    public void setUom(String uom) {
        Uom = uom;
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

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
