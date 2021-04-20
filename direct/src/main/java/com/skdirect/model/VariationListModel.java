package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public  class VariationListModel {
    @SerializedName("ImageList")
    private ArrayList<ImageListModel> ImageList;
    @SerializedName("ProductVariantSpecification")
    private ArrayList<ProductVariantSpecificationModel> ProductVariantSpecification;
    @SerializedName("ProductVariantAttributeDC")
    private ArrayList<ProductVariantAttributeDCModel> ProductVariantAttributeDC;
    @SerializedName("StockId")
    private int StockId;
    @SerializedName("SellerId")
    private int SellerId;
    @SerializedName("OffPercentage")
    private double OffPercentage;
    @SerializedName("SellingPrice")
    private double SellingPrice;
    @SerializedName("Stock")
    private int Stock;
    @SerializedName("IsActive")
    private boolean IsActive;
    @SerializedName("IsDelete")
    private boolean IsDelete;
    @SerializedName("IsStockRequired")
    private boolean IsStockRequired;
    @SerializedName("ParentProductId")
    private int ParentProductId;
    @SerializedName("UomValue")
    private String UomValue;
    @SerializedName("Uom")
    private int Uom;
    @SerializedName("Tax")
    private double Tax;
    @SerializedName("TaxGroupId")
    private int TaxGroupId;
    @SerializedName("WeightInKg")
    private double WeightInKg;
    @SerializedName("Length")
    private double Length;
    @SerializedName("Width")
    private double Width;
    @SerializedName("Height")
    private double Height;
    @SerializedName("Measurement")
    private int Measurement;
    @SerializedName("Mrp")
    private double Mrp;
    @SerializedName("IsApproved")
    private boolean IsApproved;
    @SerializedName("BrandName")
    private String BrandName;
    @SerializedName("BrandId")
    private int BrandId;
    @SerializedName("CategoryName")
    private String CategoryName;
    @SerializedName("CategoryId")
    private int CategoryId;
    @SerializedName("ProductName")
    private String ProductName;
    @SerializedName("ProductMasterId")
    private int ProductMasterId;
    @SerializedName("Id")
    private int Id;

    public double getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        DiscountAmount = discountAmount;
    }

    @SerializedName("DiscountAmount")
    public double DiscountAmount;

    public ArrayList<ImageListModel> getImageList() {
        return ImageList;
    }

    public void setImageList(ArrayList<ImageListModel> imageList) {
        ImageList = imageList;
    }

    public ArrayList<ProductVariantSpecificationModel> getProductVariantSpecification() {
        return ProductVariantSpecification;
    }

    public void setProductVariantSpecification(ArrayList<ProductVariantSpecificationModel> productVariantSpecification) {
        ProductVariantSpecification = productVariantSpecification;
    }

    public ArrayList<ProductVariantAttributeDCModel> getProductVariantAttributeDC() {
        return ProductVariantAttributeDC;
    }

    public void setProductVariantAttributeDC(ArrayList<ProductVariantAttributeDCModel> productVariantAttributeDC) {
        ProductVariantAttributeDC = productVariantAttributeDC;
    }

    public int getStockId() {
        return StockId;
    }

    public void setStockId(int stockId) {
        StockId = stockId;
    }

    public int getSellerId() {
        return SellerId;
    }

    public void setSellerId(int sellerId) {
        SellerId = sellerId;
    }

    public double getOffPercentage() {
        return OffPercentage;
    }

    public void setOffPercentage(double offPercentage) {
        OffPercentage = offPercentage;
    }

    public double getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public boolean isDelete() {
        return IsDelete;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
    }

    public boolean isStockRequired() {
        return IsStockRequired;
    }

    public void setStockRequired(boolean stockRequired) {
        IsStockRequired = stockRequired;
    }

    public int getParentProductId() {
        return ParentProductId;
    }

    public void setParentProductId(int parentProductId) {
        ParentProductId = parentProductId;
    }

    public String getUomValue() {
        return UomValue;
    }

    public void setUomValue(String uomValue) {
        UomValue = uomValue;
    }

    public int getUom() {
        return Uom;
    }

    public void setUom(int uom) {
        Uom = uom;
    }

    public double getTax() {
        return Tax;
    }

    public void setTax(double tax) {
        Tax = tax;
    }

    public int getTaxGroupId() {
        return TaxGroupId;
    }

    public void setTaxGroupId(int taxGroupId) {
        TaxGroupId = taxGroupId;
    }

    public double getWeightInKg() {
        return WeightInKg;
    }

    public void setWeightInKg(double weightInKg) {
        WeightInKg = weightInKg;
    }

    public double getLength() {
        return Length;
    }

    public void setLength(double length) {
        Length = length;
    }

    public double getWidth() {
        return Width;
    }

    public void setWidth(double width) {
        Width = width;
    }

    public double getHeight() {
        return Height;
    }

    public void setHeight(double height) {
        Height = height;
    }

    public int getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(int measurement) {
        Measurement = measurement;
    }

    public double getMrp() {
        return Mrp;
    }

    public void setMrp(double mrp) {
        Mrp = mrp;
    }

    public boolean isApproved() {
        return IsApproved;
    }

    public void setApproved(boolean approved) {
        IsApproved = approved;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public int getBrandId() {
        return BrandId;
    }

    public void setBrandId(int brandId) {
        BrandId = brandId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
