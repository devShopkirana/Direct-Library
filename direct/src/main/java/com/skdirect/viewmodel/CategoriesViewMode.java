package com.skdirect.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.skdirect.api.RestClient;
import com.skdirect.model.AllCategoresMainModel;
import com.skdirect.model.AllCategoriesModel;
import com.skdirect.model.NearProductListModel;
import com.skdirect.model.PaginationModel;
import com.skdirect.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesViewMode extends ViewModel {
    final String TAG = getClass().getSimpleName();

    private MutableLiveData<AllCategoresMainModel> categoriesViewModel;

    public LiveData<AllCategoresMainModel> getCategoriesViewModel() {
       if(categoriesViewModel==null)
        categoriesViewModel = new MutableLiveData<>();
        if (categoriesViewModel.getValue() != null)
        {
            categoriesViewModel.setValue(null);
        }
        return categoriesViewModel;
    }

    public MutableLiveData<AllCategoresMainModel> getCategoriesViewModelRequest(PaginationModel paginationModel) {
        RestClient.getInstance().getService().GetCategorybyfilter(paginationModel).enqueue(new Callback<AllCategoresMainModel>() {
            @Override
            public void onResponse(Call<AllCategoresMainModel> call, Response<AllCategoresMainModel> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    categoriesViewModel.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AllCategoresMainModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return categoriesViewModel;
    }
}
