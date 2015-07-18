package com.blueninjas.aditlal.trackingapp.utils;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by aditlal on 26/05/15.
 */
public class DeviceUtils {

    public static String getIMEI(Context c) {
        TelephonyManager telephonyManager = (TelephonyManager) c
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static String getDeviceUniqueID(Context c) {
        String device_unique_id = Settings.Secure.getString(c.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }


}
