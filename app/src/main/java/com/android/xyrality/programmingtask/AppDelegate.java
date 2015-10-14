package com.android.xyrality.programmingtask;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.RejectedExecutionException;

public class AppDelegate {
    private static final String TAG = "AppDelegate";
    private static final String PREFERENCES_FILENAME = "Xyrality";

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
}

