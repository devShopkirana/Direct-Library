package com.skdirect.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LifecycleObserver;
import androidx.multidex.MultiDex;

import com.facebook.soloader.SoLoader;
import com.google.gson.JsonObject;
import com.skdirect.api.CommonClassForAPI;
import com.skdirect.db.CartRepository;
import com.skdirect.model.TokenModel;

import org.jetbrains.annotations.NotNull;

import io.reactivex.observers.DisposableObserver;

public class MySingltonApplication extends Application implements LifecycleObserver {
    public Context appContext;
    private static MySingltonApplication mInstance;
    public Activity activity;
    public CartRepository cartRepository;
    public DBHelper dbHelper;
    public String otp = "1234";

    public static synchronized MySingltonApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        SoLoader.init(this, false);
        appContext = this;
        mInstance = this;
        cartRepository = new CartRepository(getApplicationContext());
        dbHelper = new DBHelper(this);
    }

    public void token() {
        new CommonClassForAPI().getToken(callToken, "password",!TextUtils.isNullOrEmpty(SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.MOBILE_NUMBER))?SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.MOBILE_NUMBER): Utils.getDeviceUniqueID(getApplicationContext()),
                "", false, true, "BUYERAPP", true,
                Utils.getDeviceUniqueID(getApplicationContext()),
                Double.parseDouble(SharePrefs.getStringSharedPreferences(this,SharePrefs.LAT)),
                Double.parseDouble(SharePrefs.getStringSharedPreferences(this,SharePrefs.LON)),
                SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.PIN_CODE),"",SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.SOURCEKEY));
    }


    private final DisposableObserver<TokenModel> callToken = new DisposableObserver<TokenModel>() {
        @Override
        public void onNext(@NotNull TokenModel model) {
            try {
                Utils.hideProgressDialog();
                if (model != null) {
                    Utils.getTokenData(getApplicationContext(),model);
                    if (activity != null)
                        activity.recreate();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Utils.setToast(getApplicationContext(), "Invalid Password");

            }
        }

        @Override
        public void onError(Throwable e) {
            Utils.setToast(getApplicationContext(), "Invalid Password");
            Utils.hideProgressDialog();
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            Utils.hideProgressDialog();
        }
    };
    private final DisposableObserver<JsonObject> updatecallToken = new DisposableObserver<JsonObject>() {
        @Override
        public void onNext(JsonObject model) {
            try {
                Utils.hideProgressDialog();
                if (model != null) {

                }
            } catch (Exception e) {
                e.printStackTrace();


            }
        }

        @Override
        public void onError(Throwable e) {
            Utils.hideProgressDialog();
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            Utils.hideProgressDialog();
        }
    };
}