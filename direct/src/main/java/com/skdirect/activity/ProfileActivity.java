package com.skdirect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.skdirect.R;
import com.skdirect.databinding.ActivityProfileBinding;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.MySingltonApplication;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.TextUtils;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityProfileBinding mBinding;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        dbHelper = MySingltonApplication.getInstance().dbHelper;
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
       // getProfileData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back_press) {
            onBackPressed();
        } else if (id == R.id.rl_seller_info) {
            startActivity(new Intent(ProfileActivity.this, UpdateProfileActivity.class));
        } else if (id == R.id.rl_my_order) {
            startActivity(new Intent(ProfileActivity.this, MyOrderActivity.class));
        } else if (id == R.id.rl_my_address) {
            startActivity(new Intent(ProfileActivity.this, ProfilePrimaryAddressActivity.class));
        } else if (id == R.id.rl_chnage_password) {
            startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class));
        }
    }


    private void initView() {
        mBinding.tvSellerName.setText(dbHelper.getString(R.string.user_name));
        mBinding.tvMyOrder.setText(dbHelper.getString(R.string.my_order));
        mBinding.tvCheckOrderStatus.setText(dbHelper.getString(R.string.check_your_order_status));
        mBinding.tvMyAddress.setText(dbHelper.getString(R.string.my_address));
        mBinding.tvSavedAddress.setText(dbHelper.getString(R.string.save_address_for_a_hassle_free_checkout));
        mBinding.tvChnagePassword.setText(dbHelper.getString(R.string.change_password));
        mBinding.tvMangePassword.setText(dbHelper.getString(R.string.manage_your_password));

        mBinding.toolbarTittle.tvTittle.setText(dbHelper.getString(R.string.profile));
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(this);
        mBinding.rlMyAddress.setOnClickListener(this);
        mBinding.rlSellerInfo.setOnClickListener(this);
        mBinding.rlChnagePassword.setOnClickListener(this);
        mBinding.rlMyOrder.setOnClickListener(this);
        if (!TextUtils.isNullOrEmpty(SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.FIRST_NAME))) {
            mBinding.tvSellerName.setText(SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.FIRST_NAME));
        }
        mBinding.tvMobileNumber.setText(SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.MOBILE_NUMBER));
    }

    /*private void getProfileData() {
        CommonClassForAPI.getInstance(this).getUserDetail(new DisposableObserver<UserDetailResponseModel>() {
            @Override
            public void onNext(@NonNull UserDetailResponseModel customerDataModel) {
                if (customerDataModel.getIsSuccess()) {
                    SharePrefs.getInstance(ProfileActivity.this).putString(SharePrefs.FIRST_NAME, customerDataModel.getResultItem().getFirstName());
                    SharePrefs.getInstance(ProfileActivity.this).putInt(SharePrefs.ID, customerDataModel.getResultItem().getId());
                    SharePrefs.getInstance(ProfileActivity.this).putString(SharePrefs.MOBILE_NUMBER, customerDataModel.getResultItem().getMobileNo());
                    SharePrefs.getInstance(ProfileActivity.this).putString(SharePrefs.EMAIL_ID, customerDataModel.getResultItem().getEmail());
                    SharePrefs.getInstance(ProfileActivity.this).putString(SharePrefs.STATE, customerDataModel.getResultItem().getState());
                    SharePrefs.getInstance(ProfileActivity.this).putString(SharePrefs.CITYNAME, customerDataModel.getResultItem().getCity());
                    SharePrefs.getInstance(ProfileActivity.this).putString(SharePrefs.PIN_CODE, customerDataModel.getResultItem().getPincode());
                    SharePrefs.getInstance(ProfileActivity.this).putInt(SharePrefs.PIN_CODE_master, customerDataModel.getResultItem().getPinCodeMasterId());
                    SharePrefs.getInstance(ProfileActivity.this).putBoolean(SharePrefs.IS_ACTIVE, customerDataModel.getResultItem().getIsActive());
                    SharePrefs.getInstance(ProfileActivity.this).putBoolean(SharePrefs.IS_DELETE, customerDataModel.getResultItem().getIsDelete());
                    SharePrefs.getInstance(ProfileActivity.this).putString(SharePrefs.CITYNAME, customerDataModel.getResultItem().getCity());
                    SharePrefs.setSharedPreference(ProfileActivity.this, SharePrefs.IS_REGISTRATIONCOMPLETE, customerDataModel.getResultItem().getIsRegistrationComplete());
                    SharePrefs.setStringSharedPreference(ProfileActivity.this, SharePrefs.LAT, String.valueOf(customerDataModel.getResultItem().getLatitiute()));
                    SharePrefs.setStringSharedPreference(ProfileActivity.this, SharePrefs.LON, String.valueOf(customerDataModel.getResultItem().getLongitude()));
                    if (customerDataModel.getResultItem().getUserDeliveryDC() != null && customerDataModel.getResultItem().getUserDeliveryDC().size() > 0) {
                        for (int i = 0; i < customerDataModel.getResultItem().getUserDeliveryDC().size(); i++) {
                            SharePrefs.getInstance(ProfileActivity.this).putBoolean(SharePrefs.USER_IS_DELETE, customerDataModel.getResultItem().getUserDeliveryDC().get(i).getIsDelete());
                            SharePrefs.getInstance(ProfileActivity.this).putBoolean(SharePrefs.USER_IS_ACTIVE, customerDataModel.getResultItem().getUserDeliveryDC().get(i).getIsActive());
                            SharePrefs.getInstance(ProfileActivity.this).putInt(SharePrefs.USER_DC_ID, customerDataModel.getResultItem().getUserDeliveryDC().get(i).getId());
                            SharePrefs.getInstance(ProfileActivity.this).putInt(SharePrefs.USER_DC_USER_ID, customerDataModel.getResultItem().getUserDeliveryDC().get(i).getUserId());
                            SharePrefs.getInstance(ProfileActivity.this).putString(SharePrefs.DELIVERY, customerDataModel.getResultItem().getUserDeliveryDC().get(i).getDelivery());
                        }


                    }
                    if (!TextUtils.isNullOrEmpty(SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.FIRST_NAME))) {
                        mBinding.tvSellerName.setText(SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.FIRST_NAME));
                    }
                    mBinding.tvMobileNumber.setText(SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.MOBILE_NUMBER));
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }*/
}
