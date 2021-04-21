package com.skdirect.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.skdirect.R;
import com.skdirect.databinding.ActivityChnagePasswordBinding;
import com.skdirect.model.ChangePasswordRequestModel;
import com.skdirect.model.CommonResponseModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.MySingltonApplication;
import com.skdirect.utils.TextUtils;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.ChangePasswordViewMode;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityChnagePasswordBinding mBinding;
    private ChangePasswordViewMode changePasswordViewMode;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chnage_password);
        changePasswordViewMode = ViewModelProviders.of(this).get(ChangePasswordViewMode.class);
        dbHelper = MySingltonApplication.getInstance().dbHelper;
        initView();
    }

    private void initView() {
        mBinding.toolbarTittle.tvTittle.setText(dbHelper.getString(R.string.change_password));
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(this);
        mBinding.btSavePassword.setOnClickListener(this);

        mBinding.etNewPassword.setHint(dbHelper.getString(R.string.enter_new_password));
        mBinding.etConfirmPassword.setHint(dbHelper.getString(R.string.enter_confirm_password));
        mBinding.etOtp.setHint(dbHelper.getString(R.string.enter_otp));
        mBinding.btSavePassword.setText(dbHelper.getString(R.string.save));
        mBinding.tvGetOtp.setText(dbHelper.getString(R.string.get_otp));
        mBinding.tvGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOtp();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back_press) {
            onBackPressed();
        } else if (id == R.id.bt_save_password) {
            changePassword();
        }
    }

    private void changePassword() {
        if (TextUtils.isNullOrEmpty(mBinding.etNewPassword.getText().toString().trim())) {
            Utils.setLongToast(getApplicationContext(), dbHelper.getString(R.string.please_enter_new_password));
        } else if (TextUtils.isNullOrEmpty(mBinding.etConfirmPassword.getText().toString().trim())) {
            Utils.setLongToast(getApplicationContext(), dbHelper.getString(R.string.please_enter_confirm_password));
        } else if (!mBinding.etConfirmPassword.getText().toString().trim().equalsIgnoreCase(mBinding.etNewPassword.getText().toString().trim())) {
            Utils.setLongToast(getApplicationContext(), dbHelper.getString(R.string.password_not_matched));
        } else if (TextUtils.isNullOrEmpty(mBinding.etOtp.getText().toString().trim())) {
            Utils.setLongToast(getApplicationContext(), dbHelper.getString(R.string.enter_otp));
        } else {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                Utils.showProgressDialog(ChangePasswordActivity.this);
                changePasswordAPI();

            } else {
                Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.no_internet_connection));
            }
        }
    }

    private void getOtp() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Utils.showProgressDialog(ChangePasswordActivity.this);
            GetOtpAPI();
        } else {
            Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.no_internet_connection));
        }
    }

    private void changePasswordAPI() {
        ChangePasswordRequestModel changePasswordRequestModel = new ChangePasswordRequestModel(mBinding.etNewPassword.getText().toString().trim(), mBinding.etConfirmPassword.getText().toString().trim(), mBinding.etOtp.getText().toString().trim());
        changePasswordViewMode.getChangePasswordRequest(changePasswordRequestModel);
        changePasswordViewMode.getChangePasswordVM().observe(this, new Observer<CommonResponseModel>() {
            @Override
            public void onChanged(CommonResponseModel model) {
                Utils.hideProgressDialog();
                if (model != null) {
                    if (model.isSuccess()) {
                        changePasswordDialog(model.getSuccessMessage());
                    } else {
                        Utils.setToast(ChangePasswordActivity.this, model.getErrorMessage());
                    }
                }

            }

        });
    }

    private void GetOtpAPI() {
        changePasswordViewMode.getOtpVM();
        changePasswordViewMode.getOtp().observe(this, new Observer<CommonResponseModel>() {
            @Override
            public void onChanged(CommonResponseModel model) {
                Utils.hideProgressDialog();
                if (model != null) {
                    if (model.isSuccess()) {
                        Utils.setToast(ChangePasswordActivity.this, model.getSuccessMessage());
                    }
                    else{
                        Utils.setToast(ChangePasswordActivity.this, model.getErrorMessage());



                    }
                }


            }

        });
    }

    private void changePasswordDialog(String successMessage) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(dbHelper.getString(R.string.congratulations));
        dialog.setMessage(successMessage);
        dialog.setPositiveButton(dbHelper.getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ChangePasswordActivity.this, MainActivity.class));
                        finish();

                    }
                });
        dialog.show();
    }

}
