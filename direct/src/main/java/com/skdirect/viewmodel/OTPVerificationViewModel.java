package com.skdirect.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.skdirect.api.RestClient;
import com.skdirect.model.GenerateOtpModel;
import com.skdirect.model.GenerateOtpResponseModel;
import com.skdirect.model.OtpResponceModel;
import com.skdirect.model.OtpVerificationModel;
import com.skdirect.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPVerificationViewModel extends ViewModel {
    final String TAG = getClass().getSimpleName();

    private MutableLiveData<GenerateOtpResponseModel> loginViewModel;





    public LiveData<GenerateOtpResponseModel> getLogin(GenerateOtpModel generateOtpModel) {
        if(loginViewModel==null){
            loginViewModel = new MutableLiveData<>();
        }
        loginViewModel = loginRequest(generateOtpModel);
        return loginViewModel;
    }


    public MutableLiveData<GenerateOtpResponseModel> loginRequest(GenerateOtpModel generateOtpModel) {
        RestClient.getInstance().getService().GenerateOtp(generateOtpModel).enqueue(new Callback<GenerateOtpResponseModel>() {
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
