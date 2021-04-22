package com.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.skdirect.activity.SocialMallLendingActivity;
import com.skdirect.activity.SplashActivity;
import com.skdirect.utils.GPSTracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        callRunTimePermissions();
        findViewById(R.id.btnOpen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
                Geocoder mGeocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> addresses = null;
                String address = "",pincode = "";
                try {
                    addresses = mGeocoder.getFromLocation(gpsTracker.getLatitude(), gpsTracker.getLongitude(), 1);
                    address =  addresses.get(0).getAddressLine(0);
                    pincode = addresses.get(0).getPostalCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), SocialMallLendingActivity.class);
                intent.putExtra("MOBILE_NUMBER", "9658432156");
                intent.putExtra("BUYERNAME", "BUYERNAME");
                intent.putExtra("SOURCEKEY", "73F6CF7B-7C14-48B1-A392-C0590AB6A06C");
                intent.putExtra("ADDRESS", address);
                intent.putExtra("PINCODE", pincode);
                intent.putExtra("LATITUDE", gpsTracker.getLatitude());
                intent.putExtra("LONGITUDE", gpsTracker.getLongitude());
                startActivityForResult(intent, 1199);
            }
        });

    }
    public void callRunTimePermissions() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(this/*context*/, permissions, null/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onGranted() {
                Log.e("onDenied", "onGranted");
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                Log.e("onDenied", "onDenied" + deniedPermissions);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1199) {
            if (data != null && resultCode == RESULT_OK) {
                System.out.println("data::"+data.toString());
            }else
            {
                System.out.println("data::"+data.getStringExtra("Error"));
               // Toast.makeText(this, data.getStringExtra("Error"), Toast.LENGTH_SHORT).show();
            }

        }
    }
}