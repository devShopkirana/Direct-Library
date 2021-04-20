package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductResultModel {
    @SerializedName("ImageList")
    public ArrayList<ImageListModel> ImageList;
    @SerializedName("ProductVariantSpecification")
    public ArrayList<ProductVariantSpecificationModel> ProductVariantSpecification;
    @SerializedName("VariationList")
    public ArrayList<VariationListModel> VariationListModel;
    @SerializedName("ProductVariantAttributeDC")
    private ArrayList<ProductVariantAttributeDCModel> ProductVariantAttributeDC;
    @SerializedName("DeliveryOptionDC")
    private ArrayList<DeliveryOptionModel> DeliveryOptionDC;
    @SerializedName("EncryptSellerId")
    public String EncryptSellerId;
    @SerializedName("StockId")
    public int StockId;
    @SerializedName("SellerId")
    public int SellerId;
    @SerializedName("OffPercentage")
    public double OffPercentage;
    @SerializedName("SellingPrice")
    public double SellingPrice;
    @SerializedName("Stock")
    public int Stock;
    @SerializedName("IsActive")
    public boolean IsActive;
    @SerializedName("IsDelete")
    public boolean IsDelete;
    @SerializedName("IsStockRequired")
    public boolean IsStockRequired;
    @SerializedName("ParentProductId")
    public int ParentProductId;
    @SerializedName("UomValue")
    public String UomValue;
    @SerializedName("Uom")
    public String Uom;
    @SerializedName("Tax")
    public double Tax;
    @SerializedName("TaxGroupId")
    public int TaxGroupId;
    @SerializedName("WeightInKg")
    public double WeightInKg;
    @SerializedName("Length")
    public double Length;
    @SerializedName("Width")
    public double Width;
    @SerializedName("Height")
    public double Height;
    @SerializedName("Measurement")
    public int Measurement;
    @SerializedName("Mrp")
    public double Mrp;
    @SerializedName("IsApproved")
    public boolean IsApproved;
    @SerializedName("CategoryName")
    public String CategoryName;
    @SerializedName("CategoryId")
    public int CategoryId;
    @SerializedName("ProductName")
    public String ProductName;
    @SerializedName("ProductMasterId")
    public int ProductMasterId;
    @SerializedName("Id")
    public int Id;
    @SerializedName("AddressOne")
    public String AddressOne;
    @SerializedName("AddressTwo")
    public String AddressTwo;
    @SerializedName("Pincode")
    public String Pincode;
    @SerializedName("State")
    public String State;
    @SerializedName("DiscountAmount")
    public double DiscountAmount;
    @SerializedName("ShopName")
    public String ShopName;
    public int qty;
    @SerializedName("MaxOrderQuantity")
    private String MaxOrderQuantity;

    public String getMaxOrderQuantity() {
        return MaxOrderQuantity;
    }

    public void setMaxOrderQuantity(String maxOrderQuantity) {
        MaxOrderQuantity = maxOrderQuantity;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getAddressOne() {
        return AddressOne;
    }

    public void setAddressOne(String addressOne) {
        AddressOne = addressOne;
    }

    public String getAddressTwo() {
        return AddressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        AddressTwo = addressTwo;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public double getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        DiscountAmount = discountAmount;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

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

    public ArrayList<VariationListModel> getVariationModelList() {
        return VariationListModel;
    }

    public void setVariationModelList(ArrayList<VariationListModel> variationList) {
        VariationListModel = variationList;
    }

    public List<ProductVariantAttributeDCModel> getProductVariantAttributeDC() {
        return ProductVariantAttributeDC;
    }

    public void setProductVariantAttributeDC(ArrayList<ProductVariantAttributeDCModel> productVariantAttributeDC) {
        ProductVariantAttributeDC = productVariantAttributeDC;
    }

    public String getEncryptSellerId() {
        return EncryptSellerId;
    }

    public void setEncryptSellerId(String encryptSellerId) {
        EncryptSellerId = encryptSellerId;
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

    public String getUom() {
        return Uom;
    }

    public void setUom(String uom) {
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

    public ArrayList<DeliveryOptionModel> getDeliveryOptionDC() {
        return DeliveryOptionDC;
    }

    public void setDeliveryOptionDC(ArrayList<DeliveryOptionModel> deliveryOptionDC) {
        DeliveryOptionDC = deliveryOptionDC;
    }
}
