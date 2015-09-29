package com.salagroup.salaman;

import android.app.Application;

import com.activeandroid.ActiveAndroid;


public class ShopOnline extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
