package com.skdirect.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.skdirect.api.RestClient;
import com.skdirect.model.AllCategoresMainModel;
import com.skdirect.model.CustomerDataModel;
import com.skdirect.model.MallMainModel;
import com.skdirect.model.NearByMainModel;
import com.skdirect.model.TopSellerMainModel;
import com.skdirect.model.UserDetailResponseModel;
import com.skdirect.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    final String TAG = getClass().getSimpleName();
    private MutableLiveData<UserDetailResponseModel> userDetailViewModel;
    private MutableLiveData<NearByMainModel> topNearByItem;
    private MutableLiveData<TopSellerMainModel> topSellerLiveData;
    private MutableLiveData<TopSellerMainModel> nearBySellerLiveData;
    private MutableLiveData<TopSellerMainModel> mostVisitedSellerLiveData;
    private MutableLiveData<TopSellerMainModel> newSellerLiveData;
    private MutableLiveData<AllCategoresMainModel> allCategoriesLiveData;

    private MutableLiveData<MallMainModel> mallDataViewModel;


    /*public LiveData<UserDetailResponseModel> GetUserDetail() {
        if (userDetailViewModel == null) {
            userDetailViewModel = new MutableLiveData<>();
            userDetailViewModel = getUserDetailRequest();
        }

        return userDetailViewModel;
    }*/

    public LiveData<NearByMainModel> GetTopNearByItem() {
        if (topNearByItem == null) {
            topNearByItem = new MutableLiveData<>();
        }
        topNearByItem = getGetTopNearByItemRequest();
        return topNearByItem;
    }


    public LiveData<TopSellerMainModel> GetTopSellerLiveData() {
        if (topSellerLiveData == null) {
            topSellerLiveData = new MutableLiveData<>();

        }
        topSellerLiveData = GetTopSellerLiveRequest();
        return topSellerLiveData;
    }

    public LiveData<TopSellerMainModel> GetNearSellerLiveData() {
        if (nearBySellerLiveData == null) {
            nearBySellerLiveData = new MutableLiveData<>();

        }
        nearBySellerLiveData = GetNearBYSellerLiveRequest();
        return nearBySellerLiveData;
    }

    public LiveData<TopSellerMainModel> GetMostVisitedSellerLiveData() {
        if (mostVisitedSellerLiveData == null) {
            mostVisitedSellerLiveData = new MutableLiveData<>();

        }
        mostVisitedSellerLiveData = GetMostVisitedSellerLiveRequest();
        return mostVisitedSellerLiveData;
    }

    public LiveData<TopSellerMainModel> GetNewSellerLiveData() {
        if (newSellerLiveData == null) {
            newSellerLiveData = new MutableLiveData<>();

        }
        newSellerLiveData = GetSellerSellerLiveRequest();
        return newSellerLiveData;
    }

    public LiveData<AllCategoresMainModel> getAllCategoriesLiveData() {
        if (allCategoriesLiveData == null) {
            allCategoriesLiveData = new MutableLiveData<>();

        }
        allCategoriesLiveData = getAllCategoriesRequest();
        return allCategoriesLiveData;
    }

    public LiveData<MallMainModel> getMallData() {
        if (mallDataViewModel == null) {
            mallDataViewModel = new MutableLiveData<>();
        }
        mallDataViewModel = getMallDataRequest();
        return mallDataViewModel;
    }


   /* public MutableLiveData<UserDetailResponseModel> getUserDetailRequest() {
        RestClient.getInstance().getService().GetUserDetail().enqueue(new Callback<UserDetailResponseModel>() {
            @Override
            public void onResponse(Call<UserDetailResponseModel> call, Response<UserDetailResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "request response=" + response.body());
                    userDetailViewModel.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserDetailResponseModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return userDetailViewModel;
    }*/

    public MutableLiveData<NearByMainModel> getGetTopNearByItemRequest() {
        RestClient.getInstance().getService().GetTopNearByItem().enqueue(new Callback<NearByMainModel>() {
            @Override
            public void onResponse(Call<NearByMainModel> call, Response<NearByMainModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "request response=" + response.body());
                    topNearByItem.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NearByMainModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return topNearByItem;
    }

    public MutableLiveData<TopSellerMainModel> GetTopSellerLiveRequest() {
        RestClient.getInstance().getService().GetTopSeller().enqueue(new Callback<TopSellerMainModel>() {
            @Override
            public void onResponse(Call<TopSellerMainModel> call, Response<TopSellerMainModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "request response=" + response.body());
                    topSellerLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TopSellerMainModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return topSellerLiveData;
    }

    public MutableLiveData<TopSellerMainModel> GetNearBYSellerLiveRequest() {
        RestClient.getInstance().getService().GetNearBySeller().enqueue(new Callback<TopSellerMainModel>() {
            @Override
            public void onResponse(Call<TopSellerMainModel> call, Response<TopSellerMainModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "request response=" + response.body());
                    nearBySellerLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TopSellerMainModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return nearBySellerLiveData;
    }

    public MutableLiveData<TopSellerMainModel> GetMostVisitedSellerLiveRequest() {
        RestClient.getInstance().getService().GetMostVisitedSeller().enqueue(new Callback<TopSellerMainModel>() {
            @Override
            public void onResponse(Call<TopSellerMainModel> call, Response<TopSellerMainModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "request response=" + response.body());
                    mostVisitedSellerLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TopSellerMainModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return mostVisitedSellerLiveData;
    }

    public MutableLiveData<TopSellerMainModel> GetSellerSellerLiveRequest() {
        RestClient.getInstance().getService().GetNewSeller().enqueue(new Callback<TopSellerMainModel>() {
            @Override
            public void onResponse(Call<TopSellerMainModel> call, Response<TopSellerMainModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "request response=" + response.body());
                    newSellerLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TopSellerMainModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return newSellerLiveData;
    }

    public MutableLiveData<AllCategoresMainModel> getAllCategoriesRequest() {
        RestClient.getInstance().getService().GetTopCategory().enqueue(new Callback<AllCategoresMainModel>() {
            @Override
            public void onResponse(Call<AllCategoresMainModel> call, Response<AllCategoresMainModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "request response=" + response.body());
                    allCategoriesLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AllCategoresMainModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return allCategoriesLiveData;
    }

    public MutableLiveData<MallMainModel> getMallDataRequest() {
        RestClient.getInstance().getService().getMall().enqueue(new Callback<MallMainModel>() {
            @Override
            public void onResponse(Call<MallMainModel> call, Response<MallMainModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.e(TAG, "request response=" + response.body());
                    mallDataViewModel.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MallMainModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return mallDataViewModel;
    }


}
