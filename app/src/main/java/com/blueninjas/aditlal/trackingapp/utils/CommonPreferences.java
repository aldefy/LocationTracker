package com.blueninjas.aditlal.trackingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by aditlal on 18/07/15.
 */
public class CommonPreferences {

    private SharedPreferences mSharedPreferences;
    private Context mContext;

    public CommonPreferences(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences("test", Context.MODE_PRIVATE);
    }

    private SharedPreferences getPref() {
        return mSharedPreferences;
    }

    public void setUserId(String b) {
        Logger.d("Preference manager ", "user id   " + b);
        mSharedPreferences.edit().putString("userId", b).commit();
    }

    public String getUserId() {
        return mSharedPreferences.getString("userId", null);
    }

}
