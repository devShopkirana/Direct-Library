package com.skdirect.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.skdirect.api.RestClient;
import com.skdirect.model.DeliveryMainModel;
import com.skdirect.model.MainLocationModel;
import com.skdirect.model.OrderPlaceMainModel;
import com.skdirect.model.OrderPlaceRequestModel;
import com.skdirect.utils.MyApplication;
import com.skdirect.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentViewMode extends ViewModel {
    final String TAG = getClass().getSimpleName();
    private MutableLiveData<MainLocationModel> userLocationVM;
    private MutableLiveData<DeliveryMainModel> DeliveryOptionVM;
    private MutableLiveData<JsonObject> checkOutItemVM;
    private MutableLiveData<JsonObject> mapViewModel;
    private MutableLiveData<OrderPlaceMainModel> orderPlaceVM;
    private MutableLiveData<Object> clearCartDataVM;

    public LiveData<MainLocationModel> getUserLocationVM() {
        userLocationVM=null;
        userLocationVM = new MutableLiveData<>();
        return userLocationVM;
    }

    public LiveData<DeliveryMainModel> getDeliveryOptionVM() {
        DeliveryOptionVM=null;
        DeliveryOptionVM = new MutableLiveData<>();
        return DeliveryOptionVM;
    }

    public LiveData<JsonObject> getCheckOutItemVM() {
        checkOutItemVM=null;
        checkOutItemVM = new MutableLiveData<>();
        return checkOutItemVM;
    }

    public LiveData<JsonObject> getMapViewModel() {
        mapViewModel=null;
        mapViewModel = new MutableLiveData<>();
        return mapViewModel;
    }

    public LiveData<OrderPlaceMainModel> getOrderPlaceVM() {
        orderPlaceVM=null;
        orderPlaceVM = new MutableLiveData<>();
        return orderPlaceVM;
    }

    public LiveData<Object> getClearCartData() {
        clearCartDataVM=null;
        clearCartDataVM = new MutableLiveData<>();
        return clearCartDataVM;
    }

    public MutableLiveData<MainLocationModel> getUserLocationVMRequest() {
        RestClient.getInstance().getService().GetUserLocation().enqueue(new Callback<MainLocationModel>() {
            @Override
            public void onResponse(Call<MainLocationModel> call, Response<MainLocationModel> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    userLocationVM.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MainLocationModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return userLocationVM;
    }

    public MutableLiveData<DeliveryMainModel> getDeliveryOptionVMRequest(int sellerID) {
        RestClient.getInstance().getService().GetDeliveryOption(sellerID).enqueue(new Callback<DeliveryMainModel>() {
            @Override
            public void onResponse(Call<DeliveryMainModel> call, Response<DeliveryMainModel> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    DeliveryOptionVM.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<DeliveryMainModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + t.toString());
                Utils.hideProgressDialog();
            }
        });

        return DeliveryOptionVM;
    }

    public MutableLiveData<JsonObject> getCheckOutItemVMRequest() {
        RestClient.getInstance().getService().GetCheckOutItem().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    checkOutItemVM.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + t.toString());
                Utils.hideProgressDialog();
            }
        });

        return checkOutItemVM;
    }


    public MutableLiveData<JsonObject> getMapViewModelRequest(double lat,double log) {
        RestClient.getInstance().getService().GetLocation(lat,log).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    mapViewModel.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return mapViewModel;
    }

    public MutableLiveData<OrderPlaceMainModel> getOrderPlaceVMRequest(OrderPlaceRequestModel placeRequestModel) {
        RestClient.getInstance().getService().PlaceOrder(placeRequestModel).enqueue(new Callback<OrderPlaceMainModel>() {
            @Override
            public void onResponse(Call<OrderPlaceMainModel> call, Response<OrderPlaceMainModel> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "request response="+response.body());
                    orderPlaceVM.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<OrderPlaceMainModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return orderPlaceVM;
    }

    public MutableLiveData<Object> getClearCartDataRequest() {
        String cartId = MyApplication.getInstance().cartRepository.getCartId();
        RestClient.getInstance().getService().ClearCart(cartId).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "request response=" + response.body());
                    clearCartDataVM.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return clearCartDataVM;
    }
}
