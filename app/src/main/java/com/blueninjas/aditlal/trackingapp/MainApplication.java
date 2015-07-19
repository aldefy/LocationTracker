package com.blueninjas.aditlal.trackingapp;

import android.app.Application;

import com.blueninjas.aditlal.trackingapp.utils.Logger;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.pubnub.api.Pubnub;

/**
 * Created by aditlal on 27/06/15.
 */
public class MainApplication extends Application {

    public static MainApplication application;


    Pubnub pubnub;
    private String userId;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Logger.init("LocTrack");
        pubnub = new Pubnub("pub-c-d6f27666-edb5-48b5-a4bb-e376f417b0c6", "sub-c-daf541a8-2d4a-11e5-9a04-0619f8945a4f");

        // Enable Crash Reporting
        ParseCrashReporting.enable(this);

        // Enable local data store to save and use objects offline
       // Parse.enableLocalDatastore(this);

        // Register subclasses as extension of parse objects


        // Save the current Installation to Parse.
        // ParseInstallation.getCurrentInstallation().saveInBackground();
       /* Parse.initialize(this, "yzCJSEpMYxI2BMMX0bzVJp1f3uQ1GrAzthFwtRPy",
                "YkGd1WAfVLhUC4ltdpPwZJWiuqS3TZS5eYA0DLLo");

*/

        Parse.initialize(this, "ivlA3C2RfRAP5H4eRlgiOj6oGJ42fEr4a8j5H6Tn", "fSMAvvBiucqNPn3DvO8KEHrSFudoDLvbrR0RQy7s");


        // ParseInstallation.getCurrentInstallation().saveInBackground();
    }


    public static MainApplication getInstance() {
        return application;
    }

    public Pubnub getPubNub() {
        return pubnub;
    }

    public void setPubNub(Pubnub pubnub) {
        this.pubnub = pubnub;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        pubnub.setUUID(userId);
    }
}
