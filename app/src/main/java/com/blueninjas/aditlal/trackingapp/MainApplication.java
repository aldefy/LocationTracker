package com.blueninjas.aditlal.trackingapp;

import android.app.Application;

/**
 * Created by aditlal on 27/06/15.
 */
public class MainApplication extends Application {

    public static MainApplication application;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        //Parse.initialize(this, "ZPp2ZD3jLY0fhotdPB6dWDhfD8H3fVZQHXlmxA8j", "Z4PW0uNBzOSZcWhjWmUJsu6rvJUJGfaut9M1GQdP");

        // QBSettings.getInstance().fastConfigInit("24897", "eUhQ7RGVTErQBcR", "ygdnGsh8tDNmNLQ");
    }

    public static MainApplication getInstance() {
        return application;
    }


}
