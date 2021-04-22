package com.demo;

import android.app.Application;

import com.skdirect.utils.DirectSDK;

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DirectSDK.initialize(this);
    }
}
