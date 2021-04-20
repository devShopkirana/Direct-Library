package com.skdirect.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.skdirect.api.RestClient;
import com.skdirect.model.CartMainModel;
import com.skdirect.model.UserDetailResponseModel;
import com.skdirect.utils.Utils;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewMode extends ViewModel {
    final String TAG = getClass().getSimpleName();
    private MutableLiveData<UserDetailResponseModel> userDetailViewModel;

    private MutableLiveData<CartMainModel> CardItemVM;
    private MutableLiveData<JsonObject> mapViewModel;

    public LiveData<CartMainModel> getCartItemsVM() {
        CardItemVM = null;
        CardItemVM = new MutableLiveData<>();
        return CardItemVM;
    }

    public LiveData<JsonObject> getMapViewModel() {
        mapViewModel = null;
        mapViewModel = new MutableLiveData<>();
        return mapViewModel;
    }

    public MutableLiveData<CartMainModel> getCartItemsRequest() {
        RestClient.getInstance().getService().GetCartItem().enqueue(new Callback<CartMainModel>() {
            @Override
            public void onResponse(@NotNull Call<CartMainModel> call, @NotNull Response<CartMainModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "request response=" + response.body());
                    CardItemVM.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CartMainModel> call, @NotNull Throwable t) {
                Log.e(TAG, "onFailure Responce" + t.toString());
                Utils.hideProgressDialog();
            }
        });

        return CardItemVM;
    }

    public MutableLiveData<JsonObject> getMapViewModelRequest(double lat, double log) {
        RestClient.getInstance().getService().GetLocation(lat, log).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "request response=" + response.body());
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


}
