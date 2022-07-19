package com.mes.stima;


import android.app.Application;

import timber.log.Timber;

public class StimaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
