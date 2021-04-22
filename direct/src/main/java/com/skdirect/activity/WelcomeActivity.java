package com.skdirect.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.skdirect.R;
import com.skdirect.api.CommonClassForAPI;
import com.skdirect.databinding.ActivityWelcomedBinding;
import com.skdirect.model.GenerateOtpModel;
import com.skdirect.model.GenerateOtpResponseModel;
import com.skdirect.model.OtpResponceModel;
import com.skdirect.model.OtpVerificationModel;
import com.skdirect.model.UpdateProfilePostModel;
import com.skdirect.utils.ExtendedViewPager;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.LoginViewModel;
import com.skdirect.viewmodel.OTPVerificationViewModel;

import org.jetbrains.annotations.NotNull;

import io.reactivex.observers.DisposableObserver;

public class WelcomeActivity extends AppCompatActivity {

    private ActivityWelcomedBinding mBinding;
    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private String mobileNumberString, name, email;
    private LoginViewModel loginViewModel;
    private OTPVerificationViewModel otpVerificationViewModel;
    private Button btnback, btnNext;
    private CommonClassForAPI commonClassForAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcomed);
        mBinding.setLifecycleOwner(this);

        init();
        btnback.setOnClickListener(v -> {
            int current = getItem(-1);
            if (current < layouts.length) {
                // move to next screen
                viewPager.setCurrentItem(current);
            }
        });
        btnNext.setOnClickListener(v -> {
            try {
                // customerid = SharePrefs.getInstance(WelcomeActivity.this).getInt(SharePrefs.CUSTOMER_ID);

                if (mobileNumberString == null) {
                    Toast.makeText(WelcomeActivity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (!Utils.isValidMobile(mobileNumberString)) {
                    Toast.makeText(WelcomeActivity.this, "Please Enter Valid  Mobile Number", Toast.LENGTH_SHORT).show();
                } else {
                    int current = getItem(+1);
                    if (current < layouts.length) {
                        viewPager.setCurrentItem(current);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void init() {
        final ExtendedViewPager cvp = findViewById(R.id.view_pager_view);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        otpVerificationViewModel = ViewModelProviders.of(this).get(OTPVerificationViewModel.class);
        viewPager = mBinding.viewPagerView;
        btnback = mBinding.btnBack;
        btnNext = mBinding.btnNext;
        dotsLayout = mBinding.layoutDots;
        viewPager.beginFakeDrag();
        commonClassForAPI = CommonClassForAPI.getInstance(this);

        layouts = new int[]{
                R.layout.activity_logind,
                R.layout.activity_generate_otp,
                R.layout.activity_info,
        };
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(WelcomeActivity.this, cvp);
        cvp.setAdapter(myViewPagerAdapter);
        cvp.setOffscreenPageLimit(3);
        addBottomDots(0);
        changeStatusBarColor();
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewPager.setCurrentItem(0);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void addBottomDots(int currentPage) {
        try {
            TextView[] dots = new TextView[layouts.length];
            int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
            int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);
            dotsLayout.removeAllViews();
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(this);
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(colorsInactive[currentPage]);
                dotsLayout.addView(dots[i]);
            }
            if (dots.length > 0)
                dots[currentPage].setTextColor(colorsActive[currentPage]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == 0) {
                btnback.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
            } else if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setVisibility(View.GONE);
                btnback.setVisibility(View.GONE);
            } else if (position == 1) {
                // still pages are left
                btnNext.setVisibility(View.GONE);
                btnback.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        Context context;
        ExtendedViewPager cvp;
        EditText etMobileNo, etOtp, etName, etEmail;
        TextView etVerificationTxt;
        Button btGetOtp, btnSkip, btnLoginwithpass, BtnSubmitOtp, btnNext;

        public MyViewPagerAdapter(Context context, ExtendedViewPager cvp) {
            this.context = context;
            this.cvp = cvp;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            if (position == 0) {
                etMobileNo = view.findViewById(R.id.et_mobile_number);
                btGetOtp = view.findViewById(R.id.bt_get_otp);
                btnSkip = view.findViewById(R.id.bt_skip);
                btnLoginwithpass = view.findViewById(R.id.bt_login_with_password);
                etMobileNo.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() > 0) {
                            mobileNumberString = etMobileNo.getText().toString();
                        } else {
                            mobileNumberString = "";
                        }

                    }
                });
                etMobileNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                            mobileNumberString = etMobileNo.getText().toString();
                            getOTP(btGetOtp, mobileNumberString);
                        }
                        return false;
                    }
                });
                btGetOtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mobileNumberString = etMobileNo.getText().toString();
                        getOTP(btGetOtp, mobileNumberString);
                    }
                });
                btnSkip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));

                    }
                });
                btnLoginwithpass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mobileNumberString = etMobileNo.getText().toString();
                        loginWithPassword(mobileNumberString);
                    }
                });
            } else if (position == 1) {
                etVerificationTxt = view.findViewById(R.id.tv_verifaction_text);
                etOtp = view.findViewById(R.id.et_otp);
                BtnSubmitOtp = view.findViewById(R.id.bt_lodding_otp);
                if (mobileNumberString != null) {
                    etVerificationTxt.setText("Enter Verification code which we have\n" +
                            "send to XXXXX " + mobileNumberString.substring(mobileNumberString.length() - 5));
                }

                etOtp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                                || keyEvent.getAction() == KeyEvent.ACTION_DOWN || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                            BtnSubmitOtp.setText("Loading ...");

                            checkVerification(etOtp.getText().toString(), mobileNumberString);

                        }

                        return false;
                    }
                });
                BtnSubmitOtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BtnSubmitOtp.setText("Loading ...");

                        checkVerification(etOtp.getText().toString(), mobileNumberString);
                    }
                });

            } else if (position == 2) {
                etName = view.findViewById(R.id.et_name);
                etEmail = view.findViewById(R.id.et_email_id);
                btnNext = view.findViewById(R.id.btn_next);
                btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        name = etName.getText().toString();
                        email = etEmail.getText().toString();
                        if (name.isEmpty() && name.length() > 0) {
                            Toast.makeText(WelcomeActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                        } else if (email.isEmpty() && email.length() > 0) {
                            Toast.makeText(WelcomeActivity.this, "Please Enter Your EmailId", Toast.LENGTH_SHORT).show();
                        } else if (!Utils.isValidEmail(email)) {
                            Toast.makeText(WelcomeActivity.this, "Please Enter Your Valid  EmailId", Toast.LENGTH_SHORT).show();
                        } else {
                            UpdateProfilePostModel updateProfilePostModel = new UpdateProfilePostModel(name, email);
                            commonClassForAPI.UpdateUserProfile(updateprofile, updateProfilePostModel);
                        }
                    }
                });


            }
            container.addView(view);
            return view;
        }


        @Override
        public int getCount() {
            return layouts.length;
        }


        @Override
        public boolean isViewFromObject(@NotNull View view, @NotNull Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NotNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }


    }

    private DisposableObserver<GenerateOtpResponseModel> updateprofile = new DisposableObserver<GenerateOtpResponseModel>() {
        @Override
        public void onNext(GenerateOtpResponseModel model) {
            try {
                Utils.hideProgressDialog();
                if (model != null) {
                    if (model.isSuccess()) {
                        Utils.setToast(WelcomeActivity.this, model.getSuccessMessage());
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Utils.setToast(WelcomeActivity.this, model.getErrorMessage());
                    }
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

    private void loginWithPassword(String phonenumber) {
        if (phonenumber.isEmpty()) {
            Utils.setToast(this, "Please enter  Mobile Number.");
        } else {
            startActivity(new Intent(WelcomeActivity.this, LoginWithPasswordActivity.class).putExtra("MobileNumber", phonenumber));
        }
    }

    private void CallProfileUpdate(String phonenumber) {
        if (phonenumber.isEmpty()) {
            Utils.setToast(this, "Please enter  Mobile Number.");
        } else {
            startActivity(new Intent(WelcomeActivity.this, LoginWithPasswordActivity.class).putExtra("MobileNumber", phonenumber));
        }
    }

    private void getOTP(Button btGetOtp, String phonenumber) {
        btGetOtp.setText("Loading ...");
        if (phonenumber.isEmpty()) {
            Utils.setToast(this, "Please Enter Mobile");
            btGetOtp.setText(" Get OTP");
        } else if (Utils.isValidMobile(phonenumber)) {
            callOTPApi(phonenumber);
        } else {
            Utils.setToast(this, "Please enter Valid  Mobile Number.");
        }
    }

    private void callOTPApi(String mobileNumberString) {
        if (Utils.isNetworkAvailable(this)) {
            Utils.showProgressDialog(this);
            GenerateOtpModel model = new GenerateOtpModel(mobileNumberString, Utils.getDeviceUniqueID(this));
            getLoginData(model);
        } else {
            Utils.setToast(this, "No Internet Connection Please connect.");
        }

    }

    private void getLoginData(GenerateOtpModel model) {
        loginViewModel.getLogin(model).observe(this, new Observer<GenerateOtpResponseModel>() {
            @Override
            public void onChanged(GenerateOtpResponseModel model) {
                Utils.hideProgressDialog();
                if (model != null) {
                    if (model.isSuccess()) {
                        SharePrefs.getInstance(WelcomeActivity.this).putBoolean(SharePrefs.IS_USER_EXISTS, model.getResultItem().isUserExists());
                        SharePrefs.getInstance(WelcomeActivity.this).putBoolean(SharePrefs.RESULT, model.getResultItem().isResult());
                        int current = getItem(+1);
                        if (current < layouts.length) {
                            // move to next screen
                            viewPager.setCurrentItem(current);
                        }
                    } else {
                        Utils.setToast(WelcomeActivity.this, model.getErrorMessage());
                    }
                }


            }
        });

    }

    private void checkVerification(String mobileNumber, String otpString) {
        if (otpString.isEmpty()) {
            Utils.setToast(this, "Please Enter OTP");
        } else {
            callOTPVerfiyAPI(otpString, mobileNumber);
        }

    }

    private void callOTPVerfiyAPI(String otpString, String mobileNumber) {
        if (Utils.isNetworkAvailable(this)) {
            if (commonClassForAPI != null) {
                commonClassForAPI.VerfiyOtp(OTPVerfiyData, new OtpVerificationModel(mobileNumber, otpString, Utils.getDeviceUniqueID(this)));
            }
        } else {
            Utils.setToast(this, "No Internet Connection Please connect.");
        }
    }

    private DisposableObserver<OtpResponceModel> OTPVerfiyData = new DisposableObserver<OtpResponceModel>() {
        @Override
        public void onNext(OtpResponceModel model) {
            Utils.hideProgressDialog();
            if (model != null) {
                if (model.isSuccess()) {
                    SharePrefs.getInstance(WelcomeActivity.this).putBoolean(SharePrefs.IS_USER_EXISTS, model.getResultItem().getIsUserExist());
                    SharePrefs.getInstance(WelcomeActivity.this).putString(SharePrefs.USER_ID, model.getResultItem().getUserid());
                    int current = getItem(+1);
                    if (current < layouts.length) {
                        // move to next screen
                        viewPager.setCurrentItem(current);
                    }

                } else {
                    Utils.setToast(WelcomeActivity.this, "Please enter valid OTP.");
                }
            } else {
                Utils.setToast(WelcomeActivity.this, "Please enter valid OTP.");
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
