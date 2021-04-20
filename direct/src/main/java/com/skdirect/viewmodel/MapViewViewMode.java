package com.skdirect.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.skdirect.api.RestClient;
import com.skdirect.model.AllCategoriesModel;
import com.skdirect.model.CommonResponseModel;
import com.skdirect.model.PaginationModel;
import com.skdirect.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapViewViewMode extends ViewModel {
    final String TAG = getClass().getSimpleName();

    private MutableLiveData<JsonObject> mapViewModel;
    private MutableLiveData<CommonResponseModel> setLocationLiveData;

    public LiveData<JsonObject> getMapViewModel() {
       if(mapViewModel==null)
        mapViewModel = new MutableLiveData<>();
        return mapViewModel;
    }
    public LiveData<CommonResponseModel> setLocationViewModel() {
        if(setLocationLiveData==null)
            setLocationLiveData = new MutableLiveData<>();
        return setLocationLiveData;
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
    public MutableLiveData<CommonResponseModel> setLocationViewModelRequest(double lat,double log) {
        RestClient.getInstance().getService().setLocation(lat,log).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    setLocationLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return setLocationLiveData;
    }
}
