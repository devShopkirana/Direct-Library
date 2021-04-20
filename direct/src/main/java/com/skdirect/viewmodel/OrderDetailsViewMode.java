package com.skdirect.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.skdirect.api.RestClient;
import com.skdirect.model.AddReviewModel;
import com.skdirect.model.MallMainModelBolleanResult;
import com.skdirect.model.OrderDetailsModel;
import com.skdirect.model.OrderItemModel;
import com.skdirect.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsViewMode extends ViewModel {
    final String TAG = getClass().getSimpleName();

    private MutableLiveData<OrderDetailsModel> orderDetailsViewMode;
    private MutableLiveData<ArrayList<OrderItemModel>> orderDetailsItems;
    private MutableLiveData<MallMainModelBolleanResult> CancelOrderVM;

    public LiveData<OrderDetailsModel> getOrderDetailsViewMode() {
        orderDetailsViewMode=null;
        orderDetailsViewMode = new MutableLiveData<>();
        return orderDetailsViewMode;
    }

    public LiveData<ArrayList<OrderItemModel>> getOrderDetailsItemsViewMode() {
        orderDetailsItems=null;
        orderDetailsItems = new MutableLiveData<>();
        return orderDetailsItems;
    }

    public LiveData<MallMainModelBolleanResult> getCancelOrderVM() {
        CancelOrderVM=null;
        CancelOrderVM = new MutableLiveData<>();
        return CancelOrderVM;
    }

    public MutableLiveData<OrderDetailsModel> getOrderDetailsRequest(int orderID) {
        RestClient.getInstance().getService().GetOrderDetailProcess(orderID).enqueue(new Callback<OrderDetailsModel>() {
            @Override
            public void onResponse(Call<OrderDetailsModel> call, Response<OrderDetailsModel> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    orderDetailsViewMode.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsModel> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return orderDetailsViewMode;
    }

    public MutableLiveData<ArrayList<OrderItemModel>> getOrderDetailsItemsRequest(int orderID) {
        RestClient.getInstance().getService().GetOrderDetails(orderID).enqueue(new Callback<ArrayList<OrderItemModel>>() {
            @Override
            public void onResponse(Call<ArrayList<OrderItemModel>> call, Response<ArrayList<OrderItemModel>> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    orderDetailsItems.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<OrderItemModel>> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return orderDetailsItems;
    }

    public MutableLiveData<MallMainModelBolleanResult> getCancelOrderVMRequest(int orderID) {
        RestClient.getInstance().getService().CancelOrder(orderID).enqueue(new Callback<MallMainModelBolleanResult>() {
            @Override
            public void onResponse(Call<MallMainModelBolleanResult> call, Response<MallMainModelBolleanResult> response) {
                if (response.isSuccessful() && response.body()!=null ) {
                    Log.e(TAG, "request response="+response.body());
                    CancelOrderVM.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MallMainModelBolleanResult> call, Throwable t) {
                Log.e(TAG, "onFailure Responce" + call.toString());
                Utils.hideProgressDialog();
            }
        });

        return CancelOrderVM;
    }
}
