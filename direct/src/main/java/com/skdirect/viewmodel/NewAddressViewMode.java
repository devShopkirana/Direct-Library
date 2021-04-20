package com.skdirect.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.skdirect.api.RestClient;
import com.skdirect.model.AllCategoriesModel;
import com.skdirect.model.PaginationModel;
import com.skdirect.model.UpdateEditeAddreshMainModel;
import com.skdirect.utils.Utils;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewAddressViewMode extends ViewModel {
    final String TAG = getClass().getSimpleName();
    private MutableLiveData<JsonObject> mapViewModel;
    private MutableLiveData<JsonObject> addLocationVM;
    private MutableLiveData<UpdateEditeAddreshMainModel> addUpdateLocationVM;

    public LiveData<JsonObject> getMapViewModel() {
        mapViewModel=null;
        mapViewModel = new MutableLiveData<>();
        return mapViewModel;
    }

    public LiveData<JsonObject> getAddLocationVM() {
        addLocationVM=null;
        addLocationVM = new MutableLiveData<>();
        return addLocationVM;
    }

    public LiveData<UpdateEditeAddreshMainModel> getUpdateLocationVM() {
        addUpdateLocationVM=null;
        addUpdateLocationVM = new MutableLiveData<>();
        return addUpdateLocationVM;
    }

    public MutableLiveData<JsonObject> getMapViewModelRequest(double lat, double log) {
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

    public MutableLiveData<JsonObject> getAddLocationVMRequest(JsonObject jsonObject) {
        RestClient.getInstance().getService().AddLocation(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    addLocationVM.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return addLocationVM;
    }

    public MutableLiveData<UpdateEditeAddreshMainModel> getupdateLocationVMRequest(JsonObject jsonArray) {
        RestClient.getInstance().getService().UpdateBuyerLocation(jsonArray).enqueue(new Callback<UpdateEditeAddreshMainModel>() {
            @Override
            public void onResponse(Call<UpdateEditeAddreshMainModel> call, Response<UpdateEditeAddreshMainModel> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    addUpdateLocationVM.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UpdateEditeAddreshMainModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return addUpdateLocationVM;
    }
}
