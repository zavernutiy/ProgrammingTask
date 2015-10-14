package com.android.xyrality.programmingtask;

import android.app.Application;

/**
 * Created by Max on 10/14/2015.
 */
public class ProgrammingTaskApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppDelegate.create(this);
    }
}
