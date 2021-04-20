package com.skdirect.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class GetLocationDetail {

    private LocationData.AddressCallBack addressCallBack;
    private Context context;

    public GetLocationDetail(LocationData.AddressCallBack addressCallBack, Context context) {
        this.addressCallBack = addressCallBack;
        this.context = context;
    }



    public void getAddress(Double latitude, Double longitude, String key) {
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                LocationData locationData = new LocationData();
                locationData.setCity(city);
                locationData.setFull_address(address);
                locationData.setPincode(postalCode);
                locationData.setCountry(country);
                addressCallBack.locationData(locationData);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
