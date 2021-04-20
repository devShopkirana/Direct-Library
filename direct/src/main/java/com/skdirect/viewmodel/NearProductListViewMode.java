package com.skdirect.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.skdirect.api.RestClient;
import com.skdirect.model.FilterPostModel;
import com.skdirect.model.NearProductListMainModel;
import com.skdirect.model.NearProductListModel;
import com.skdirect.model.PaginationModel;
import com.skdirect.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearProductListViewMode extends ViewModel {
    final String TAG = getClass().getSimpleName();

    private MutableLiveData<NearProductListMainModel> nearProductListViewModel;
    private MutableLiveData<NearProductListMainModel> filterProductListViewModel;

    public LiveData<NearProductListMainModel> getNearProductList() {
          nearProductListViewModel=null;
            nearProductListViewModel = new MutableLiveData<>();
        return nearProductListViewModel;
    }
    public LiveData<NearProductListMainModel> getFilterProductList() {
        filterProductListViewModel=null;
        filterProductListViewModel = new MutableLiveData<>();
        return filterProductListViewModel;
    }
    public MutableLiveData<NearProductListMainModel> getNearProductListRequest(PaginationModel paginationModel) {
        RestClient.getInstance().getService().getNearItem(paginationModel).enqueue(new Callback<NearProductListMainModel>() {
            @Override
            public void onResponse(Call<NearProductListMainModel> call, Response<NearProductListMainModel> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    nearProductListViewModel.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NearProductListMainModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return nearProductListViewModel;
    }
    public MutableLiveData<NearProductListMainModel> getFilterProductListRequest( FilterPostModel filterPostModel) {
        RestClient.getInstance().getService().getFilterItem(filterPostModel).enqueue(new Callback<NearProductListMainModel>() {
            @Override
            public void onResponse(Call<NearProductListMainModel> call, Response<NearProductListMainModel> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    filterProductListViewModel.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NearProductListMainModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return filterProductListViewModel;
    }
}
