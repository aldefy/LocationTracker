package com.blueninjas.aditlal.trackingapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blueninjas.aditlal.trackingapp.MainApplication;
import com.blueninjas.aditlal.trackingapp.R;
import com.blueninjas.aditlal.trackingapp.utils.CommonPreferences;
import com.blueninjas.aditlal.trackingapp.utils.Logger;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by aditlal on 18/07/15.
 */
public class OnBoardingActivity extends AppCompatActivity {
    @InjectView(R.id.login_button)
    Button loginButton;
    @InjectView(R.id.textMobileField)
    EditText textMobileField;
    @InjectView(R.id.til)
    TextInputLayout til;
    @InjectView(R.id.forgot)
    TextView forgot;
    @InjectView(R.id.phone_login_form)
    LinearLayout phoneLoginForm;
    /*  @InjectView(R.id.login_form)
      ScrollView loginForm;*/
    /* private LoginButton loginButton;

         CallbackManager callbackManager;*/
    private String TAG = "OnBoardingActivity";

    ProgressDialog d;

    MainApplication application;
    CommonPreferences commonPreferences;

    List<String> permissions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.inject(this);
        application = MainApplication.getInstance();
        d = new ProgressDialog(OnBoardingActivity.this);
        d.setMessage("Logging in");
        d.setCanceledOnTouchOutside(false);

        textMobileField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    d.show();
                    //do something
                    ParseUser user = new ParseUser();
                    user.logInInBackground(textMobileField.getText().toString(), "password123", new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            d.cancel();
                            Logger.d("USer", textMobileField.getText().toString());
                            application.getPubNub().setUUID(textMobileField.getText().toString());
                            startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));
                            finish();
                        }
                    });

                    return true;
                }

                return false;
            }
        });

        commonPreferences = new CommonPreferences(OnBoardingActivity.this);
        permissions.add("user_friends");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.show();
                ParseUser user = new ParseUser();
                user.logInInBackground(textMobileField.getText().toString(), "password123", new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        d.cancel();
                        Logger.d("USer", textMobileField.getText().toString());
                        application.getPubNub().setUUID(textMobileField.getText().toString());
                        startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));
                        finish();
                    }
                });


            }
        });




      /*  callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                final AccessToken accessToken = loginResult.getAccessToken();

                GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                        Log.d(TAG, user.optString("email"));
                        Log.d(TAG, user.optString("name"));
                        Log.d(TAG, user.optString("id"));
                        application.setUserId(user.optString("id") + "" + user.optString("email"));
                        commonPreferences.setUserId(user.optString("id") + "" + user.optString("email"));

                        startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));
                        finish();

                    }
                }).executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });*/
    }

    /* @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         callbackManager.onActivityResult(requestCode, resultCode, data);
     }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
