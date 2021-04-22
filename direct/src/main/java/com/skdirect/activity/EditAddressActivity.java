package com.skdirect.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.skdirect.R;
import com.skdirect.databinding.ActivityEditAddreshBinding;
import com.skdirect.model.UpdateEditeAddreshMainModel;
import com.skdirect.model.UserLocationModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.GPSTracker;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.TextUtils;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.NewAddressViewMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class EditAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityEditAddreshBinding mBinding;
    private NewAddressViewMode newAddressViewMode;
    private GPSTracker gpsTracker;
    DBHelper dbHelper;
    private UserLocationModel userLocationModel;
    private Place place;
    private LatLng latLng;
    private Geocoder mGeocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_addresh);
        newAddressViewMode = ViewModelProviders.of(this).get(NewAddressViewMode.class);
        dbHelper = DirectSDK.getInstance().dbHelper;
        mGeocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        getIntentData();
        initView();
    }

    private void getIntentData() {
        userLocationModel = (UserLocationModel) getIntent().getSerializableExtra("UserEditData");
        if (userLocationModel != null) {
            mBinding.etFullName.setText(userLocationModel.getAddressOne());
            mBinding.etStreet.setText(userLocationModel.getAddressThree());
            mBinding.etLandmark.setText(userLocationModel.getAddressTwo());
            mBinding.etPinCode.setText(userLocationModel.getPincode());
            mBinding.etPinCity.setText(userLocationModel.getCity());
            mBinding.etPinState.setText(userLocationModel.getState());
            latLng = new LatLng(userLocationModel.getLatitiute(), userLocationModel.getLongitude());
        }

    }

    private void initView() {

        mBinding.tilFullName.setHint(dbHelper.getString(R.string.full_name));
        mBinding.tilStreetAdd.setHint(dbHelper.getString(R.string.street_address));
        mBinding.tilLandMark.setHint(dbHelper.getString(R.string.landmark_optional));
        mBinding.tilPincode.setHint(dbHelper.getString(R.string.pincode));
        mBinding.tilCity.setHint(dbHelper.getString(R.string.city));
        mBinding.tilState.setHint(dbHelper.getString(R.string.state));
        mBinding.btSaveAddresh.setText(dbHelper.getString(R.string.save));
        mBinding.toolbarTittle.tvUsingLocation.setText(dbHelper.getString(R.string.use_current_location));

        mBinding.toolbarTittle.tvUsingLocation.setVisibility(View.VISIBLE);
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(this);
        mBinding.toolbarTittle.tvUsingLocation.setOnClickListener(this);
        mBinding.btSaveAddresh.setOnClickListener(this);

        mBinding.etStreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), UserLocationActvity.class)
                        .putExtra("cityname", "")
                        .putExtra("activity", "EditAddress")
                        .putExtra("searchCity", false), 1001);

            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back_press) {
            onBackPressed();
        } else if (id == R.id.tv_using_location) {
            callLocationAPI();
        } else if (id == R.id.bt_save_addresh) {
            addLocation();
        }
    }

    private void addLocation() {
        if (TextUtils.isNullOrEmpty(mBinding.etFullName.getText().toString().trim())) {
            Utils.setToast(getApplicationContext(), "Please enter full name.");
        } else if (TextUtils.isNullOrEmpty(mBinding.etStreet.getText().toString().trim())) {
            Utils.setToast(getApplicationContext(), "Please enter street address.");
        } else if (TextUtils.isNullOrEmpty(mBinding.etPinCode.getText().toString().trim())) {
            Utils.setToast(getApplicationContext(), "Please enter pin code.");
        } else if (TextUtils.isNullOrEmpty(mBinding.etPinCity.getText().toString().trim())) {
            Utils.setToast(getApplicationContext(), "Please enter city name.");
        } else {
            if (Utils.isNetworkAvailable(getApplicationContext())) {
                Utils.showProgressDialog(EditAddressActivity.this);
                setLocationAPI();
            } else {
                Utils.setToast(getApplicationContext(), "No Internet Connection Please connect.");
            }
        }


    }

    private void setLocationAPI() {
        newAddressViewMode.getupdateLocationVMRequest(setUserValue());
        newAddressViewMode.getUpdateLocationVM().observe(this, new Observer<UpdateEditeAddreshMainModel>() {
            @Override
            public void onChanged(UpdateEditeAddreshMainModel status) {
                Utils.hideProgressDialog();
                if (status.getResultItem()) {
                    onBackPressed();
                    Utils.setToast(getApplicationContext(), status.getSuccessMessage());
                }
            }
        });
    }


    private JsonObject setUserValue() {
        gpsTracker = new GPSTracker(getApplicationContext());
        JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty("AddressOne", mBinding.etFullName.getText().toString());
            jsonObject.addProperty("AddressThree", mBinding.etStreet.getText().toString());
            jsonObject.addProperty("AddressTwo", mBinding.etLandmark.getText().toString());
            jsonObject.addProperty("Id", userLocationModel.getId());
            jsonObject.addProperty("Latitiute", latLng.latitude);
            jsonObject.addProperty("Longitude", latLng.longitude);
            jsonObject.addProperty("Pincode", mBinding.etPinCode.getText().toString());
            jsonObject.addProperty("City", mBinding.etPinCity.getText().toString());
            jsonObject.addProperty("State", mBinding.etPinState.getText().toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(jsonObject);

        return jsonObject;
    }

    private void callLocationAPI() {

        gpsTracker = new GPSTracker(getApplicationContext());

        if (Utils.isNetworkAvailable(getApplicationContext())) {
            if (gpsTracker != null) {
                Utils.showProgressDialog(this);
                latLng = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                callLocation(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            }

        } else {
            Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.no_connection));
        }
    }


    private void callLocation(double latitude, double longitude) {
        newAddressViewMode.getMapViewModelRequest(latitude, longitude);
        newAddressViewMode.getMapViewModel().observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject data) {
                Utils.hideProgressDialog();
                JSONObject jsonResponse = null;
                try {
                    jsonResponse = new JSONObject(data.toString());
                    if (jsonResponse.getBoolean("IsSuccess")) {
                        JSONObject resultItemObject = jsonResponse.getJSONObject("ResultItem");
                        if (resultItemObject != null) {
                            String StateName = resultItemObject.getString("StateName");
                            String CityName = resultItemObject.getString("CityName");
                            String Pincode = resultItemObject.getString("Pincode");
                            String Addressone = resultItemObject.getString("Addressone");
                            String Addresstwo = resultItemObject.getString("Addresstwo");
                            String Addressthree = resultItemObject.getString("Addressthree");
                            if (!TextUtils.isNullOrEmpty(Pincode)) {
                                mBinding.etPinCode.setText(Pincode);
                            }
                            if (!TextUtils.isNullOrEmpty(CityName)) {
                                mBinding.etPinCity.setText(CityName);
                            }
                            if (!TextUtils.isNullOrEmpty(StateName)) {
                                mBinding.etPinState.setText(StateName);
                            }
                            if (!TextUtils.isNullOrEmpty(Addressone)) {
                                mBinding.etStreet.setText(Addressone);
                            } else {
                                try {
                                    List<Address> addresses = mGeocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                                    if (!TextUtils.isNullOrEmpty(addresses.get(0).getFeatureName())) {
                                        mBinding.etStreet.setText(addresses.get(0).getFeatureName());
                                    }
                                    if (!TextUtils.isNullOrEmpty(addresses.get(0).getSubAdminArea())) {
                                        mBinding.etPinCity.setText(addresses.get(0).getSubAdminArea());
                                    }
                                    if (TextUtils.isNullOrEmpty(mBinding.etPinCode.getText().toString()) && !TextUtils.isNullOrEmpty(addresses.get(0).getPostalCode())) {
                                        mBinding.etPinCode.setText(addresses.get(0).getPostalCode());
                                    }
                                    if (TextUtils.isNullOrEmpty(mBinding.etPinState.getText().toString()) && !TextUtils.isNullOrEmpty(addresses.get(0).getAdminArea())) {
                                        mBinding.etPinState.setText(addresses.get(0).getAdminArea());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (!TextUtils.isNullOrEmpty(Addresstwo)) {
                                mBinding.etLandmark.setText(Addresstwo);
                            }

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 & resultCode == RESULT_OK) {
            place = data.getParcelableExtra("PlaceResult");
            latLng = place.getLatLng();
            callLocation(latLng.latitude, latLng.longitude);
           /* mBinding.etStreetAddresh.setText(place.getAddress());
            mBinding.etPinCity.setText(city);
            mBinding.etPinState.setText(state);
            mBinding.etPinCode.setText(pincode);*/

        }
    }
}
