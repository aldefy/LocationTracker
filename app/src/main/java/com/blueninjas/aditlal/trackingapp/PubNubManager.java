package com.blueninjas.aditlal.trackingapp;

import android.util.Log;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aditlal on 18/07/15.
 */
public class PubNubManager {

    public final static String TAG = "PUBNUB";
    MainApplication application;

    public static Pubnub startPubnub() {
        Log.d(TAG, "Initializing PubNub");
        return new Pubnub("pub-c-d6f27666-edb5-48b5-a4bb-e376f417b0c6", "sub-c-daf541a8-2d4a-11e5-9a04-0619f8945a4f");
    }

    public static void subscribe(Pubnub mPubnub, String channelName, Callback subscribeCallback) {
        // Subscribe to channel
        try {
            mPubnub.subscribe(channelName, subscribeCallback);
            Log.d(TAG, "Subscribed to Channel");
        } catch (PubnubException e) {
            Log.e(TAG, e.toString());
        }
    }



    public static void broadcastLocation(Pubnub pubnub, String channelName, double latitude,
                                         double longitude, String user) {
        JSONObject message = new JSONObject();
        try {
            message.put("lat", latitude);
            message.put("lon", longitude);
            message.put("user", user);
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
        }
        Log.d(TAG, "Sending JSON Message: " + message.toString());
        pubnub.publish(channelName, message, publishCallback);
    }

    public static Callback publishCallback = new Callback() {

        @Override
        public void successCallback(String channel, Object response) {
            Log.d("PUBNUB", "Sent Message: " + response.toString());
        }

        @Override
        public void errorCallback(String channel, PubnubError error) {
            Log.d("PUBNUB", error.toString());
        }
    };
}

