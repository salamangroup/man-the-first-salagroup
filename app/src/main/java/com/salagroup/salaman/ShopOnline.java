package com.salagroup.salaman;

import android.app.Application;

import com.salagroup.salaman.data.SQLiteDatasource;

public class ShopOnline extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // copy database to device if not exits
        SQLiteDatasource datasource = new SQLiteDatasource(getApplicationContext());
        datasource.closeDBConnection();
    }
}
