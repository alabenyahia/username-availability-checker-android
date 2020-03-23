package com.pickurapps.usernameavailabilitychecker;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6185803298667574~7861772759");
    }
}
