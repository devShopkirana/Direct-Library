package com.skdirect.api;

import com.google.gson.JsonObject;
import com.skdirect.model.AddCartItemModel;
import com.skdirect.model.AddReviewModel;
import com.skdirect.model.AddViewMainModel;
import com.skdirect.model.AddViewModel;
import com.skdirect.model.AllCategoresMainModel;
import com.skdirect.model.AppVersionModel;
import com.skdirect.model.CartItemModel;
import com.skdirect.model.CartMainModel;
import com.skdirect.model.ChangePasswordRequestModel;
import com.skdirect.model.CommonResponseModel;
import com.skdirect.model.DeliveryMainModel;
import com.skdirect.model.FilterPostModel;
import com.skdirect.model.GenerateOtpModel;
import com.skdirect.model.GenerateOtpResponseModel;
import com.skdirect.model.ItemAddModel;
import com.skdirect.model.MainLocationModel;
import com.skdirect.model.MainSimilarTopSellerModel;
import com.skdirect.model.MainTopSimilarSellerModel;
import com.skdirect.model.MallMainModel;
import com.skdirect.model.MallMainModelBolleanResult;
import com.skdirect.model.MallMainPriceModel;
import com.skdirect.model.MyOrderRequestModel;
import com.skdirect.model.NearByMainModel;
import com.skdirect.model.NearBySellerMainModel;
import com.skdirect.model.NearProductListMainModel;
import com.skdirect.model.OrderDetailsModel;
import com.skdirect.model.OrderItemModel;
import com.skdirect.model.OrderModel;
import com.skdirect.model.OrderPlaceMainModel;
import com.skdirect.model.OrderPlaceRequestModel;
import com.skdirect.model.OrderStatusMainModel;
import com.skdirect.model.OtpResponceModel;
import com.skdirect.model.OtpVerificationModel;
import com.skdirect.model.PaginationModel;
import com.skdirect.model.PostBrandModel;
import com.skdirect.model.ProductDataModel;
import com.skdirect.model.ReviewMainModel;
import com.skdirect.model.SearchMainModel;
import com.skdirect.model.SearchRequestModel;
import com.skdirect.model.SellerDetailsModel;
import com.skdirect.model.SellerProductMainModel;
import com.skdirect.model.SellerProfileDataModel;
import com.skdirect.model.ShopMainModel;
import com.skdirect.model.TokenModel;
import com.skdirect.model.TopSellerMainModel;
import com.skdirect.model.UpdateEditeAddreshMainModel;
import com.skdirect.model.UpdateProfilePostModel;
import com.skdirect.model.UserLocationModel;
import com.skdirect.model.response.ApplyOfferResponse;
import com.skdirect.model.response.OfferResponse;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIServices {

    @GET("api/NativeBuyer/Common/GetAppInfo")
    Call<AppVersionModel> getAppversion();

    /*@POST("api/Notification/UpdateFcmId")*/
    @GET("api/NativeBuyer/Common/UpdateFcmId")
    Observable<JsonObject> getUpdateToken(@Query("fcmId") String fcmId);

    @POST("/api/NativeBuyer/Login/verifyOtp")
    Observable<OtpResponceModel> getVerfiyOtp(@Body OtpVerificationModel OtpVerificationModel);

    @POST("/api/NativeBuyer/Login/GenerateOtp")
    Call<GenerateOtpResponseModel> GenerateOtp(@Body GenerateOtpModel generateOtpModel);

    @FormUrlEncoded
    @POST("/token")
    Observable<TokenModel> getToken(@Field("grant_type") String grant_type, @Field("username") String username, @Field("password") String password, @Field("ISOTP") boolean isOTp, @Field("ISBUYER") boolean isBuyer, @Field("LOGINTYPE") String LOGINTYPE, @Field("ISDEVICE") boolean ISDEVICE, @Field("DEVICEID") String DEVICEID, @Field("LAT") double LAT, @Field("LNG") double LNG, @Field("PINCODE") String pincode, @Field("lang") String lang, @Field("TYPE") String TYPE);

    @FormUrlEncoded
    @POST("/token")
    Observable<TokenModel> getTokenwithphoneno(@Field("grant_type") String grant_type, @Field("username") String username, @Field("password") String password, @Field("ISOTP") boolean isOTp, @Field("ISBUYER") boolean isBuyer, @Field("LOGINTYPE") String LOGINTYPE, @Field("ISDEVICE") boolean ISDEVICE, @Field("DEVICEID") String DEVICEID, @Field("LAT") double LAT, @Field("LNG") double LNG, @Field("PINCODE") String pincode, @Field("lang") String lang, @Field("TYPE") String TYPE, @Field("PHONENUMBER") String PHONENUMBER);


   /* @GET("api/NativeBuyer/BuyerProfile/GetUserDetail")
    Call<UserDetailResponseModel> GetUserDetail();

    @GET("api/NativeBuyer/BuyerProfile/GetUserDetail")
    Observable<UserDetailResponseModel> getUserDetail();*/

    /*Home Page APi*/
    @GET("api/NativeBuyer/AppHome/GetNearByItem")
    Call<NearByMainModel> GetTopNearByItem();

    @GET("api/NativeBuyer/AppHome/GetTopSeller")
    Call<TopSellerMainModel> GetTopSeller();

    @GET("api/NativeBuyer/AppHome/GetNearBySeller")
    Call<TopSellerMainModel> GetNearBySeller();

    @GET("api/NativeBuyer/AppHome/GetMostVisitedSeller")
    Call<TopSellerMainModel> GetMostVisitedSeller();

    @GET("api/NativeBuyer/AppHome/GetNewSeller")
    Call<TopSellerMainModel> GetNewSeller();

    @GET("api/NativeBuyer/AppHome/GetTopCategory")
    Call<AllCategoresMainModel> GetTopCategory();

    /*APP Home Filter*/

    @POST("api/NativeBuyer/AppHomeFilter/GetNearByItemFilter")
    Call<NearProductListMainModel> getNearItem(@Body PaginationModel paginationModel);

    @POST("api/NativeBuyer/GlobalFilter/ApplyFilter")
    Call<NearProductListMainModel> getFilterItem(@Body FilterPostModel filterPostModel);

    @POST("api/NativeBuyer/AppHomeFilter/GetTopSellerFilter")
    Call<NearBySellerMainModel> GetSellerListForBuyer(@Body PaginationModel paginationModel);

    @POST("api/NativeBuyer/AppHomeFilter/GetCategorybyfilter")
    Call<AllCategoresMainModel> GetCategorybyfilter(@Body PaginationModel paginationModel);

    @POST("api/NativeBuyer/AppHomeFilter/GetNearBySellerFilter")
    Call<NearBySellerMainModel> GetNearBySellerFilter(@Body PaginationModel paginationModel);

    @POST("api/NativeBuyer/AppHomeFilter/GetMostVisitedSellerFilter")
    Call<NearBySellerMainModel> GetMostVisitedSellerFilter(@Body PaginationModel paginationModel);

    @POST("api/NativeBuyer/AppHomeFilter/GetNewSellerFilter")
    Call<NearBySellerMainModel> GetNewSellerFilter(@Body PaginationModel paginationModel);


    @GET("/api/NativeBuyer/ShopList/GetShopList")
    Observable<ShopMainModel> GetTopSellerItem(@Query("Skip") int Skip, @Query("Take") int take, @Query("Keyword") String Keyword, @Query("categoryId") String categoryId);

    @POST("/api/NativeBuyer/ItemList/GetSellerListWithItem")
    Observable<SearchMainModel> GetSellerListWithItem(@Body SearchRequestModel paginationModel);


    @GET("api/NativeProductDetail/GetSellerProductById/{GetSellerProductById}")
    Call<ProductDataModel> GetSellerProductById(@Path("GetSellerProductById") int GetSellerProductById);

    @GET("api/NativeProductDetail/GetTopSimilarproduct/{GetSellerProductById}")
    Call<MainTopSimilarSellerModel> GetTopSimilarproduct(@Path("GetSellerProductById") int GetSellerProductById);


    @GET("api/NativeProductDetail/GetSimilarProductTopSeller/{GetSimilarProductTopSeller}")
    Call<MainSimilarTopSellerModel> GetSimilarProductTopSeller(@Path("GetSimilarProductTopSeller") int GetSellerProductById);

    @GET("api/NativeProductDetail/GetMoreSimilarSellerProduct/{GetMoreSimilarSellerProduct}")
    Call<MainTopSimilarSellerModel> GetMoreSimilarSellerProduct(@Path("GetMoreSimilarSellerProduct") int GetSellerProductById);

    @GET("api/NativeBuyer/CartOverview/GetCartItems")
    Call<CartItemModel> GetCartItems();

    @GET("api/NativeBuyer/SellerProfile/AddProductView")
    Call<AddViewMainModel> AddProductView(@Query("productId") int productID);

    @GET("api/NativeBuyer/SellerProfile/GetSellerDetail/{GetSellerDetail}")
    Call<SellerDetailsModel> GetSellerDetail(@Path("GetSellerDetail") int GetSellerDetail);

    @POST("api/NativeBuyer/SellerProfile/GetSellerProduct")
    Call<SellerProductMainModel> GetSellerProduct(@Body SellerProfileDataModel paginationModel);

    @POST("api/NativeBuyer/SellerProfile/AddStoreView")
    Call<AddViewMainModel> AddStoreView(@Body AddViewModel paginationModel);

    @POST("api/NativeBuyer/SellerProfile/AddCart")
    Call<AddCartItemModel> AddCart(@Body ItemAddModel itemAddModel);

    @GET("api/NativeBuyer/SellerProfile/GetCartItems")
    Call<CartMainModel> GetCartItem();

    @GET("api/NativeBuyer/SellerProfile/ClearCart")
    Call<Object> ClearCart(@Query("Id") String id);

    @GET("api/NativeBuyer/CartOverview/GetCartItems")
    Call<CartMainModel> CartItems();

    @GET("api/NativeBuyer/SellerProfile/DeleteCartItems")
    Observable<CartMainModel> deleteCartItems(@Query("id") int id);

    @GET("api/NativeBuyer/Address/GetLocation")
    Call<JsonObject> GetLocation(@Query("lat") double lat, @Query("lng") double log);

    @GET("api/NativeBuyer/Address/GetAddress")
    Call<MainLocationModel> GetUserLocation();

    @GET("api/NativeBuyer/Order/GetDeliveryOption")
    Call<DeliveryMainModel> GetDeliveryOption(@Query("SellerId") int SellerId);

    @GET("api/NativeBuyer/Order/GetCheckOutItem")
    Call<JsonObject> GetCheckOutItem();

    @POST("api/NativeBuyer/Order/PlaceOrder")
    Call<OrderPlaceMainModel> PlaceOrder(@Body OrderPlaceRequestModel placeRequestModel);

    @POST("api/NativeBuyer/Address/AddBuyerLocation")
    Call<JsonObject> AddLocation(@Body JsonObject jsonObject);

    @POST("api/NativeBuyer/Address/UpdateBuyerLocation")
    Call<UpdateEditeAddreshMainModel> UpdateBuyerLocation(@Body JsonObject jsonObject);

    @POST("api/Buyer/SellerProductDetail/AddCartItem/")
    Call<AddCartItemModel> AddCartItem(@Body ItemAddModel itemAddModel);


    @POST("api/buyer/Profile/UpdateUserDetail")
    Call<Boolean> UpdateUserDetail(@Body JsonObject jsonObject);


    @POST("api/NativeBuyer/BuyerProfile/UpdateBuyerProfile")
    Observable<CommonResponseModel> UpdateProfile(@Body UpdateProfilePostModel updateProfilePostModel);


    @GET("api/buyer/Profile/MakeDefaultAddress/{MakeDefaultAddress}")
    Call<Boolean> MakeDefaultAddress(@Path("MakeDefaultAddress") int UserDetailId);


    @POST("api/buyer/Profile/UpdateUserLocation")
    Call<Boolean> UpdateUserLocation(@Body ArrayList<UserLocationModel> locationModels);


    @POST("api/NativeBuyer/BuyerProfile/ChangePassword")
    Call<CommonResponseModel> ChangePassword(@Body ChangePasswordRequestModel passwordRequestModel);

    @POST("api/NativeBuyer/MyOrder/GetMyOrder")
    Observable<OrderModel> GetOrderMaster(@Body MyOrderRequestModel myOrderRequestModel);

    @POST("api/NativeBuyer/MyOrder/Rating")
    Observable<JsonObject> getRating(@Body AddReviewModel reViewViewMode);

    @GET("api/buyer/MyOrder/GetOrderDetailProcess")
    Call<OrderDetailsModel> GetOrderDetailProcess(@Query("OrderId") int OrderId);

    @GET("api/buyer/MyOrder/GetOrderDetails/{OrderId}")
    Call<ArrayList<OrderItemModel>> GetOrderDetails(@Query("OrderId") int OrderId);

    @GET("api/NativeBuyer/MyOrder/CancelOrder")
    Call<MallMainModelBolleanResult> CancelOrder(@Query("OrderId") int OrderId);

    @GET("/api/NativeBuyer/Location/SetLocation")
    Call<CommonResponseModel> setLocation(@Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("api/NativeBuyer/Mall/GetMall")
    Call<MallMainModel> getMall();

    @GET("api/NativeBuyer/SellerCoupon/GetCouponList")
    Observable<OfferResponse> getCouponList(@Query("sellerid") int sellerId);

    @GET("/api/NativeBuyer/SellerCoupon/ApplyCoupon/{couponId}")
    Observable<ApplyOfferResponse> applyCoupon(@Path("couponId") int couponId);

    @GET("api/NativeBuyer/GlobalFilter/GetCategoryList")
    Observable<JsonObject> getFilterCategory();

    @GET("api/NativeBuyer/BuyerProfile/SendOtp")
    Call<CommonResponseModel> sendotp();

    @GET("api/NativeBuyer/GlobalFilter/GetPriceRangeAsync")
    Observable<MallMainPriceModel> getFilterPriceRange(@Query("categoryid") int categoryId);

    @POST("api/NativeBuyer/GlobalFilter/GetBrandListAsync")
    Observable<JsonObject> getFilterBrandList(@Body PostBrandModel postBrandModel);

    @POST("api/NativeBuyer/Login/VerifyByPassword")
    Observable<JsonObject> getVerifyPassword(@Body JsonObject jsonObject);

    @GET("api/NativeBuyer/SellerProfile/AsssignCart")
    Observable<JsonObject> getAssignCart(@Query("id") String id);

    @GET("api/NativeBuyer/MyOrder/GetOrderStatus")
    Observable<OrderStatusMainModel> getOrderStatusList();
}