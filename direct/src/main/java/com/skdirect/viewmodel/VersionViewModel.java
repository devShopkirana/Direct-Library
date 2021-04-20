package com.skdirect.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.skdirect.api.RestClient;
import com.skdirect.model.AppVersionModel;
import com.skdirect.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VersionViewModel extends ViewModel {
    final String TAG = getClass().getSimpleName();
    private MutableLiveData<AppVersionModel> mutableLiveData;

    public LiveData<AppVersionModel> getVersion() {
        if(mutableLiveData==null)
        {
            mutableLiveData = new MutableLiveData<>();
        }
        mutableLiveData = requestHolidays();
        return mutableLiveData;
    }

    public MutableLiveData<AppVersionModel> requestHolidays() {
        RestClient.getInstance().getService().getAppversion().enqueue(new Callback<AppVersionModel>() {
            @Override
            public void onResponse(Call<AppVersionModel> call, Response<AppVersionModel> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<AppVersionModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }


        });

        return mutableLiveData;
    }


}
