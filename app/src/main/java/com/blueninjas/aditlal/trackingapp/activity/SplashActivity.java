package com.blueninjas.aditlal.trackingapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.blueninjas.aditlal.trackingapp.MainApplication;
import com.parse.ParseUser;

/**
 * Created by aditlal on 18/07/15.
 */
public class SplashActivity extends Activity {

    MainApplication application;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = MainApplication.getInstance();
        ParseUser user = ParseUser.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(SplashActivity.this, OnBoardingActivity.class));
            finish();
        }

    }
}
