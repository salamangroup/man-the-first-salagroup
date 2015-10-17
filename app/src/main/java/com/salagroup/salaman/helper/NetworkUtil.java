package com.salagroup.salaman.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by TrytoThuan on 24/09/2015.
 */
public class NetworkUtil {

    public static int TYPE_MOBILE = 2;
    public static int TYPE_WIFI = 1;
    public static int TYPE_NOT_CONNECT = 0;
    private static NetworkInfo activeNetwork;

    /**
     * Check network state
     */
    public static boolean isNetworkConnected(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    /**
     * Get network type, return int type
     * TYPE_MOBILE = 2;
     * TYPE_WIFI = 1;
     * TYPE_NOT_CONNECT = 0;
     */
    public static int getConnectivityType(Context context) {

        if (NetworkUtil.isNetworkConnected(context)) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) return TYPE_MOBILE;
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) return TYPE_WIFI;
        }

        return TYPE_NOT_CONNECT;
    }


    /**
     * Check network type, return String
     * parameter "networkType" - network type
     * TYPE_MOBILE = 2;
     * TYPE_WIFI = 1;
     * TYPE_NOT_CONNECT = 0;
     */
    public static String getConnectivityStatus(int networkType) {

        String status = "";
        if (networkType == TYPE_MOBILE) {
            status = "Wifi Enabled";
        } else if (networkType == TYPE_WIFI) {
            status = "Mobile data enbled";
        } else if (networkType == TYPE_NOT_CONNECT) {
            status = "Not connected to Internet";
        }

        return status;
    }

    public static String getIpAddr(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();

        return String.format(
                "%d.%d.%d.%d",
                (ip & 0xff),
                (ip >> 8 & 0xff),
                (ip >> 16 & 0xff),
                (ip >> 24 & 0xff));
    }


}
