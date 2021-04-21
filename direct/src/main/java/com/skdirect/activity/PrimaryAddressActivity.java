package com.skdirect.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.skdirect.R;
import com.skdirect.adapter.UserLocationAdapter;
import com.skdirect.databinding.ActivityPrimaryAddressBinding;
import com.skdirect.model.UserLocationModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.MySingltonApplication;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.PrimaryAddressViewMode;

import java.util.ArrayList;

public class PrimaryAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityPrimaryAddressBinding mBinding;
    private PrimaryAddressViewMode primaryAddressViewMode;
    private final ArrayList<UserLocationModel> locationModelArrayList = new ArrayList<>();
    private UserLocationAdapter userLocationAdapter;
    private int userLocationId;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_primary_address);
        primaryAddressViewMode = ViewModelProviders.of(this).get(PrimaryAddressViewMode.class);
        dbHelper = MySingltonApplication.getInstance().dbHelper;
        getSharedData();
        initView();
        callUserLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        callUserLocation();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back_press) {
            onBackPressed();
        } else if (id == R.id.bt_selected_process) {
            if (locationModelArrayList.size() > 0) {
                Intent intent = new Intent();
                intent.putExtra("address", userLocationAdapter.getSelectedData());
                setResult(Activity.RESULT_OK, intent);
                finish();
            } else {
                Utils.setToast(getApplicationContext(), MySingltonApplication.getInstance().dbHelper.getString(R.string.please_enter_address));
            }
        } else if (id == R.id.bt_add_new_addresh) {
            startActivity(new Intent(getApplicationContext(), NewAddressActivity.class));
        }
    }


    private void initView() {
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(this);
        mBinding.toolbarTittle.tvTittle.setText(dbHelper.getString(R.string.address));
        mBinding.btSelectedProcess.setOnClickListener(this);
        mBinding.btAddNewAddresh.setOnClickListener(this);

        mBinding.btAddNewAddresh.setText(dbHelper.getString(R.string.add_new_address));
        mBinding.btSelectedProcess.setText(dbHelper.getString(R.string.select_amp_proceed));
        mBinding.tvEmpty.setText(dbHelper.getString(R.string.no_address_found));
    }

    private void getSharedData() {
        userLocationId = getIntent().getIntExtra("UserLocationId", 0);
    }

    private void callUserLocation() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Utils.showProgressDialog(PrimaryAddressActivity.this);
            userLocationAPI();
        } else {
            Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.no_internet_connection));
        }
    }

    private void userLocationAPI() {
        primaryAddressViewMode.getUserLocationVMRequest();
        primaryAddressViewMode.getUserLocationVM().observe(this, locationModel -> {
            mBinding.tvEmpty.setVisibility(View.GONE);
            Utils.hideProgressDialog();
            locationModelArrayList.clear();
            if (locationModel.isSuccess()) {
                if (locationModel.getResultItem().size() > 0) {
                    locationModelArrayList.addAll(locationModel.getResultItem());
                    int position = 0;
                    for (int i = 0; i < locationModelArrayList.size(); i++) {
                        if (locationModelArrayList.get(i).isPrimaryAddress()) {
                            position = i;
                            break;
                        }
                    }
                    userLocationAdapter = new UserLocationAdapter(PrimaryAddressActivity.this, locationModelArrayList, position);
                    mBinding.rvUserLocation.setAdapter(userLocationAdapter);
                }
            } else {
                mBinding.tvEmpty.setVisibility(View.VISIBLE);
            }
        });
    }
}
