package com.blueninjas.aditlal.trackingapp;

import android.app.Application;

import com.pubnub.api.Pubnub;

/**
 * Created by aditlal on 27/06/15.
 */
public class MainApplication extends Application {

    public static MainApplication application;
    Pubnub pubnub;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        /* View the Full Documentation. */
            /* Instantiate PubNub */

        pubnub = new Pubnub("pub-c-d6f27666-edb5-48b5-a4bb-e376f417b0c6", "sub-c-daf541a8-2d4a-11e5-9a04-0619f8945a4f");
        //Parse.initialize(this, "ZPp2ZD3jLY0fhotdPB6dWDhfD8H3fVZQHXlmxA8j", "Z4PW0uNBzOSZcWhjWmUJsu6rvJUJGfaut9M1GQdP");

        // QBSettings.getInstance().fastConfigInit("24897", "eUhQ7RGVTErQBcR", "ygdnGsh8tDNmNLQ");
    }

    public static MainApplication getInstance() {
        return application;
    }

    public Pubnub getPubNub() {
        return pubnub;
    }


}
