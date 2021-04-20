package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddCartItemModel {
    @SerializedName("ResultItem")
    private ResultItem resultItem;

    @SerializedName("SuccessMessage")
    private String SuccessMessage;

    @SerializedName("ErrorMessage")
    private String ErrorMessage;

    @SerializedName("IsSuccess")
    private boolean IsSuccess;

    public String getSuccessMessage() {
        return SuccessMessage;
    }

    public void setSuccessMessage(String successMessage) {
        SuccessMessage = successMessage;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }

    public ResultItem getResultItem() {
        return resultItem;
    }

    public void setResultItem(ResultItem resultItem) {
        this.resultItem = resultItem;
    }

    public class ResultItem {
        @SerializedName("ShoppingCartItem")
        private ArrayList<ShoppingCartItem> ShoppingCartItem;
        @SerializedName("EncryptSellerId")
        private String EncryptSellerId;
        @SerializedName("SellerId")
        private int SellerId;
        @SerializedName("ModifiedBy")
        private int ModifiedBy;
        @SerializedName("CreatedBy")
        private int CreatedBy;
        @SerializedName("CreatedDate")
        private String CreatedDate;
        @SerializedName("ModifiedDate")
        private String ModifiedDate;
        @SerializedName("CookieValue")
        private String CookieValue;
        @SerializedName("Id")
        private String Id;

        public ArrayList<AddCartItemModel.ShoppingCartItem> getShoppingCartItem() {
            return ShoppingCartItem;
        }

        public void setShoppingCartItem(ArrayList<AddCartItemModel.ShoppingCartItem> shoppingCartItem) {
            ShoppingCartItem = shoppingCartItem;
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

        public int getModifiedBy() {
            return ModifiedBy;
        }

        public void setModifiedBy(int modifiedBy) {
            ModifiedBy = modifiedBy;
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

        public String getModifiedDate() {
            return ModifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            ModifiedDate = modifiedDate;
        }

        public String getCookieValue() {
            return CookieValue;
        }

        public void setCookieValue(String cookieValue) {
            CookieValue = cookieValue;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }
    }

    public class ShoppingCartItem {
        @SerializedName("IsActive")
        private boolean IsActive;
        @SerializedName("IsStockRequired")
        private boolean IsStockRequired;
        @SerializedName("Stock")
        private int Stock;
        @SerializedName("Measurement")
        private int Measurement;
        @SerializedName("Uom")
        private int Uom;
        @SerializedName("TotalDiscountAmount")
        private int TotalDiscountAmount;
        @SerializedName("TotalDiscount")
        private int TotalDiscount;
        @SerializedName("TotalSaving")
        private int TotalSaving;
        @SerializedName("IsDelete")
        private boolean IsDelete;
        @SerializedName("OffPercentage")
        private int OffPercentage;
        @SerializedName("TotalPrice")
        private int TotalPrice;
        @SerializedName("price")
        private int price;
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
        private int Margin;
        @SerializedName("Mrp")
        private int Mrp;
        @SerializedName("MOQ")
        private int MOQ;
        @SerializedName("Id")
        private int Id;

        public boolean isActive() {
            return IsActive;
        }

        public void setActive(boolean active) {
            IsActive = active;
        }

        public boolean isStockRequired() {
            return IsStockRequired;
        }

        public void setStockRequired(boolean stockRequired) {
            IsStockRequired = stockRequired;
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

        public int getUom() {
            return Uom;
        }

        public void setUom(int uom) {
            Uom = uom;
        }

        public int getTotalDiscountAmount() {
            return TotalDiscountAmount;
        }

        public void setTotalDiscountAmount(int totalDiscountAmount) {
            TotalDiscountAmount = totalDiscountAmount;
        }

        public int getTotalDiscount() {
            return TotalDiscount;
        }

        public void setTotalDiscount(int totalDiscount) {
            TotalDiscount = totalDiscount;
        }

        public int getTotalSaving() {
            return TotalSaving;
        }

        public void setTotalSaving(int totalSaving) {
            TotalSaving = totalSaving;
        }

        public boolean isDelete() {
            return IsDelete;
        }

        public void setDelete(boolean delete) {
            IsDelete = delete;
        }

        public int getOffPercentage() {
            return OffPercentage;
        }

        public void setOffPercentage(int offPercentage) {
            OffPercentage = offPercentage;
        }

        public int getTotalPrice() {
            return TotalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            TotalPrice = totalPrice;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
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

        public int getMargin() {
            return Margin;
        }

        public void setMargin(int margin) {
            Margin = margin;
        }

        public int getMrp() {
            return Mrp;
        }

        public void setMrp(int mrp) {
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
    }
}