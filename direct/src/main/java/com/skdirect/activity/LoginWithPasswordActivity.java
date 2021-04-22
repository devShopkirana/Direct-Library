package com.skdirect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.gson.JsonObject;
import com.skdirect.R;
import com.skdirect.api.CommonClassForAPI;
import com.skdirect.databinding.ActivityLoginWithPasswordBinding;
import com.skdirect.model.TokenModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.TextUtils;
import com.skdirect.utils.Utils;

import io.reactivex.observers.DisposableObserver;

public class LoginWithPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginWithPasswordBinding mBinding;
    private String passwordString, mobileNumber;

    private CommonClassForAPI commonClassForAPI;
    private String fcmToken;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login_with_password);
        dbHelper = DirectSDK.getInstance().dbHelper;
        getIntentData();
        initView();
    }

    private void getIntentData() {
        mobileNumber = getIntent().getStringExtra("MobileNumber");

    }

    private void initView() {

        mBinding.tvPasswordVerification.setText(dbHelper.getString(R.string.password_verification));
        mBinding.tvEnterThePassword.setText(dbHelper.getString(R.string.enter_the_password));
        mBinding.etPassword.setHint(dbHelper.getString(R.string.enter_password));
        mBinding.btLoddingOtp.setText(dbHelper.getString(R.string.next));

        fcmToken = Utils.getFcmToken();
        commonClassForAPI = CommonClassForAPI.getInstance(this);
        mBinding.btLoddingOtp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_lodding_otp) {
            Utils.hideKeyboard(this, view);
            loginWithPassword();
        }
    }

    private void loginWithPassword() {
        mBinding.btLoddingOtp.setText(dbHelper.getString(R.string.loading));
        passwordString = mBinding.etPassword.getText().toString().trim();
        if (passwordString.isEmpty()) {
            mBinding.btLoddingOtp.setText(dbHelper.getString(R.string.next));
            Utils.setToast(this, dbHelper.getString(R.string.please_enter_password));
        } else {
            checkPasswordApi();
        }
    }

    private void checkPasswordApi() {
        if (Utils.isNetworkAvailable(this)) {
            if (commonClassForAPI != null) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("Password", passwordString);
                jsonObject.addProperty("UserName", mobileNumber);
                commonClassForAPI.VerfiyPassword(passwordDes, jsonObject);
            }
        } else {
            Utils.setToast(this, dbHelper.getString(R.string.no_internet_connection));
        }
    }


    private final DisposableObserver<JsonObject> passwordDes = new DisposableObserver<JsonObject>() {
        @Override
        public void onNext(JsonObject jsonObject) {
            try {
                Utils.hideProgressDialog();
                if (jsonObject.get("IsSuccess").getAsBoolean()) {
                    Utils.setToast(getApplicationContext(), jsonObject.get("SuccessMessage").getAsString());
                    if (Utils.isNetworkAvailable(getApplicationContext())) {
                        if (commonClassForAPI != null) {
                            commonClassForAPI
                                    .getTokenwithphoneNo(callToken, "password", Utils.getDeviceUniqueID(LoginWithPasswordActivity.this),
                                            passwordString, false, true, "BUYERAPP", true,
                                            Utils.getDeviceUniqueID(LoginWithPasswordActivity.this), Double.parseDouble(SharePrefs.getStringSharedPreferences(LoginWithPasswordActivity.this, SharePrefs.LAT)), Double.parseDouble(SharePrefs.getStringSharedPreferences(LoginWithPasswordActivity.this, SharePrefs.LON)), SharePrefs.getInstance(LoginWithPasswordActivity.this).getString(SharePrefs.PIN_CODE), "GET", mobileNumber,SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.SOURCEKEY));
                        }

                    } else {
                        Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.no_internet_connection));
                    }
                } else {
                    mBinding.btLoddingOtp.setText(dbHelper.getString(R.string.next));
                    Utils.setToast(getApplicationContext(), jsonObject.get("ErrorMessage").getAsString());
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        @Override
        public void onError(Throwable e) {
            mBinding.btLoddingOtp.setText(dbHelper.getString(R.string.next));
            Utils.hideProgressDialog();
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            Utils.hideProgressDialog();
        }
    };

    private final DisposableObserver<TokenModel> callToken = new DisposableObserver<TokenModel>() {
        @Override
        public void onNext(TokenModel model) {
            try {
                Utils.hideProgressDialog();
                if (model != null) {
                    SharePrefs.getInstance(getApplicationContext()).putString(SharePrefs.TOKEN, model.getAccess_token());
                    SharePrefs.getInstance(getApplicationContext()).putString(SharePrefs.USER_NAME, model.getUserName());
                    SharePrefs.setSharedPreference(getApplicationContext(), SharePrefs.IS_REGISTRATIONCOMPLETE, model.getIsRegistrationComplete());
                    SharePrefs.setStringSharedPreference(getApplicationContext(), SharePrefs.LAT, model.getLatitiute());
                    SharePrefs.setStringSharedPreference(getApplicationContext(), SharePrefs.LON, model.getLongitude());
                    SharePrefs.getInstance(getApplicationContext()).putString(SharePrefs.BUSINESS_TYPE, model.getBusinessType());
                    SharePrefs.getInstance(getApplicationContext()).putBoolean(SharePrefs.IS_CONTACTREAD, model.getIscontactRead());
                    SharePrefs.getInstance(getApplicationContext()).putBoolean(SharePrefs.IS_SUPER_ADMIN, model.getIsSuperAdmin());
                    SharePrefs.getInstance(getApplicationContext()).putBoolean(SharePrefs.IS_LOGIN, true);
                    commonClassForAPI.getUpdateToken(updatecallToken, fcmToken);
                    if(!TextUtils.isNullOrEmpty(DirectSDK.getInstance().cartRepository.getCartId())){
                        commonClassForAPI.assignCart(new DisposableObserver<JsonObject>() {
                            @Override
                            public void onNext(JsonObject model) { }
                            @Override
                            public void onError(Throwable e) {
                                Utils.hideProgressDialog();
                                e.printStackTrace();
                            }
                            @Override
                            public void onComplete() {
                                Utils.hideProgressDialog();
                            }
                        }, DirectSDK.getInstance().cartRepository.getCartId());
                    }
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
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




