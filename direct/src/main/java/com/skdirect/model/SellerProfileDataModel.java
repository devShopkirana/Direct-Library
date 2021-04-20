package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class SellerProfileDataModel {

    @SerializedName("SellerId")
    private String SellerId;

    @SerializedName("DecryptsellerId")
    private int DecryptsellerId;

    @SerializedName("ParentProductId")
    private int ParentProductId;

    @SerializedName("Id")
    private int Id;

    @SerializedName("Latitude")
    private double Latitude;

    @SerializedName("Longitude")
    private double Longitude;

    @SerializedName("TagId")
    private int TagId;

    @SerializedName("Keyword")
    private String  Keyword;

    @SerializedName("Skip")
    private int Skip;

    @SerializedName("Take")
    private int Take;

    @SerializedName("ParentCategoryId")
    private int  ParentCategoryId;

    @SerializedName("CategoryName")
    private String  CategoryName;

    @SerializedName("CateogryId")
    private int  CateogryId;

    @SerializedName("IsParentCategory")
    private boolean  IsParentCategory;

    @SerializedName("ProductId")
    private String  ProductId;

    @SerializedName("BrandId")
    private int  BrandId;


    @SerializedName("ProductName")
    private String  ProductName;

    public SellerProfileDataModel(String productId) {
        ProductId = productId;
    }


    public SellerProfileDataModel(int sellerId, int cateogryId, int brandId,String productName, int skip, int take, int parentProductId, String keyword,double lat,double log) {
        DecryptsellerId = sellerId;
        CateogryId = cateogryId;
        BrandId = brandId;
        ProductName = productName;
        Skip =skip;
        Take = take;
        ParentProductId = parentProductId;
        Keyword = keyword;
        Latitude = lat;
        Longitude = log;


    }
}
