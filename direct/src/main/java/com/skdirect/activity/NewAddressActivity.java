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
import com.google.gson.JsonObject;
import com.skdirect.R;
import com.skdirect.databinding.ActivityNewAddreshBinding;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.GPSTracker;
import com.skdirect.utils.MySingltonApplication;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.TextUtils;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.NewAddressViewMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class NewAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityNewAddreshBinding mBinding;
    private NewAddressViewMode newAddressViewMode;
    private GPSTracker gpsTracker;
    private DBHelper dbHelper;
    String city, pincode, state, Address;
    private Place place;
    private Geocoder mGeocoder;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_addresh);
        newAddressViewMode = ViewModelProviders.of(this).get(NewAddressViewMode.class);
        dbHelper = MySingltonApplication.getInstance().dbHelper;
        initView();
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

    private void initView() {
        mGeocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        mBinding.tilFullName.setHint(dbHelper.getString(R.string.full_name));
        mBinding.tilStreetAdd.setHint(dbHelper.getString(R.string.street_address));
        mBinding.tilLandMark.setHint(dbHelper.getString(R.string.landmark_optional));
        mBinding.tilPincode.setHint(dbHelper.getString(R.string.pincode));
        mBinding.tilCity.setHint(dbHelper.getString(R.string.city));
        mBinding.tilState.setHint(dbHelper.getString(R.string.state));
        mBinding.btSaveAddresh.setText(dbHelper.getString(R.string.save));
        mBinding.toolbarTittle.tvUsingLocation.setText(dbHelper.getString(R.string.use_current_location));

      /*  mBinding.etStreet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                startActivityForResult(new Intent(getApplicationContext(), UserLocationActvity.class)
                        .putExtra("cityname", "")
                        .putExtra("searchCity", false), 1001);

            }
        });*/
        mBinding.etStreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getApplicationContext(), UserLocationActvity.class)
                        .putExtra("cityname", "")
                        .putExtra("searchCity", false), 1001);

            }
        });

        mBinding.toolbarTittle.tvUsingLocation.setVisibility(View.VISIBLE);
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(this);
        mBinding.toolbarTittle.tvUsingLocation.setOnClickListener(this);
        mBinding.btSaveAddresh.setOnClickListener(this);

        /*mBinding.etFullName.setText(SharePrefs.getInstance(NewAddressActivity.this).getString(SharePrefs.FIRST_NAME));
        mBinding.tilStreetAdd.setText(SharePrefs.getInstance(NewAddressActivity.this).getString(SharePrefs.));
        mBinding.tilLandMark.setText(SharePrefs.getInstance(NewAddressActivity.this).getString(SharePrefs.FIRST_NAME));
        mBinding.tilPincode.setText(SharePrefs.getInstance(NewAddressActivity.this).getString(SharePrefs.PIN_CODE));
        mBinding.tilCity.setText(SharePrefs.getInstance(NewAddressActivity.this).getString(SharePrefs.CITY_NAME));
        mBinding.tilState.setText(SharePrefs.getInstance(NewAddressActivity.this).getString(SharePrefs.STATE));*/
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
                Utils.showProgressDialog(NewAddressActivity.this);
                setLocationAPI();
            } else {
                Utils.setToast(getApplicationContext(), "No Internet Connection Please connect.");
            }
        }


    }

    private void setLocationAPI() {
        newAddressViewMode.getAddLocationVMRequest(setUserValue());
        newAddressViewMode.getAddLocationVM().observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject jsonObject) {
                Utils.hideProgressDialog();
                if (jsonObject.get("IsSuccess").getAsBoolean()) {
                    onBackPressed();
                } else {
                    Utils.setToast(getApplicationContext(), jsonObject.get("ErrorMessage").getAsString());
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

    private JsonObject setUserValue() {
        gpsTracker = new GPSTracker(getApplicationContext());
        JsonObject jsonObject = new JsonObject();
        try {
            jsonObject.addProperty("AddressOne", mBinding.etFullName.getText().toString());
            jsonObject.addProperty("AddressThree", mBinding.etStreet.getText().toString());
            jsonObject.addProperty("AddressTwo", mBinding.etLandmark.getText().toString());
            jsonObject.addProperty("Id", "");
            jsonObject.addProperty("IsActive", SharePrefs.getInstance(NewAddressActivity.this).getBoolean(SharePrefs.IS_ACTIVE));
            jsonObject.addProperty("IsDelete", SharePrefs.getInstance(NewAddressActivity.this).getBoolean(SharePrefs.IS_DELETE));
            jsonObject.addProperty("IsPrimaryAddress", "");
            jsonObject.addProperty("Latitiute", latLng.latitude);
            jsonObject.addProperty("Longitude", latLng.longitude);
            jsonObject.addProperty("LocationType", "");
            jsonObject.addProperty("Pincode", mBinding.etPinCode.getText().toString());
            jsonObject.addProperty("UserDetailId", "");
            jsonObject.addProperty("City", mBinding.etPinCity.getText().toString());
            jsonObject.addProperty("State", mBinding.etPinState.getText().toString());

        } catch (Exception e) {

            e.printStackTrace();
        }
        //   JsonArray jsonArray = new JsonArray();
        // jsonArray.add(jsonObject);

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


}
