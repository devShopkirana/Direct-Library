package com.skdirect.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.skdirect.api.RestClient;
import com.skdirect.model.NearBySellerMainModel;
import com.skdirect.model.PaginationModel;
import com.skdirect.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostVisitedViewMode extends ViewModel {
    final String TAG = getClass().getSimpleName();

    private MutableLiveData<NearBySellerMainModel> sellerProductListVM;

    public LiveData<NearBySellerMainModel> getSellerProductListVM() {
        sellerProductListVM=null;
        sellerProductListVM = new MutableLiveData<>();
        return sellerProductListVM;
    }

    public MutableLiveData<NearBySellerMainModel> getSellerProductListRequest(PaginationModel paginationModel) {
        RestClient.getInstance().getService().GetMostVisitedSellerFilter(paginationModel).enqueue(new Callback<NearBySellerMainModel>() {
            @Override
            public void onResponse(Call<NearBySellerMainModel> call, Response<NearBySellerMainModel> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    sellerProductListVM.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NearBySellerMainModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return sellerProductListVM;
    }
}
