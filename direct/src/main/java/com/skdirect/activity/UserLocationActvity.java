package com.skdirect.activity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.skdirect.R;
import com.skdirect.adapter.PlacesAutoCompleteAdapter;
import com.skdirect.databinding.ActivityUserLocationBinding;
import com.skdirect.location.EasyWayLocation;
import com.skdirect.location.Listener;
import com.skdirect.location.LocationData;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.GpsUtils;
import com.skdirect.utils.MySingltonApplication;
import com.skdirect.utils.TextUtils;
import com.skdirect.utils.Utils;

public class UserLocationActvity extends AppCompatActivity implements PlacesAutoCompleteAdapter.ClickListener, Listener, LocationData.AddressCallBack {
    private ActivityUserLocationBinding mBinding;
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    private String cityName = "", intentActivity;
    private boolean searchCity = false;
    private EasyWayLocation easyWayLocation;
    private DBHelper dbHelper;

    Boolean isGPS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_location);
        easyWayLocation = new EasyWayLocation(this, false, this);
        dbHelper = MySingltonApplication.getInstance().dbHelper;

        if (getIntent() != null) {
            intentActivity = getIntent().getStringExtra("activity");
            cityName = getIntent().getStringExtra("cityname");
            searchCity = getIntent().getBooleanExtra("searchCity", false);
        }
        initView();
    }

    private void initView() {

        if (!TextUtils.isNullOrEmpty(intentActivity)&& intentActivity.equalsIgnoreCase("PlaceSearch")){
            mBinding.tvOr.setVisibility(View.VISIBLE);
            mBinding.btUseCurrentLocation.setVisibility(View.VISIBLE);
        }else {
            mBinding.tvOr.setVisibility(View.GONE);
            mBinding.btUseCurrentLocation.setVisibility(View.GONE);
        }

        mBinding.address.setHint(dbHelper.getString(R.string.search_your_address));
        mBinding.tvOr.setText(dbHelper.getString(R.string.or));
        mBinding.btUseCurrentLocation.setText(dbHelper.getString(R.string.use_current_location));

        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        //mBinding.etSearchPlace.addTextChangedListener(filterTextWatcher);
        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this, searchCity);
        mAutoCompleteAdapter.setClickListener(this);
        mBinding.placesRecyclerView.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();
        mBinding.etSearchPlace.requestFocus();

        mBinding.imSearchPlace.setVisibility(View.VISIBLE);
        mBinding.progressSearch.setVisibility(View.INVISIBLE);

        mBinding.etSearchPlace.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchPlace();
                return true;
            }
            return false;
        });
        mBinding.imSearchPlace.setOnClickListener(view -> searchPlace());
        mBinding.btUseCurrentLocation.setOnClickListener(view -> getLocation());
        mBinding.toolbarTittle.ivBackPress.setOnClickListener(view -> onBackPressed());
    }

    private void getLocation() {
        int noOfSecond = 1;
        Utils.showProgressDialog(this);
        if (Utils.ISGPSON(UserLocationActvity.this)) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    //TODO Set your button auto perform click.
                    Utils.hideProgressDialog();
                    easyWayLocation.startLocation();
                }
            }, noOfSecond * 1000);

        } else {
            Utils.hideProgressDialog();

            new GpsUtils(UserLocationActvity.this).turnGPSOn(isGPSEnable -> {
                // turn on GPS
                isGPS = isGPSEnable;
            });
        }

    }

    @Override
    public void click(Place place) {
        Intent intent = new Intent();
        intent.putExtra("PlaceResult", place);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void searchPlace() {
        String s = mBinding.etSearchPlace.getText().toString();
        if (TextUtils.isNullOrEmpty(s)) {
            if (searchCity) {
                Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.please_enter_city_name));
            } else {
                Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.please_enter_address));
            }
        } else {
            mBinding.imSearchPlace.setVisibility(View.INVISIBLE);
            mBinding.progressSearch.setVisibility(View.VISIBLE);
            if (searchCity) {
                mAutoCompleteAdapter.getFilter().filter(s);
            } else {
                mAutoCompleteAdapter.getFilter().filter(cityName + ", " + s);
            }
        }
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            mBinding.imSearchPlace.setVisibility(View.VISIBLE);
            mBinding.progressSearch.setVisibility(View.INVISIBLE);
        }, 2000);
    }


    @Override
    public void locationData(LocationData locationData) {

    }

    @Override
    public void locationOn() {

    }

    @Override
    public void currentLocation(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Intent intent = new Intent();
        intent.putExtra("PlaceResult", latLng);
        setResult(3, intent);
        finish();
    }

    @Override
    public void locationCancelled() {

    }
}
