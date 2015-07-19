package com.blueninjas.aditlal.trackingapp.activity;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blueninjas.aditlal.trackingapp.MainApplication;
import com.blueninjas.aditlal.trackingapp.PubNubManager;
import com.blueninjas.aditlal.trackingapp.R;
import com.blueninjas.aditlal.trackingapp.utils.Logger;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.GpsLocationProvider;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.overlay.PathOverlay;
import com.mapbox.mapboxsdk.overlay.UserLocationOverlay;
import com.mapbox.mapboxsdk.views.MapView;
import com.parse.ParseUser;
import com.pubnub.api.Callback;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by aditlal on 19/07/15.
 */
public class ChannelDetailActivity extends AppCompatActivity {

    @InjectView(R.id.mapview)
    MapView mv;
    @InjectView(R.id.sendLocation)
    Button sendLocation;
    @InjectView(R.id.sendLocation2)
    Button sendLocation2;
    @InjectView(R.id.sendLocation3)
    Button sendLocation3;
    @InjectView(R.id.sendLocation4)
    Button sendLocation4;
    private GpsLocationProvider gpsLocationProvider;
    private UserLocationOverlay userLocationOverlay;
    Marker marker;
    Marker userTwo;
    Marker userThree;

    MainApplication application;
    private boolean isFirstMessage;

    private PathOverlay mLine;

    LatLng chan = new LatLng(12.9322204, 77.6282115);
    LatLng chan2 = new LatLng(12.924827, 77.6337055);
    LatLng chan3 = new LatLng(12.924827, 78.99);
    LatLng cha4 = new LatLng(12.924545, 77.631216);
    LatLng cha5 = new LatLng(12.930526, 77.633748);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        ButterKnife.inject(this);
        sendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something with the location
                PubNubManager.broadcastLocation(application.getPubNub(), "fhdgjf", chan.getLatitude(), chan.getLongitude(), ParseUser.getCurrentUser().getUsername());
            }
        });
        sendLocation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PubNubManager.broadcastLocation(application.getPubNub(), "fhdgjf", chan2.getLatitude(), chan2.getLongitude(), ParseUser.getCurrentUser().getUsername());
            }
        });

        sendLocation3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PubNubManager.broadcastLocation(application.getPubNub(), "fhdgjf", cha4.getLatitude(), cha4.getLongitude(), ParseUser.getCurrentUser().getUsername());
            }
        });
        sendLocation4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PubNubManager.broadcastLocation(application.getPubNub(), "fhdgjf", cha5.getLatitude(), cha5.getLongitude(), ParseUser.getCurrentUser().getUsername());
            }
        });
        application = MainApplication.getInstance();
        // Show user location (purposely not in follow mode)
        mv.setUserLocationEnabled(true);
        getMyLocation();
        application.getPubNub().hereNow("fhdgjf", new Callback() {
            @Override
            public void successCallback(String channel, final Object message) {
                super.successCallback(channel, message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(ChannelDetailActivity.this, "USers in list " + message.toString(), Toast.LENGTH_SHORT).show();
                        Logger.d("users in list", message.toString());
                    }
                });


            }
        });
        PubNubManager.subscribe(application.getPubNub(), "fhdgjf", new Callback() {
            @Override
            public void successCallback(String channel, final Object message) {
                super.successCallback(channel, message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(ChannelDetailActivity.this, "Location " + message.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                try {
                    Logger.d("ihdiugf", message.toString());
                    JSONObject iob = (JSONObject) message;
                    final double lat = iob.getDouble("lat");
                    final double lon = iob.getDouble("lon");
                    final String user = iob.getString("user");
                    //  updateCamera(new LatLng(lat, lon));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (user.equalsIgnoreCase("9611643171")) {
                                updateMarker2(new LatLng(lat, lon), user);
                                updateCamera(new LatLng(lat, lon));
                                updatePolyline(new LatLng(lat, lon));
                            } else if
                                    (user.equalsIgnoreCase("9480020022")) {
                                updateMarker3(new LatLng(lat, lon), user);
                                updateCamera(new LatLng(lat, lon));
                                updatePolyline(new LatLng(lat, lon));
                            } else {
                                updateMarker2(new LatLng(lat, lon), user);
                                updateCamera(new LatLng(lat, lon));
                                updatePolyline(new LatLng(lat, lon));
                                // updatePolyline(new LatLng(lat, lon));
                            }
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mv.getOverlays().add(new UserLocationOverlay(gpsLocationProvider, mv) {
            @Override
            public void onLocationChanged(final Location location, GpsLocationProvider source) {
                super.onLocationChanged(location, source);
                Logger.d("Changes location", location.getLatitude() + "");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChannelDetailActivity.this, "Location " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                    }
                });
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                marker.setPoint(latLng);
                // Do something with the location
                PubNubManager.broadcastLocation(application.getPubNub(), "fhdgjf", location.getLatitude(), location.getLongitude(), ParseUser.getCurrentUser().getUsername());


            }
        });

    }

    private void getMyLocation() {
        gpsLocationProvider = new GpsLocationProvider(ChannelDetailActivity.this);
        userLocationOverlay = new UserLocationOverlay(gpsLocationProvider, mv);
        userLocationOverlay.enableMyLocation();
        userLocationOverlay.setDrawAccuracyEnabled(true);
        mv.getOverlays().add(userLocationOverlay);
        mv.setCenter(userLocationOverlay.getMyLocation());
        mv.setUserLocationRequiredZoom(10);


        try {
            marker = new Marker(mv, ParseUser.getCurrentUser().getUsername(), "", new LatLng(userLocationOverlay.getLastFix().getLatitude(), userLocationOverlay.getLastFix().getLongitude()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //mv.addMarker(marker);

        PubNubManager.broadcastLocation(application.getPubNub(), "fhdgjf", userLocationOverlay.getLastFix().getLatitude(), userLocationOverlay.getLastFix().getLongitude(), ParseUser.getCurrentUser().getObjectId());

        userTwo = new Marker(mv, "User2", "", chan3);
        userThree = new Marker(mv, "User3", "", chan3);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mv.addMarker(userTwo);
                mv.addMarker(userThree);
            }
        });
        initializePolyline();
    }

    private void updateMarker2(LatLng l, String user) {
        Logger.d("JJ");
        if (!isFirstMessage) {
            mv.removeMarker(userTwo);
        }
        isFirstMessage = false;
        userTwo = new Marker(mv, user, "", l);
        userTwo.closeToolTip();
        mv.addMarker(userTwo);
    }

    private void updateMarker3(LatLng l, String user) {
        Logger.d("JJ");
        if (!isFirstMessage) {
            mv.removeMarker(userTwo);
        }
        isFirstMessage = false;
        userThree = new Marker(mv, user, "", l);
        userThree.closeToolTip();
        mv.addMarker(userThree);
    }

    private void updateCamera(LatLng l) {
        mv.setCameraDistance(10);
        mv.setCenter(l);
    }

    private void updatePolyline(LatLng latLng) {
        mv.removeOverlay(mLine);
        mLine.addPoint(latLng);
        mv.getOverlays().add(mLine);
    }

    private void initializePolyline() {
        mv.removeOverlay(mLine);
        mv.clear();
        mLine = new PathOverlay(Color.BLUE, 5);
    }
}
