package com.skdirect.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.skdirect.R;
import com.skdirect.api.CommonClassForAPI;
import com.skdirect.databinding.ActivityLoginBinding;
import com.skdirect.model.GenerateOtpModel;
import com.skdirect.model.TokenModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.LoginViewModel;

import org.jetbrains.annotations.NotNull;

import io.reactivex.observers.DisposableObserver;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private ActivityLoginBinding mBinding;

    private String mobileNumberString;
    private LoginViewModel loginViewModel;
    private DBHelper dbHelper;
    private GoogleApiClient googleApiClient;
    private static final int RESOLVE_HINT = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        dbHelper = DirectSDK.getInstance().dbHelper;
        initView();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_get_otp) {
            getOTP();
        } else if (id == R.id.bt_login_with_password) {
            loginWithPassword();
        } else if (id == R.id.bt_skip) {
            startActivity(new Intent(this, MainActivity.class));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT && resultCode == Activity.RESULT_OK && data != null) {
            Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
            if (credential != null) {
                String mobile = credential.getId();
                if (mobile.contains("+")) {
                    mobile = mobile.substring(3);
                }
                mBinding.etMobileNumber.setText(mobile);
            }
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void initView() {
        mBinding.btGetOtp.setOnClickListener(this);
        mBinding.btLoginWithPassword.setOnClickListener(this);
        mBinding.btSkip.setOnClickListener(this);
        mBinding.tvYourno.setText(dbHelper.getString(R.string.what_s_your_number));
        mBinding.etMobileNumber.setHint(dbHelper.getString(R.string.enter_phone_number));
        mBinding.tvOtpMsg.setText(dbHelper.getString(R.string.we_will_send_an_otp_to_nabove_number));
        mBinding.btGetOtp.setText(dbHelper.getString(R.string.get_otp));
        mBinding.btSkip.setText(dbHelper.getString(R.string.skip));
        mBinding.btLoginWithPassword.setText(dbHelper.getString(R.string.login_using_password));
        mBinding.etMobileNumber.setOnEditorActionListener((v, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                getOTP();
            }
            return false;
        });
        if (SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.TOKEN).isEmpty()) {
            Gettoken();
        }

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .addApi(Auth.CREDENTIALS_API)
                .build();
        getHintPhoneNumber();
    }

    private void loginWithPassword() {
        mobileNumberString = mBinding.etMobileNumber.getText().toString().trim();
        if (mobileNumberString.isEmpty()) {
            Utils.setToast(this, dbHelper.getString(R.string.enter_valid_mobno));
        } else if (Utils.isValidMobile(mobileNumberString)) {
            startActivity(new Intent(LoginActivity.this, LoginWithPasswordActivity.class).putExtra("MobileNumber", mobileNumberString));
        } else {
            Utils.setToast(this, dbHelper.getString(R.string.enter_valid_mobno));
        }
    }

    private void getOTP() {
        mobileNumberString = mBinding.etMobileNumber.getText().toString().trim();
        if (mobileNumberString.isEmpty()) {
            Utils.setToast(this, dbHelper.getString(R.string.enter_mobno));
            mBinding.btGetOtp.setText(dbHelper.getString(R.string.get_otp));
        } else if (Utils.isValidMobile(mobileNumberString)) {
            mBinding.btGetOtp.setText(dbHelper.getString(R.string.loading));
            callOTPApi(mobileNumberString);
        } else {
            Utils.setToast(this, dbHelper.getString(R.string.enter_valid_mobno));
        }
    }

    private void callOTPApi(String mobileNumberString) {
        if (Utils.isNetworkAvailable(this)) {
            Utils.showProgressDialog(this);
            GenerateOtpModel model = new GenerateOtpModel(mobileNumberString,
                    Utils.getDeviceUniqueID(this), FirebaseInstanceId.getInstance().getToken());
            getLoginData(model);
        } else {
            Utils.setToast(this, dbHelper.getString(R.string.no_connection));
        }
    }

    public void Gettoken() {
        new CommonClassForAPI().getToken(callToken, "password", Utils.getDeviceUniqueID(this),
                Utils.getDeviceUniqueID(this), true, true, "BUYERAPP", true,
                Utils.getDeviceUniqueID(this),
                Double.parseDouble(SharePrefs.getStringSharedPreferences(this, SharePrefs.LAT)),
                Double.parseDouble(SharePrefs.getStringSharedPreferences(this, SharePrefs.LON)),
                SharePrefs.getInstance(LoginActivity.this).getString(SharePrefs.PIN_CODE), "",SharePrefs.getInstance(this).getString(SharePrefs.SOURCEKEY));
    }

    public void getHintPhoneNumber() {
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();
        PendingIntent mIntent =
                Auth.CredentialsApi.getHintPickerIntent(googleApiClient, hintRequest);
        try {
            startIntentSenderForResult(mIntent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }


    private final DisposableObserver<TokenModel> callToken = new DisposableObserver<TokenModel>() {
        @Override
        public void onNext(@NotNull TokenModel model) {
            try {
                Utils.hideProgressDialog();
                if (model != null) {
                    Utils.getTokenData(getApplicationContext(), model);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.invalid_pass));
            }
        }

        @Override
        public void onError(Throwable e) {
            Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.invalid_pass));
            Utils.hideProgressDialog();
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            Utils.hideProgressDialog();
        }
    };

    private void getLoginData(GenerateOtpModel model) {
        loginViewModel.getLogin(model).observe(this, model1 -> {
            Utils.hideProgressDialog();
            if (model1 != null) {
                if (model1.isSuccess()) {
                    SharePrefs.getInstance(getApplicationContext()).putBoolean(SharePrefs.IS_USER_EXISTS, model1.getResultItem().isUserExists());
                    SharePrefs.getInstance(getApplicationContext()).putBoolean(SharePrefs.RESULT, model1.getResultItem().isResult());
                    startActivity(new Intent(getApplicationContext(), GenerateOTPActivity.class)
                            .putExtra("MobileNumber", mobileNumberString));
                    finish();
                } else {
                    Utils.setToast(getApplicationContext(), model1.getErrorMessage());
                }
            }
        });
    }
}