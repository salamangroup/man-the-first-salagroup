package com.salagroup.salaman.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by TrytoThuan on 24/09/2015.
 */
public class NetworkUtil {

    public static int TYPE_MOBILE = 2;
    public static int TYPE_WIFI = 1;
    public static int TYPE_NOT_CONNECT = 0;
    private static NetworkInfo activeNetwork;

    public static boolean isNetworkConnected(Context context){

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static int getConnectivityType(Context context){

        if (NetworkUtil.isNetworkConnected(context)){
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) return TYPE_MOBILE;
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) return TYPE_WIFI;
        }

        return TYPE_NOT_CONNECT;
    }
    public static String getConnectivityStatus(int networkType){

        String status="";
        if (networkType == TYPE_MOBILE){
            status = "Wifi Enabled";
        }else if (networkType == TYPE_WIFI){
            status = "Mobile data enbled";
        }else if ( networkType == TYPE_NOT_CONNECT) {
            status ="Not connected to Internet";
        }

        return status;
    }

}
