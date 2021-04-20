package com.skdirect.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.skdirect.utils.DataConverter;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "ShoppingCart")
public class CartModel implements Serializable {
    @TypeConverters(DataConverter.class)
    @SerializedName("ShoppingCartVariantAtrribute")
    private ArrayList<String> ShoppingCartVariantAtrribute;
    @SerializedName("TotalMrp")
    private double TotalMrp;
    @SerializedName("ShopName")
    private String ShopName;
    @SerializedName("IsActive")
    private boolean isActive;
    @SerializedName("IsStockRequired")
    private boolean isStockRequired;
    @SerializedName("Stock")
    private int Stock;
    @SerializedName("Measurement")
    private int Measurement;
    @SerializedName("Uom")
    private String Uom;
    @SerializedName("ImagePath")
    private String ImagePath;
    @SerializedName("TotalDiscountAmount")
    private int TotalDiscountAmount;
    @SerializedName("ProductName")
    private String ProductName;
    @SerializedName("TotalDiscount")
    private int TotalDiscount;

    @SerializedName("TotalSaving")
    private double TotalSaving;

    @SerializedName("IsDelete")
    private boolean isDelete;

    @SerializedName("OffPercentage")
    private double OffPercentage;

    @SerializedName("TotalPrice")
    private double TotalPrice;

    @SerializedName("price")
    private double price;

    @SerializedName("Quantity")
    private int Quantity;

    @SerializedName("CreatedBy")
    private int CreatedBy;

    @SerializedName("CreatedDate")
    private String CreatedDate;

    @SerializedName("SellerId")
    private int SellerId;

    @SerializedName("BuyerId")
    private int BuyerId;

    @SerializedName("ProductMasterId")
    private int ProductMasterId;

    @SerializedName("Margin")
    private double Margin;

    @SerializedName("Mrp")
    private double Mrp;
    @SerializedName("MOQ")
    private int MOQ;
    @PrimaryKey
    @SerializedName("Id")
    private int Id;
    private String carId;

    @SerializedName("DiscountAmount")
    private int DiscountAmount;

    @SerializedName("MaxOrderQuantity")
    private String MaxOrderQuantity;

    public String getMaxOrderQuantity() {
        return MaxOrderQuantity;
    }

    public void setMaxOrderQuantity(String maxOrderQuantity) {
        MaxOrderQuantity = maxOrderQuantity;
    }


    public int getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        DiscountAmount = discountAmount;
    }

    public CartModel() {
    }

    @Ignore
    public CartModel(ArrayList<String> shoppingCartVariantAtrribute, double totalMrp, String shopName, boolean isActive, boolean isStockRequired, int stock, int measurement, String uom, String imagePath, int totalDiscountAmount, String productName, int totalDiscount, int totalSaving, boolean isDelete, double offPercentage, int totalPrice, int price, int quantity, int createdBy, String createdDate, int sellerId, int buyerId, int productMasterId, double margin, double mrp, int MOQ, int id) {
        ShoppingCartVariantAtrribute = shoppingCartVariantAtrribute;
        TotalMrp = totalMrp;
        ShopName = shopName;
        this.isActive = isActive;
        this.isStockRequired = isStockRequired;
        Stock = stock;
        Measurement = measurement;
        Uom = uom;
        ImagePath = imagePath;
        TotalDiscountAmount = totalDiscountAmount;
        ProductName = productName;
        TotalDiscount = totalDiscount;
        TotalSaving = totalSaving;
        this.isDelete = isDelete;
        OffPercentage = offPercentage;
        TotalPrice = totalPrice;
        this.price = price;
        Quantity = quantity;
        CreatedBy = createdBy;
        CreatedDate = createdDate;
        SellerId = sellerId;
        BuyerId = buyerId;
        ProductMasterId = productMasterId;
        Margin = margin;
        Mrp = mrp;
        this.MOQ = MOQ;
        Id = id;
    }


    public ArrayList<String> getShoppingCartVariantAtrribute() {
        return ShoppingCartVariantAtrribute;
    }

    public void setShoppingCartVariantAtrribute(ArrayList<String> shoppingCartVariantAtrribute) {
        ShoppingCartVariantAtrribute = shoppingCartVariantAtrribute;
    }

    public double getTotalMrp() {
        return TotalMrp;
    }

    public void setTotalMrp(double totalMrp) {
        TotalMrp = totalMrp;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean isStockRequired() {
        return isStockRequired;
    }

    public void setStockRequired(boolean stockRequired) {
        this.isStockRequired = stockRequired;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public int getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(int measurement) {
        Measurement = measurement;
    }

    public String getUom() {
        return Uom;
    }

    public void setUom(String uom) {
        Uom = uom;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public int getTotalDiscountAmount() {
        return TotalDiscountAmount;
    }

    public void setTotalDiscountAmount(int totalDiscountAmount) {
        TotalDiscountAmount = totalDiscountAmount;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getTotalDiscount() {
        return TotalDiscount;
    }

    public void setTotalDiscount(int totalDiscount) {
        TotalDiscount = totalDiscount;
    }

    public double getTotalSaving() {
        return TotalSaving;
    }

    public void setTotalSaving(double totalSaving) {
        TotalSaving = totalSaving;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        this.isDelete = delete;
    }

    public double getOffPercentage() {
        return OffPercentage;
    }

    public void setOffPercentage(double offPercentage) {
        OffPercentage = offPercentage;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
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

    public int getSellerId() {
        return SellerId;
    }

    public void setSellerId(int sellerId) {
        SellerId = sellerId;
    }

    public int getBuyerId() {
        return BuyerId;
    }

    public void setBuyerId(int buyerId) {
        BuyerId = buyerId;
    }

    public int getProductMasterId() {
        return ProductMasterId;
    }

    public void setProductMasterId(int productMasterId) {
        ProductMasterId = productMasterId;
    }

    public double getMargin() {
        return Margin;
    }

    public void setMargin(double margin) {
        Margin = margin;
    }

    public double getMrp() {
        return Mrp;
    }

    public void setMrp(double mrp) {
        Mrp = mrp;
    }

    public int getMOQ() {
        return MOQ;
    }

    public void setMOQ(int MOQ) {
        this.MOQ = MOQ;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}
