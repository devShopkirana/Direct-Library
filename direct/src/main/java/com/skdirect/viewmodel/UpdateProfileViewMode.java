package com.skdirect.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.skdirect.api.RestClient;
import com.skdirect.model.AllCategoriesModel;
import com.skdirect.model.CustomerDataModel;
import com.skdirect.model.PaginationModel;
import com.skdirect.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileViewMode extends ViewModel {
    final String TAG = getClass().getSimpleName();

    private MutableLiveData<Boolean> updateProfileVM;

    public LiveData<Boolean> updateProfileVM() {
        updateProfileVM=null;
        updateProfileVM = new MutableLiveData<>();
        return updateProfileVM;
    }

    public MutableLiveData<Boolean> updateProfileVMRequest(JsonObject jsonObject) {
        RestClient.getInstance().getService().UpdateUserDetail(jsonObject).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    updateProfileVM.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + t.toString());
                Utils.hideProgressDialog();
            }
        });

        return updateProfileVM;
    }
}
