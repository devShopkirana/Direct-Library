package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class SellerProductList {

    @SerializedName("IsStockRequired")
    private boolean IsStockRequired;
    @SerializedName("Stock")
    private int Stock;
    @SerializedName("SellerId")
    private int SellerId;
    @SerializedName("CreatedBy")
    private int CreatedBy;
    @SerializedName("OffPercentage")
    private double OffPercentage;
    @SerializedName("VariationCount")
    private int VariationCount;
    @SerializedName("Qty")
    private int Qty;
    @SerializedName("ImagePath")
    private String ImagePath;
    @SerializedName("Rate")
    private double Rate;
    @SerializedName("Piece")
    private int Piece;
    @SerializedName("Margin")
    private double Margin;
    @SerializedName("SellingPrice")
    private double SellingPrice;
    @SerializedName("MOQ")
    private int MOQ;
    @SerializedName("ProductMasterId")
    private int ProductMasterId;
    @SerializedName("Measurement")
    private int Measurement;
    @SerializedName("BrandName")
    private String BrandName;
    @SerializedName("BrandId")
    private int BrandId;
    @SerializedName("CategoryName")
    private String CategoryName;
    @SerializedName("CateogryId")
    private int CateogryId;
    @SerializedName("Uom")
    private String Uom;
    @SerializedName("UomId")
    private int UomId;
    @SerializedName("Tax")
    private int Tax;
    @SerializedName("TaxId")
    private int TaxId;
    @SerializedName("Mrp")
    private double Mrp;
    @SerializedName("ProductName")
    private String ProductName;
    @SerializedName("SellerProductId")
    private int SellerProductId;
    @SerializedName("Id")
    private int Id;
    @SerializedName("ParentProductId")
    private int ParentProductId;

    @SerializedName("MaxOrderQuantity")
    private String MaxOrderQuantity;

    public String getMaxOrderQuantity() {
        return MaxOrderQuantity;
    }

    public void setMaxOrderQuantity(String maxOrderQuantity) {
        MaxOrderQuantity = maxOrderQuantity;
    }

    @SerializedName("DiscountAmount")
    private int DiscountAmount;

    public int getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        DiscountAmount = discountAmount;
    }

    public int getParentProductId() {
        return ParentProductId;
    }

    public void setParentProductId(int parentProductId) {
        ParentProductId = parentProductId;
    }

    @SerializedName("NoofView")
    private int NoofView;

    public int getNoofView() {
        return NoofView;
    }

    public void setNoofView(int noofView) {
        NoofView = noofView;
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

    public int getSellerId() {
        return SellerId;
    }

    public void setSellerId(int sellerId) {
        SellerId = sellerId;
    }

    public int getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(int createdBy) {
        CreatedBy = createdBy;
    }

    public double getOffPercentage() {
        return OffPercentage;
    }

    public void setOffPercentage(double offPercentage) {
        OffPercentage = offPercentage;
    }

    public int getVariationCount() {
        return VariationCount;
    }

    public void setVariationCount(int variationCount) {
        VariationCount = variationCount;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public double getRate() {
        return Rate;
    }

    public void setRate(double rate) {
        Rate = rate;
    }

    public int getPiece() {
        return Piece;
    }

    public void setPiece(int piece) {
        Piece = piece;
    }

    public double getMargin() {
        return Margin;
    }

    public void setMargin(double margin) {
        Margin = margin;
    }

    public double getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public int getMOQ() {
        return MOQ;
    }

    public void setMOQ(int MOQ) {
        this.MOQ = MOQ;
    }

    public int getProductMasterId() {
        return ProductMasterId;
    }

    public void setProductMasterId(int productMasterId) {
        ProductMasterId = productMasterId;
    }

    public int getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(int measurement) {
        Measurement = measurement;
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

    public int getCateogryId() {
        return CateogryId;
    }

    public void setCateogryId(int cateogryId) {
        CateogryId = cateogryId;
    }

    public String getUom() {
        return Uom;
    }

    public void setUom(String uom) {
        Uom = uom;
    }

    public int getUomId() {
        return UomId;
    }

    public void setUomId(int uomId) {
        UomId = uomId;
    }

    public int getTax() {
        return Tax;
    }

    public void setTax(int tax) {
        Tax = tax;
    }

    public int getTaxId() {
        return TaxId;
    }

    public void setTaxId(int taxId) {
        TaxId = taxId;
    }

    public double getMrp() {
        return Mrp;
    }

    public void setMrp(double mrp) {
        Mrp = mrp;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getSellerProductId() {
        return SellerProductId;
    }

    public void setSellerProductId(int sellerProductId) {
        SellerProductId = sellerProductId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}

