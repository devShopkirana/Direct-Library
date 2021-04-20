package com.skdirect.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.skdirect.api.RestClient;
import com.skdirect.model.AllCategoriesModel;
import com.skdirect.model.ChangePasswordRequestModel;
import com.skdirect.model.CommonResponseModel;
import com.skdirect.model.PaginationModel;
import com.skdirect.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordViewMode extends ViewModel {
    final String TAG = getClass().getSimpleName();

    private MutableLiveData<CommonResponseModel> changePasswordVM;
    private MutableLiveData<CommonResponseModel> OtpVM;

    public LiveData<CommonResponseModel> getChangePasswordVM() {
        if(changePasswordVM==null)
        changePasswordVM = new MutableLiveData<>();
        if (changePasswordVM.getValue() != null)
        {
            changePasswordVM.setValue(null);
        }
        return changePasswordVM;
    }
    public LiveData<CommonResponseModel> getOtpVM() {
        if(OtpVM==null)
            OtpVM = new MutableLiveData<>();
        if (OtpVM.getValue() != null)
        {
            OtpVM.setValue(null);
        }
        return OtpVM;
    }

    public MutableLiveData<CommonResponseModel> getChangePasswordRequest(ChangePasswordRequestModel passwordRequestModel) {
        RestClient.getInstance().getService().ChangePassword(passwordRequestModel).enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    changePasswordVM.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return changePasswordVM;
    }

    public MutableLiveData<CommonResponseModel> getOtp() {
        RestClient.getInstance().getService().sendotp().enqueue(new Callback<CommonResponseModel>() {
            @Override
            public void onResponse(Call<CommonResponseModel> call, Response<CommonResponseModel> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    OtpVM.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CommonResponseModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return OtpVM;
    }
}
