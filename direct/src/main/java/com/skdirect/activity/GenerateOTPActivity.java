package com.skdirect.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.JsonObject;
import com.skdirect.R;
import com.skdirect.api.CommonClassForAPI;
import com.skdirect.broadcast.SmsBroadcastReceiver;
import com.skdirect.databinding.ActivityGenerateOtpBinding;
import com.skdirect.interfacee.OtpReceivedInterface;
import com.skdirect.model.GenerateOtpModel;
import com.skdirect.model.GenerateOtpResponseModel;
import com.skdirect.model.OtpResponceModel;
import com.skdirect.model.OtpVerificationModel;
import com.skdirect.model.TokenModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.TextUtils;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.OTPVerificationViewModel;

import org.jetbrains.annotations.NotNull;

import io.reactivex.observers.DisposableObserver;

public class GenerateOTPActivity extends AppCompatActivity implements OtpReceivedInterface, View.OnClickListener {
    final String TAG = getClass().getSimpleName();
    private ActivityGenerateOtpBinding Binding;
    private SmsBroadcastReceiver mSmsBroadcastReceiver;
    private String latsFiveNumber, otpString, mobileNumber;
    private OTPVerificationViewModel otpVerificationViewModel;
    private CountDownTimer cTimer = null;
    private CommonClassForAPI commonClassForAPI;
    private String fcmToken;
    DBHelper dbHelper;
    int noOfSecond = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Binding = DataBindingUtil.setContentView(this, R.layout.activity_generate_otp);
        otpVerificationViewModel = ViewModelProviders.of(this).get(OTPVerificationViewModel.class);
        dbHelper = DirectSDK.getInstance().dbHelper;
        getIntentData();
        initView();
    }

    private void getIntentData() {
        mobileNumber = getIntent().getStringExtra("MobileNumber");
        latsFiveNumber = mobileNumber.substring(mobileNumber.length() - 5);
        fcmToken = Utils.getFcmToken();
    }

    private void initView() {
        commonClassForAPI = CommonClassForAPI.getInstance(this);
        Binding.btLoddingOtp.setOnClickListener(this);
        Utils.showProgressDialog(this);
        Binding.tvVerifactionText.setText(dbHelper.getString(R.string.enter_no_txt) + latsFiveNumber);
        Binding.tvVerificationHead.setText(dbHelper.getString(R.string.verification));
        Binding.resendotp.setText(dbHelper.getString(R.string.resend_otp));
        Binding.btLoddingOtp.setText(dbHelper.getString(R.string.next));
        // startTimer(Binding.resendOtpTimer, Binding.resendotp);
        // init broadcast receiver
        mSmsBroadcastReceiver = new SmsBroadcastReceiver();
        mSmsBroadcastReceiver.setOnOtpListeners(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(mSmsBroadcastReceiver, intentFilter);

        Binding.etOtp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    Binding.btLoddingOtp.setText(dbHelper.getString(R.string.loading));
                    checkVerification();
                }
                return false;
            }
        });

        Binding.resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTimer();
                callResendOTPApi(mobileNumber);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Binding.etOtp.setText(DirectSDK.getInstance().otp);
                //TODO Set your button auto perform click.
                Binding.btLoddingOtp.performClick();

            }
        }, noOfSecond * 100);

//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        mAuth.setLanguageCode("en");
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder()
//                        .setPhoneNumber("+91" + mobileNumber)       // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(this)                 // Activity (for callback binding)
//                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                System.out.println("asd " + firebaseAuth.getPendingAuthResult());
//                if (firebaseAuth.getPendingAuthResult() != null) {
//                    System.out.println("asd " + firebaseAuth.getPendingAuthResult().getResult());
//                    System.out.println("asd " + firebaseAuth.getPendingAuthResult().getResult().getUser());
//                    System.out.println("asd " + firebaseAuth.getCurrentUser().getPhoneNumber());
//                }
//            }
//        });
    }

    void startTimer(TextView tvResendOtpTimer, TextView resendotp) {
        cTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                //  Logger.logD("WelcomeActivity", "Timer:" + millisUntilFinished / 1000);
                tvResendOtpTimer.setText(dbHelper.getString(R.string.resend_otp) + ":" + "" + millisUntilFinished / 1000);
                Binding.resendotp.setVisibility(View.GONE);
            }

            public void onFinish() {
                resendotp.setEnabled(true);
                resendotp.setBackgroundResource(R.drawable.rectangle);
                resendotp.setPadding(8, 8, 8, 8);
                Binding.resendotp.setVisibility(View.VISIBLE);
                resendotp.setTextColor(getResources().getColor(R.color.colorAccentDir));
                //Toast.makeText(context, getString(R.string.resendotp), Toast.LENGTH_SHORT).show();
            }
        };
        cTimer.start();
    }


    // cancel timer
    void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSmsBroadcastReceiver != null) {
            unregisterReceiver(mSmsBroadcastReceiver);
        }
    }

    @Override
    public void onOtpReceived(String otp) {
        Binding.etOtp.setText(otp + "");
        Binding.btLoddingOtp.callOnClick();
    }

    @Override
    public void onOtpTimeout() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_lodding_otp) {
            checkVerification();
        }
    }

    private void checkVerification() {
        Binding.etOtp.setText("5678");
        otpString = Binding.etOtp.getText().toString().trim();
        if (otpString.isEmpty()) {
            Utils.setToast(this, dbHelper.getString(R.string.enter_otp));
        } else {
            Binding.btLoddingOtp.setText(dbHelper.getString(R.string.loading));
            callOTPVerfiyAPI(otpString);
        }
    }

    private void callOTPVerfiyAPI(String otpString) {
        if (Utils.isNetworkAvailable(this)) {
            if (commonClassForAPI != null) {
                commonClassForAPI.VerfiyOtp(OTPVerfiyData, new OtpVerificationModel(mobileNumber, otpString, Utils.getDeviceUniqueID(this)));
            }
        } else {
            Utils.setToast(this, dbHelper.getString(R.string.no_connection));
        }
    }


    private void callResendOTPApi(String mobileNumberString) {
        if (Utils.isNetworkAvailable(this)) {
            Utils.showProgressDialog(this);
            GenerateOtpModel model = new GenerateOtpModel(mobileNumberString, Utils.getDeviceUniqueID(this));
            getResendOTPData(model);
        } else {
            Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.no_connection));
        }
    }

    private void getResendOTPData(GenerateOtpModel generateOtpModel) {
        otpVerificationViewModel.getLogin(generateOtpModel).observe(this, new Observer<GenerateOtpResponseModel>() {
            @Override
            public void onChanged(GenerateOtpResponseModel model) {
                Utils.hideProgressDialog();
                if (model != null) {
                    if (model.isSuccess()) {
                        SharePrefs.getInstance(GenerateOTPActivity.this).putBoolean(SharePrefs.IS_USER_EXISTS, model.getResultItem().isUserExists());
                        SharePrefs.getInstance(GenerateOTPActivity.this).putBoolean(SharePrefs.RESULT, model.getResultItem().isResult());
                        startTimer(Binding.resendOtpTimer, Binding.resendotp);
                    } else {
                        Utils.setToast(GenerateOTPActivity.this, model.getErrorMessage());
                    }
                }
            }
        });
    }

    private final DisposableObserver<OtpResponceModel> OTPVerfiyData = new DisposableObserver<OtpResponceModel>() {
        @Override
        public void onNext(@NotNull OtpResponceModel model) {
            Utils.hideProgressDialog();
            if (model != null) {
                if (model.isSuccess()) {
                    SharePrefs.getInstance(GenerateOTPActivity.this).putBoolean(SharePrefs.IS_USER_EXISTS, model.getResultItem().getIsUserExist());
                    SharePrefs.getInstance(GenerateOTPActivity.this).putString(SharePrefs.USER_ID, model.getResultItem().getUserid());
                    commonClassForAPI
                            .getTokenwithphoneNo(callToken, "password", Utils.getDeviceUniqueID(GenerateOTPActivity.this),
                                    Utils.getDeviceUniqueID(GenerateOTPActivity.this), true, true, "BUYERAPP", true,
                                    Utils.getDeviceUniqueID(GenerateOTPActivity.this), Double.parseDouble(SharePrefs.getStringSharedPreferences(GenerateOTPActivity.this, SharePrefs.LAT)), Double.parseDouble(SharePrefs.getStringSharedPreferences(GenerateOTPActivity.this, SharePrefs.LON)), SharePrefs.getInstance(GenerateOTPActivity.this).getString(SharePrefs.PIN_CODE), "GET", mobileNumber,SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.FIRST_NAME),SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.SOURCEKEY));
                } else {
                    Binding.btLoddingOtp.setText(dbHelper.getString(R.string.next));
                    Utils.setToast(GenerateOTPActivity.this, dbHelper.getString(R.string.valid_otp));
                }
            } else {
                Utils.setToast(GenerateOTPActivity.this, dbHelper.getString(R.string.valid_otp));
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

    private final DisposableObserver<TokenModel> callToken = new DisposableObserver<TokenModel>() {
        @Override
        public void onNext(@NotNull TokenModel model) {
            try {
                Utils.hideProgressDialog();
                if (model != null) {
                    Utils.getTokenData(getApplicationContext(), model);
                    commonClassForAPI.getUpdateToken(updatecallToken, fcmToken);
                    if (!TextUtils.isNullOrEmpty(DirectSDK.getInstance().cartRepository.getCartId())) {
                        commonClassForAPI.assignCart(new DisposableObserver<JsonObject>() {
                            @Override
                            public void onNext(@NotNull JsonObject model) {
                            }

                            @Override
                            public void onError(@NotNull Throwable e) {
                                e.printStackTrace();
                                Utils.hideProgressDialog();
                            }

                            @Override
                            public void onComplete() {
                                Utils.hideProgressDialog();
                            }
                        }, DirectSDK.getInstance().cartRepository.getCartId());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Binding.btLoddingOtp.setText(dbHelper.getString(R.string.next));
                Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.invalid_pass));
            }
        }

        @Override
        public void onError(Throwable e) {
            Binding.btLoddingOtp.setText(dbHelper.getString(R.string.next));
            Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.invalid_pass));
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
        public void onNext(@NotNull JsonObject model) {
            try {
                Utils.hideProgressDialog();
                if (model != null) {
                    if (SharePrefs.getSharedPreferences(getApplicationContext(), SharePrefs.IS_REGISTRATIONCOMPLETE)) {
                        SharePrefs.getInstance(getApplicationContext()).putBoolean(SharePrefs.IS_LOGIN, true);
                        if (SharePrefs.getSharedPreferences(getApplicationContext(), SharePrefs.CAME_FROM_CART)) {
                            startActivity(new Intent(GenerateOTPActivity.this, CartActivity.class).putExtra("ComeTo","Login"));
                            SharePrefs.setSharedPreference(getApplicationContext(), SharePrefs.CAME_FROM_CART, false);
                        } else {
                            startActivity(new Intent(GenerateOTPActivity.this, MainActivity.class));
                        }
                    } else {
                        startActivity(new Intent(GenerateOTPActivity.this, RegisterationActivity.class));
                    }
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


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NotNull PhoneAuthCredential credential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d(TAG, "onVerificationCompleted:" + credential);
            final String code = credential.getSmsCode();
            if (code != null) {
                Binding.etOtp.setText(code);
            }
            Utils.setToast(getApplicationContext(), "verified " + credential.getSmsCode());
        }

        @Override
        public void onVerificationFailed(@NotNull FirebaseException e) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w(TAG, "onVerificationFailed", e);
            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                e.printStackTrace();
                // Invalid request
            } else if (e instanceof FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                e.printStackTrace();
            }
            // Show a message and update the UI
            Utils.setToast(getApplicationContext(), "error" + e.getMessage());
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            Log.d(TAG, "onCodeSent:" + verificationId);
            // Save verification ID and resending token so we can use them later
//            mVerificationId = verificationId;
//            mResendToken = token;
        }
    };
}
