package com.skdirect.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.skdirect.R;
import com.skdirect.api.CommonClassForAPI;
import com.skdirect.databinding.ActivitySplashBinding;
import com.skdirect.model.AppVersionModel;
import com.skdirect.utils.MyApplication;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.Utils;

import org.jetbrains.annotations.NotNull;

import io.reactivex.observers.DisposableObserver;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding mBinding;
    private SplashActivity activity;
    private CommonClassForAPI commonClassForAPI;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        activity = this;
        commonClassForAPI = CommonClassForAPI.getInstance(this);
        initViews();
    }

    @Override
    protected void onPostResume() {
        callAPI();
        super.onPostResume();
    }


    private void initViews() {
        Glide.with(activity).load("")
                .placeholder(R.drawable.splash)
                .into(mBinding.imSplash);
    }


    private void callAPI() {
        try {
            if (Utils.isNetworkAvailable(activity)) {
                if (commonClassForAPI != null) {
                    commonClassForAPI.getAppInfo(new DisposableObserver<AppVersionModel>() {
                        @Override
                        public void onNext(@NotNull AppVersionModel appVersionModels) {
                            if (appVersionModels != null) {
                                if (appVersionModels.isSuccess()) {
                                    //                    if (BuildConfig.VERSION_NAME.equals(appVersionModels.getResultItem().getVersion())) {

                                    SharePrefs.getInstance(getApplicationContext()).putString(SharePrefs.SELLER_URL, appVersionModels.getResultItem().getSellerUrl());
                                    SharePrefs.getInstance(getApplicationContext()).putString(SharePrefs.BUYER_URL, appVersionModels.getResultItem().getBuyerUrl());
                                    SharePrefs.getInstance(getApplicationContext()).putString(SharePrefs.PRIVACY_POLICY, appVersionModels.getResultItem().getPrivacyPolicy());
                                    SharePrefs.getInstance(getApplicationContext()).putString(SharePrefs.TERMS_CONDITION, appVersionModels.getResultItem().getTermsCondition());
                                    SharePrefs.getInstance(getApplicationContext()).putString(SharePrefs.ABOUT_APP, appVersionModels.getResultItem().getAboutApp());
                                    launchHomeScreen();
                                    finish();
                                    //                    } else {
//                        checkVersionData(appVersionModels);
//                    }
                                }
                            }
                        }
                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }
                        @Override
                        public void onComplete() {
                            Utils.hideProgressDialog();
                        }
                    });
                }
            } else {
                Utils.setToast(getBaseContext(), MyApplication.getInstance().dbHelper.getString(R.string.no_internet_connection));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void launchHomeScreen() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEditor = prefs.edit();
        prefEditor.remove(SharePrefs.FILTER_CATEGORY_LIST);
        prefEditor.apply();
        prefEditor.clear();
        if (SharePrefs.getSharedPreferences(getApplicationContext(), SharePrefs.Is_First_Time)) {
            startActivity(new Intent(activity, MainActivity.class));
        } else {
            startActivity(new Intent(activity, IntroActivity.class));
        }
    }

    private void checkVersionData(AppVersionModel appVersionModels) {
        try {
            SharePrefs.getInstance(activity).putString(SharePrefs.SELLER_URL, appVersionModels.getResultItem().getSellerUrl());
            SharePrefs.getInstance(activity).putString(SharePrefs.BUYER_URL, appVersionModels.getResultItem().getBuyerUrl());
//            if (BuildConfig.VERSION_NAME.equalsIgnoreCase(appVersionModels.getResultItem().getVersion())) {
//
//            } else {
                @SuppressLint("RestrictedApi")
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle(R.string.update_available);
                alertDialogBuilder.setMessage(MyApplication.getInstance().dbHelper.getString(R.string.update_to_latest_version) + " " + appVersionModels.getResultItem().getVersion() + " " + MyApplication.getInstance().dbHelper.getString(R.string.from_play_store));
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton(MyApplication.getInstance().dbHelper.getString(R.string.update), (dialog, id) -> {
                    dialog.cancel();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + activity.getPackageName())));
                });
                alertDialogBuilder.setNegativeButton(MyApplication.getInstance().dbHelper.getString(R.string.skip), (dialog, i) -> {
                    startActivity(new Intent(activity, MainActivity.class));
                    finish();
                });
                alertDialogBuilder.show();
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}