package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class PaginationModel {



    @SerializedName("Keyword")
    private String  Keyword;

    @SerializedName("Skip")
    private int Skip;

    @SerializedName("Take")
    private int Take;

    @SerializedName("IsParentCategory")
    private boolean  IsParentCategory;

   /* @SerializedName("ProductId")
    private int  ProductId;*/

   /* public PaginationModel(int productId) {
        ProductId = productId;
    }*/


    /*public PaginationModel(int skip, int take, int parentCategoryId, String categoryName, boolean isParentCategory) {
        Skip = skip;
        Take = take;
        ParentCategoryId = parentCategoryId;
        CategoryName = categoryName;
        IsParentCategory = isParentCategory;
    }*/

  /*  public PaginationModel(int sellerId, int cateogryId, int brandId,String productName, int skip, int take, int parentProductId, String keyword) {
        SellerId = sellerId;
        CateogryId = cateogryId;
        BrandId = brandId;
        ProductName = productName;
        Skip =skip;
        Take = take;
        ParentProductId = parentProductId;
        Keyword = keyword;

    }*/

    public PaginationModel(int skip, int take, String keyword) {
        Skip = skip;
        Take = take;
        Keyword = keyword;
    }

    public PaginationModel(int skip, int take,  boolean isParentCategory) {
        Skip = skip;
        Take = take;
        IsParentCategory = isParentCategory;
    }
}
