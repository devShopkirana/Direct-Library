package com.skdirect.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.skdirect.model.SearchMainModel;
import com.skdirect.model.SearchRequestModel;
import com.skdirect.model.ShopMainModel;

public class SearchViewMode extends ViewModel {
    final String TAG = getClass().getSimpleName();

    private MutableLiveData<SearchMainModel> searchViewModel;

    private MutableLiveData<ShopMainModel> shopDataViewModel;

    public LiveData<SearchMainModel> getSearchViewModel() {
        if (searchViewModel != null) {
            searchViewModel.setValue(null);
        }
        if (searchViewModel == null) {
            searchViewModel = new MutableLiveData<>();
        }
        if (searchViewModel.getValue() != null) {
            searchViewModel.setValue(null);
        }
        return searchViewModel;
    }

    public LiveData<ShopMainModel> getShopDataViewModel() {
        if (shopDataViewModel == null) {
            shopDataViewModel = new MutableLiveData<>();
        }
        if (shopDataViewModel.getValue() != null) {
            shopDataViewModel.setValue(null);
        }
        return shopDataViewModel;
    }

    public MutableLiveData<SearchMainModel> getSearchRequest(SearchRequestModel searchRequestModel) {
//        RestClient.getInstance().getService().GetSellerListWithItem(searchRequestModel).enqueue(new Callback<SearchMainModel>() {
//            @Override
//            public void onResponse(Call<SearchMainModel> call, Response<SearchMainModel> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.e(TAG, "request response=" + response.body());
//                    searchViewModel.setValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SearchMainModel> call, Throwable t) {
//                Log.e(TAG, "onFailure Responce" + t.toString());
//                Utils.hideProgressDialog();
//            }
//        });

        return searchViewModel;
    }


    public MutableLiveData<ShopMainModel> getShopDataViewModelRequest(int skipCount, int takeCount, String s, String cateogryId) {
//        RestClient.getInstance().getService().GetTopSellerItem(skipCount, takeCount, s, cateogryId).enqueue(new Callback<ShopMainModel>() {
//            @Override
//            public void onResponse(Call<ShopMainModel> call, Response<ShopMainModel> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    Log.e(TAG, "request response=" + response.body());
//                    shopDataViewModel.setValue(response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ShopMainModel> call, Throwable t) {
//                Log.e(TAG, "onFailure Responce" + t.toString());
//                Utils.hideProgressDialog();
//            }
//        });
//
        return shopDataViewModel;
    }
}
