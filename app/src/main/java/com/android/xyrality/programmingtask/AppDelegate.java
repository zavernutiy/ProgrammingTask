package com.android.xyrality.programmingtask;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import java.util.concurrent.RejectedExecutionException;

public class AppDelegate {
    private static final String TAG = "AppDelegate";
    private static final String PREFERENCES_FILENAME = "Xyrality";

    public static final String USERNAME_TAG = "Username";
    public static final String PASSWORD_TAG = "Password";

    private static AppDelegate  instance;
    private static Context context;


    private AppDelegate() {

    }

    public static void create(Context context) {
        if ( AppDelegate.instance == null ) {
            AppDelegate.instance = new AppDelegate();
            AppDelegate.context = context;
        } else {
            throw new RejectedExecutionException(AppDelegate.class.getName() + " cannot be created again.");
        }
    }

    public static AppDelegate getInstance() {
        if ( AppDelegate.instance == null ) {
            throw new RejectedExecutionException(AppDelegate.class.getName() + " not init.");
        }

        return AppDelegate.instance;
    }

    public static Context getContext() {
        return AppDelegate.context;
    }

    public static SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_PRIVATE);
    }

    public static String getDeviceType() {
        return String.format("%s%s", Build.MODEL, Build.VERSION.RELEASE);
    }

    public static String getDeviceId(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        return wInfo.getMacAddress();
    }
}

