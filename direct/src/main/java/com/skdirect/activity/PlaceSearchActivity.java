package com.skdirect.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.gson.JsonObject;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.skdirect.R;
import com.skdirect.api.CommonClassForAPI;
import com.skdirect.databinding.ActivityPlacesSearchBinding;
import com.skdirect.model.TokenModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.TextUtils;
import com.skdirect.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.observers.DisposableObserver;

public class PlaceSearchActivity extends AppCompatActivity implements View.OnClickListener {
    private final int REQUEST_FOR_ADDRESS = 1001;
    private ActivityPlacesSearchBinding mBinding;
    private Place place;
    private Geocoder mGeocoder;
    private LatLng latLng;

    private CommonClassForAPI commonClassForAPI;
    private String fcmToken,type;
    private String pinCode,city,state;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_places_search);
        dbHelper = DirectSDK.getInstance().dbHelper;
        initView();

    }

    private void initView() {
        mBinding.etLocation.setHint(dbHelper.getString(R.string.enter_your_address));
        mBinding.etPinCode.setHint(dbHelper.getString(R.string.enter_pin_code));
        mBinding.btSave.setHint(dbHelper.getString(R.string.save));
        commonClassForAPI = CommonClassForAPI.getInstance(this);
        fcmToken = Utils.getFcmToken();
        mGeocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        mBinding.etLocation.setOnClickListener(this);
        mBinding.btSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.et_location) {
            callRunTimePermissions();
        } else if (id == R.id.bt_save) {
            saveLocationData();
        }
    }

    private void saveLocationData() {
        if (TextUtils.isNullOrEmpty(mBinding.etLocation.getText().toString().trim())) {
            Utils.setToast(this, dbHelper.getString(R.string.enter_your_address));
        } else if (TextUtils.isNullOrEmpty(mBinding.etPinCode.getText().toString().trim())) {
            Utils.setToast(this, dbHelper.getString(R.string.enter_pin_code));
        } else {
            if (Utils.isNetworkAvailable(this)) {
                if (commonClassForAPI != null) {
                    commonClassForAPI.getTokenwithphoneNo(callToken, "password", Utils.getDeviceUniqueID(PlaceSearchActivity.this), Utils.getDeviceUniqueID(PlaceSearchActivity.this), true, true, "BUYERAPP", true, Utils.getDeviceUniqueID(PlaceSearchActivity.this), latLng.latitude, latLng.longitude, pinCode, "",SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.MOBILE_NUMBER),SharePrefs.getInstance(this).getString(SharePrefs.SOURCEKEY));
                }
            } else {
                Utils.setToast(this, dbHelper.getString(R.string.no_internet_connection));
            }
        }
    }

    private void findLocation() {
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        }
        startActivityForResult(new Intent(getApplicationContext(), UserLocationActvity.class)
                .putExtra("activity", "PlaceSearch")
                .putExtra("cityname", "")
                .putExtra("searchCity", false), REQUEST_FOR_ADDRESS);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FOR_ADDRESS & resultCode == RESULT_OK) {
            //place = Autocomplete.getPlaceFromIntent(data);
            place = data.getParcelableExtra("PlaceResult");
            mBinding.etLocation.setText(place.getAddress());
            try {
                latLng = place.getLatLng();
                List<Address> addresses = mGeocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                if (addresses.get(0).getLocality() != null) {
                    pinCode = addresses.get(0).getPostalCode();
                    city = addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();
                    String tempCity = addresses.get(0).getLocality();
                    mBinding.etPinCode.setText(pinCode);
                    if (TextUtils.isNullOrEmpty(mBinding.etPinCode.getText().toString())) {
                        mBinding.etPinCode.setFocusable(true);
                        mBinding.etPinCode.setFocusableInTouchMode(true);
                    } else {
                        mBinding.etPinCode.setFocusable(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == 3) {
            try {
                latLng = data.getParcelableExtra("PlaceResult");
                List<Address> addresses = mGeocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                mBinding.etLocation.setText(addresses.get(0).getAddressLine(0));
                if (addresses.get(0).getLocality() != null) {
                    pinCode = addresses.get(0).getPostalCode();
                    String tempCity = addresses.get(0).getLocality();
                    mBinding.etPinCode.setText(pinCode);
                    if (TextUtils.isNullOrEmpty(mBinding.etPinCode.getText().toString())) {
                        mBinding.etPinCode.setFocusable(true);
                        mBinding.etPinCode.setFocusableInTouchMode(true);
                    } else {
                        mBinding.etPinCode.setFocusable(false);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void callRunTimePermissions() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                Log.e("onDenied", "onGranted");
                findLocation();
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                Log.e("onDenied", "onDenied" + deniedPermissions);

            }
        });
    }

    private final DisposableObserver<TokenModel> callToken = new DisposableObserver<TokenModel>() {
        @Override
        public void onNext(@NotNull TokenModel model) {
            try {
                Utils.hideProgressDialog();
                if (model != null) {
                    Utils.getTokenData(getApplicationContext(),model);
                    commonClassForAPI.getUpdateToken(updatecallToken, fcmToken);
                    SharePrefs.setSharedPreference(getApplicationContext(), SharePrefs.Is_First_Time, true);
                    if (model.getIsRegistrationComplete()) {
                        SharePrefs.getInstance(getApplicationContext()).putBoolean(SharePrefs.IS_LOGIN, true);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), RegisterationActivity.class));
                    }
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
               // Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.invalid_pass));
            }
        }

        @Override
        public void onError(Throwable e) {
            Utils.setToast(getApplicationContext(), "Wrong key");
            Utils.hideProgressDialog();
            e.printStackTrace();
            startActivity(new Intent(getApplicationContext(),SocialMallLendingActivity.class).putExtra("Error","Wrong Key"));
            finish();
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

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
            Utils.hideProgressDialog();
        }

        @Override
        public void onComplete() {
            Utils.hideProgressDialog();
        }
    };

}
