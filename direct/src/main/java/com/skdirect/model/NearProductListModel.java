package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class NearProductListModel {

    @SerializedName("Id")
    private int Id;

    @SerializedName("SellerProductId")
    private int SellerProductId;

    @SerializedName("ProductName")
    private String ProductName;

    @SerializedName("Description")
    private String Description;


    @SerializedName("Mrp")
    private double Mrp;

    @SerializedName("TaxId")
    private int TaxId;

    @SerializedName("Tax")
    private int Tax;

    @SerializedName("UomId")
    private int UomId;

    @SerializedName("Uom")
    private String Uom;

    @SerializedName("CateogryId")
    private int CateogryId;

    @SerializedName("CategoryName")
    private String CategoryName;

    @SerializedName("BrandId")
    private int BrandId;

    @SerializedName("BrandName")
    private String BrandName;

    @SerializedName("Measurement")
    private int Measurement;

    @SerializedName("ProductMasterId")
    private int ProductMasterId;

    @SerializedName("MOQ")
    private int MOQ;

    @SerializedName("SellingPrice")
    private double SellingPrice;

    @SerializedName("Margin")
    private double Margin;

    @SerializedName("Piece")
    private double Piece;

    @SerializedName("Rate")
    private double Rate;


    @SerializedName("ImagePath")
    private String ImagePath;


    @SerializedName("Qty")
    private int Qty;


    @SerializedName("VariationCount")
    private int VariationCount;

    @SerializedName("OffPercentage")
    private double OffPercentage;

    @SerializedName("CreatedBy")
    private int CreatedBy;


    @SerializedName("ParentProductId")
    private int ParentProductId;

    @SerializedName("SellerId")
    private int SellerId;

    @SerializedName("EncryptSellerId")
    private int EncryptSellerId;

    @SerializedName("IsActive")
    private boolean IsActive;

    @SerializedName("Stock")
    private int Stock;

    @SerializedName("IsStockRequired")
    private boolean IsStockRequired;

    @SerializedName("ShopName")
    private String ShopName;

    @SerializedName("NoofView")
    private int NoofView;

    public int getNoofView() {
        return NoofView;
    }

    public void setNoofView(int noofView) {
        NoofView = noofView;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getSellerProductId() {
        return SellerProductId;
    }

    public void setSellerProductId(int sellerProductId) {
        SellerProductId = sellerProductId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getMrp() {
        return Mrp;
    }

    public void setMrp(double mrp) {
        Mrp = mrp;
    }

    public int getTaxId() {
        return TaxId;
    }

    public void setTaxId(int taxId) {
        TaxId = taxId;
    }

    public int getTax() {
        return Tax;
    }

    public void setTax(int tax) {
        Tax = tax;
    }

    public int getUomId() {
        return UomId;
    }

    public void setUomId(int uomId) {
        UomId = uomId;
    }

    public String getUom() {
        return Uom;
    }

    public void setUom(String uom) {
        Uom = uom;
    }

    public int getCateogryId() {
        return CateogryId;
    }

    public void setCateogryId(int cateogryId) {
        CateogryId = cateogryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getBrandId() {
        return BrandId;
    }

    public void setBrandId(int brandId) {
        BrandId = brandId;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public int getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(int measurement) {
        Measurement = measurement;
    }

    public int getProductMasterId() {
        return ProductMasterId;
    }

    public void setProductMasterId(int productMasterId) {
        ProductMasterId = productMasterId;
    }

    public int getMOQ() {
        return MOQ;
    }

    public void setMOQ(int MOQ) {
        this.MOQ = MOQ;
    }

    public double getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public double getMargin() {
        return Margin;
    }

    public void setMargin(double margin) {
        Margin = margin;
    }

    public double getPiece() {
        return Piece;
    }

    public void setPiece(double piece) {
        Piece = piece;
    }

    public double getRate() {
        return Rate;
    }

    public void setRate(double rate) {
        Rate = rate;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public int getVariationCount() {
        return VariationCount;
    }

    public void setVariationCount(int variationCount) {
        VariationCount = variationCount;
    }

    public double getOffPercentage() {
        return OffPercentage;
    }

    public void setOffPercentage(double offPercentage) {
        OffPercentage = offPercentage;
    }

    public int getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(int createdBy) {
        CreatedBy = createdBy;
    }

    public int getParentProductId() {
        return ParentProductId;
    }

    public void setParentProductId(int parentProductId) {
        ParentProductId = parentProductId;
    }

    public int getSellerId() {
        return SellerId;
    }

    public void setSellerId(int sellerId) {
        SellerId = sellerId;
    }

    public int getEncryptSellerId() {
        return EncryptSellerId;
    }

    public void setEncryptSellerId(int encryptSellerId) {
        EncryptSellerId = encryptSellerId;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public boolean getIsStockRequired() {
        return IsStockRequired;
    }

    public void setIsStockRequired(boolean isStockRequired) {
        IsStockRequired = isStockRequired;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }
}
