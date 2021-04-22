package com.skdirect.api;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonObject;
import com.skdirect.R;
import com.skdirect.model.AddReviewModel;
import com.skdirect.model.AppVersionModel;
import com.skdirect.model.CartMainModel;
import com.skdirect.model.CommonResponseModel;
import com.skdirect.model.MallMainPriceModel;
import com.skdirect.model.MyOrderRequestModel;
import com.skdirect.model.OrderModel;
import com.skdirect.model.OrderStatusMainModel;
import com.skdirect.model.OtpResponceModel;
import com.skdirect.model.OtpVerificationModel;
import com.skdirect.model.PostBrandModel;
import com.skdirect.model.SearchMainModel;
import com.skdirect.model.SearchRequestModel;
import com.skdirect.model.ShopMainModel;
import com.skdirect.model.TokenModel;
import com.skdirect.model.UpdateProfilePostModel;
import com.skdirect.model.response.ApplyOfferResponse;
import com.skdirect.model.response.OfferResponse;
import com.skdirect.utils.DirectSDK;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class CommonClassForAPI {
    private Activity mActivity;
    private static CommonClassForAPI CommonClassForAPI;


    public static CommonClassForAPI getInstance(Activity activity) {
        DirectSDK.getInstance().activity = activity;
        if (CommonClassForAPI == null) {
            CommonClassForAPI = new CommonClassForAPI(activity);
        }
        return CommonClassForAPI;
    }

    public CommonClassForAPI(Activity activity) {
        mActivity = activity;
    }

    public CommonClassForAPI() {

    }

    public void getToken(final DisposableObserver observer, String grant_type, String mobileNumber, String passwordString, boolean ISOTP, boolean ISBUYER, String buyerapp, boolean isDevice, String deviceID, double lat, double log, String pincode, String type,String SOURCEKEY) {
        RestClient.getInstance().getService().getToken(grant_type, mobileNumber, passwordString, ISOTP, ISBUYER, buyerapp, isDevice, deviceID, lat, log, pincode,
                DirectSDK.getInstance().dbHelper.getString(R.string.language_code), type,SOURCEKEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TokenModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(TokenModel o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void getTokenwithphoneNo(final DisposableObserver observer, String password, String mobileNumber, String passwordString, boolean ISOTP, boolean ISBUYER, String buyerapp, boolean isDevice, String deviceID, double lat, double log, String pincode, String type, String phoneno,String SOURCEKEY) {
        RestClient.getInstance().getService().getTokenwithphoneno(password, mobileNumber, passwordString, ISOTP, ISBUYER, buyerapp, isDevice, deviceID, lat, log, pincode,
                DirectSDK.getInstance().dbHelper.getString(R.string.language_code), type, phoneno,SOURCEKEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TokenModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(TokenModel o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void getUpdateToken(final DisposableObserver observer, String fcmId) {
        RestClient.getInstance().getService().getUpdateToken(fcmId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(JsonObject o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void VerfiyOtp(final DisposableObserver observer, OtpVerificationModel otpVerificationModel) {
        RestClient.getInstance().getService().getVerfiyOtp(otpVerificationModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OtpResponceModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(OtpResponceModel o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void UpdateUserProfile(final DisposableObserver observer, UpdateProfilePostModel updateProfilePostModel) {
        RestClient.getInstance().getService().UpdateProfile(updateProfilePostModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonResponseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(CommonResponseModel o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void getCouponList(final DisposableObserver<OfferResponse> observer, int sellerId) {
        RestClient.getInstance().getService().getCouponList(sellerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OfferResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NotNull OfferResponse o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }


    public void applyCoupon(final DisposableObserver<ApplyOfferResponse> observer, int couponId) {
        RestClient.getInstance().getService().applyCoupon(couponId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApplyOfferResponse>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NotNull ApplyOfferResponse o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void getFilterCategoryResult(final DisposableObserver<JsonObject> observer) {
        RestClient.getInstance().getService().getFilterCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NotNull JsonObject o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void getFilterPriceRangeResult(final DisposableObserver<MallMainPriceModel> observer, int CategoryId) {
        RestClient.getInstance().getService().getFilterPriceRange(CategoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MallMainPriceModel>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NotNull MallMainPriceModel o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void getFilterBrandsListResult(final DisposableObserver<JsonObject> observer, PostBrandModel postBrandModel) {
        RestClient.getInstance().getService().getFilterBrandList(postBrandModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NotNull JsonObject o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

  /*  public void getUserDetail(DisposableObserver<UserDetailResponseModel> observer) {
        RestClient.getInstance().getService().getUserDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserDetailResponseModel>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NotNull UserDetailResponseModel o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }*/


    public void deleteCartItems(DisposableObserver<CartMainModel> observer, int productId) {
        RestClient.getInstance().getService().deleteCartItems(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CartMainModel>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NotNull CartMainModel o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void VerfiyPassword(final DisposableObserver observer, JsonObject jsonObject) {
        RestClient.getInstance().getService().getVerifyPassword(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(JsonObject o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void assignCart(final DisposableObserver observer, String cartId) {
        RestClient.getInstance().getService().getAssignCart(cartId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(JsonObject o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void getAllOrderStatusList(final DisposableObserver<OrderStatusMainModel> observer) {
        RestClient.getInstance().getService().getOrderStatusList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderStatusMainModel>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NotNull OrderStatusMainModel o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void GetOrderMaster(final DisposableObserver<OrderModel> observer, MyOrderRequestModel myOrderRequestModel) {
        RestClient.getInstance().getService().GetOrderMaster(myOrderRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<OrderModel>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NotNull OrderModel o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void getSellerListWithItem(final DisposableObserver<SearchMainModel> observer, SearchRequestModel model) {
        RestClient.getInstance().getService().GetSellerListWithItem(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchMainModel>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NotNull SearchMainModel o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void getTopSellerItem(final DisposableObserver<ShopMainModel> observer, int skipCount, int takeCount, String s, String cateogryId) {
        RestClient.getInstance().getService().GetTopSellerItem(skipCount, takeCount, s, cateogryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ShopMainModel>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NotNull ShopMainModel o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }

    public void getRating(final DisposableObserver<JsonObject> observer, AddReviewModel paginationModel) {
        RestClient.getInstance().getService().getRating(paginationModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NotNull JsonObject o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }


    public void getAppInfo(final DisposableObserver observer) {
        RestClient.getInstance().getService().getAppversion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AppVersionModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(AppVersionModel o) {
                        observer.onNext(o);
                    }

                    @Override
                    public void onError(Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        observer.onComplete();
                    }
                });
    }
}