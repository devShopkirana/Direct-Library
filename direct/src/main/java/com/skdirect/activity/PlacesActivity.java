package com.skdirect.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.skdirect.R;
import com.skdirect.adapter.PlacesAutoCompleteMapsAdapter;
import com.skdirect.databinding.ActivityPlacesBinding;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.GpsUtils;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.Utils;


public class PlacesActivity extends AppCompatActivity implements PlacesAutoCompleteMapsAdapter.ClickListener {
    public ActivityPlacesBinding binding;
    private PlacesAutoCompleteMapsAdapter mAutoCompleteAdapter;
    private Boolean searchCity = false;
    DBHelper dbHelper;
    Boolean isGPS = false;

    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        if (overrideConfiguration != null) {
            int uiMode = overrideConfiguration.uiMode;
            overrideConfiguration.setTo(getBaseContext().getResources().getConfiguration());
            overrideConfiguration.uiMode = uiMode;
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_places);
        dbHelper = DirectSDK.getInstance().dbHelper;

        binding.llGps.setOnClickListener(V -> {
            if(Utils.ISGPSON(PlacesActivity.this)){
                startActivityForResult(new Intent(PlacesActivity.this, MapsExtendedActivity.class),2000);
                finish();
            }
            else{
                new GpsUtils(PlacesActivity.this).turnGPSOn(isGPSEnable -> {
                    // turn on GPS
                    isGPS = isGPSEnable;

                });
            }

        });
        binding.ivBack.setOnClickListener(V -> finish());
        initPlaces();
    }

    public void initPlaces() {

        binding.etSearchPlace.setHint(dbHelper.getString(R.string.search_for));
        binding.tvCurrentLocation.setText(dbHelper.getString(R.string.current_location));
        binding.tvSearchResult.setText(dbHelper.getString(R.string.search_results));

        Places.initialize(this, getResources().getString(R.string.google_maps_key_for_mall_use));
        binding.etSearchPlace.addTextChangedListener(filterTextWatcher);
        mAutoCompleteAdapter = new PlacesAutoCompleteMapsAdapter(this, searchCity);
        binding.rvPlaces.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteAdapter.setClickListener(this);
        binding.rvPlaces.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();
        binding.etSearchPlace.requestFocus();
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("")) {
                if (searchCity) {
                    mAutoCompleteAdapter.getFilter().filter(s.toString());
                } else {
                    mAutoCompleteAdapter.getFilter().filter(s.toString());
                }
                binding.placesProgress.setVisibility(View.VISIBLE);
                binding.ivCancel.setVisibility(View.GONE);
                binding.llPlaces.setVisibility(View.VISIBLE);
            } else {
                binding.placesProgress.setVisibility(View.GONE);
                binding.ivCancel.setVisibility(View.GONE);
                binding.llPlaces.setVisibility(View.GONE);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    @Override
    public void click(Place place) {
        try {
            LatLng latLng = place.getLatLng();

            Intent returnIntent = new Intent();
            assert latLng != null;

            returnIntent.putExtra("latitude", latLng.latitude);
            returnIntent.putExtra("longitude", latLng.longitude);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}