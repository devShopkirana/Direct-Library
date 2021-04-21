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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.R;
import com.skdirect.adapter.ProfileUserLocationAdapter;
import com.skdirect.databinding.ActivityProfilePrimaryAddressBinding;
import com.skdirect.interfacee.MakeDefaultInterface;
import com.skdirect.model.MainLocationModel;
import com.skdirect.model.UserLocationModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.MySingltonApplication;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.PrimaryAddressViewMode;

import java.util.ArrayList;

public class ProfilePrimaryAddressActivity extends AppCompatActivity implements View.OnClickListener, MakeDefaultInterface {
    private ActivityProfilePrimaryAddressBinding mBinding;
    private PrimaryAddressViewMode primaryAddressViewMode;
    private ArrayList<UserLocationModel> locationModelArrayList = new ArrayList<>();
    private ProfileUserLocationAdapter userLocationAdapter;
    private int userLocationId;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile_primary_address);
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

    private void getSharedData() {
        userLocationId = getIntent().getIntExtra("UserLocationId", 0);
    }

    private void initView() {
        mBinding.btAddNewAddresh.setText(dbHelper.getString(R.string.add_new_address));
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(this);
        mBinding.toolbarTittle.tvTittle.setText(dbHelper.getString(R.string.address));
        mBinding.btAddNewAddresh.setOnClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        mBinding.rvUserLocation.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back_press) {
            onBackPressed();
        } else if (id == R.id.bt_add_new_addresh) {
            startActivity(new Intent(ProfilePrimaryAddressActivity.this, NewAddressActivity.class));
        }
    }

    private void callUserLocation() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Utils.showProgressDialog(ProfilePrimaryAddressActivity.this);
            userLocationAPI();
        } else {
            Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.no_internet_connection));
        }
    }

    private void userLocationAPI() {
        primaryAddressViewMode.getUserLocationVMRequest();
        primaryAddressViewMode.getUserLocationVM().observe(this, new Observer<MainLocationModel>() {
            @Override
            public void onChanged(MainLocationModel locationModel) {
                Utils.hideProgressDialog();
                if (locationModel.isSuccess()) {
                    if (locationModel.getResultItem().size() > 0) {
                        locationModelArrayList.clear();
                        locationModelArrayList.addAll(locationModel.getResultItem());
                        int position = 0;
                        for (int i = 0; i < locationModelArrayList.size(); i++) {
                            if (locationModelArrayList.get(i).isPrimaryAddress()) {
                                position = i;
                                break;
                            }
                        }
                        userLocationAdapter = new ProfileUserLocationAdapter(ProfilePrimaryAddressActivity.this, locationModelArrayList, position,ProfilePrimaryAddressActivity.this);
                        mBinding.rvUserLocation.setAdapter(userLocationAdapter);
                    }

                }
            }
        });
    }

    @Override
    public void defaultOnClick(UserLocationModel userLocationModel, int position) {
        makeDefaultLocationDialgo(userLocationModel,position);
    }



    private void makeDefaultLocationDialgo(UserLocationModel userLocationModel, int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(dbHelper.getString(R.string.alert));
            builder.setMessage(dbHelper.getString(R.string.make_default_address));
            builder.setPositiveButton(dbHelper.getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    primaryAddressViewMode.getMakeDefaultLocationVMRequest(userLocationModel.getId());
                    primaryAddressViewMode.getMakeDefaultLocationVM().observe(ProfilePrimaryAddressActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            Utils.hideProgressDialog();
                            if (aBoolean){

                                userLocationAdapter.selectedPosition=position;
                                userLocationAdapter.notifyDataSetChanged();

                            }
                        }
                    });


                }
            });

            builder.setNegativeButton(dbHelper.getString(R.string.no), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }

            });
            AlertDialog dialog = builder.create();
            dialog.show();
    }

    @Override
    public void deleteLocationClick(UserLocationModel userLocationModel) {
        deleteLocationDialog(userLocationModel);

    }

    private void deleteLocationDialog(UserLocationModel userLocationModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(dbHelper.getString(R.string.alert));
        builder.setMessage(dbHelper.getString(R.string.want_to_delete));
        builder.setPositiveButton(dbHelper.getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ArrayList<UserLocationModel> locationModels = new ArrayList<>();
                UserLocationModel UserLocationModel = new UserLocationModel(userLocationModel.isRegistrationComplete(),userLocationModel.getCity(),userLocationModel.getState(),userLocationModel.getPincode(),userLocationModel.getLongitude(),userLocationModel.getLatitiute(),true,userLocationModel.isActive(),userLocationModel.getId(),userLocationModel.isPrimaryAddress(),userLocationModel.getLocationType(),userLocationModel.getPinCodeMasterId(),userLocationModel.getAddressThree(),userLocationModel.getAddressTwo(),userLocationModel.getAddressOne(),userLocationModel.getUserDetailId());
                locationModels.add(UserLocationModel);
                primaryAddressViewMode.getDeleteLocationVMRequest(locationModels);
                primaryAddressViewMode.getDeleteLocationVM().observe(ProfilePrimaryAddressActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        Utils.hideProgressDialog();
                        if (aBoolean){
                            locationModelArrayList.clear();
                            userLocationAdapter.notifyDataSetChanged();
                            callUserLocation();
                        }
                    }
                });
            }
        });

        builder.setNegativeButton(dbHelper.getString(R.string.no), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
