package com.example.henryf.pryeasypaybar;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by HenryF on 20/02/2017.
 */

public class PryEasyPayBarApp extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
