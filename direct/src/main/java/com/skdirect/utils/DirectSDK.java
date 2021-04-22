package com.skdirect.utils;

import android.app.Activity;
import android.content.Context;

import com.facebook.soloader.SoLoader;
import com.skdirect.api.CommonClassForAPI;
import com.skdirect.db.CartRepository;
import com.skdirect.model.TokenModel;

import org.jetbrains.annotations.NotNull;

import io.reactivex.observers.DisposableObserver;

public class DirectSDK {
    private static DirectSDK mInstance;
    public static Context context;
    public static CartRepository cartRepository;
    public static DBHelper dbHelper;
    public String otp = "1234";
    public Activity activity;


    public static synchronized DirectSDK getInstance() {
        if (mInstance == null) {
            mInstance = new DirectSDK();
        }
        return mInstance;
    }

    public static void initialize(Context context1) {
        context = context1;
        mInstance = new DirectSDK();
        SoLoader.init(context, false);
        cartRepository = new CartRepository(context);
        dbHelper = new DBHelper(context);
    }

    public void token() {
        new CommonClassForAPI().getToken(callToken, "password", !TextUtils.isNullOrEmpty(SharePrefs.getInstance(context).getString(SharePrefs.MOBILE_NUMBER)) ? SharePrefs.getInstance(context).getString(SharePrefs.MOBILE_NUMBER) : Utils.getDeviceUniqueID(context),
                "", false, true, "BUYERAPP", true,
                Utils.getDeviceUniqueID(context),
                Double.parseDouble(SharePrefs.getStringSharedPreferences(context, SharePrefs.LAT)),
                Double.parseDouble(SharePrefs.getStringSharedPreferences(context, SharePrefs.LON)),
                SharePrefs.getInstance(context).getString(SharePrefs.PIN_CODE), "", SharePrefs.getInstance(context).getString(SharePrefs.SOURCEKEY));
    }

    private final DisposableObserver<TokenModel> callToken = new DisposableObserver<TokenModel>() {
        @Override
        public void onNext(@NotNull TokenModel model) {
            try {
                Utils.hideProgressDialog();
                if (model != null) {
                    Utils.getTokenData(context, model);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Utils.setToast(context, "Invalid Password");

            }
        }

        @Override
        public void onError(Throwable e) {
            Utils.setToast(context, "Invalid Password");
            Utils.hideProgressDialog();
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            Utils.hideProgressDialog();
        }
    };
}