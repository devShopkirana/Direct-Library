package com.skdirect.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.skdirect.api.RestClient;
import com.skdirect.model.AppVersionModel;
import com.skdirect.model.GenerateOtpModel;
import com.skdirect.model.GenerateOtpResponseModel;
import com.skdirect.model.OtpResponceModel;
import com.skdirect.utils.Utils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    final String TAG = getClass().getSimpleName();

    private MutableLiveData<GenerateOtpResponseModel> loginViewModel;

    public LiveData<GenerateOtpResponseModel> getLogin(GenerateOtpModel model) {
        if(loginViewModel==null){
            loginViewModel = new MutableLiveData<>();
            loginViewModel = loginRequest(model);
        }
        return loginViewModel;
    }

    public MutableLiveData<GenerateOtpResponseModel> loginRequest(GenerateOtpModel model) {
        RestClient.getInstance().getService().GenerateOtp(model).enqueue(new Callback<GenerateOtpResponseModel>() {
            @Override
            public void onResponse(Call<GenerateOtpResponseModel> call, Response<GenerateOtpResponseModel> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    loginViewModel.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<GenerateOtpResponseModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return loginViewModel;
    }


}
