package com.blueninjas.aditlal.trackingapp.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.blueninjas.aditlal.trackingapp.MainApplication;
import com.blueninjas.aditlal.trackingapp.PubNubManager;
import com.blueninjas.aditlal.trackingapp.R;
import com.blueninjas.aditlal.trackingapp.utils.Logger;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.GpsLocationProvider;
import com.mapbox.mapboxsdk.overlay.Marker;
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
    private GpsLocationProvider gpsLocationProvider;
    private UserLocationOverlay userLocationOverlay;
    Marker marker;
    Marker userTwo;

    MainApplication application;
    private boolean isFirstMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        ButterKnife.inject(this);
        application = MainApplication.getInstance();
        // Show user location (purposely not in follow mode)
        mv.setUserLocationEnabled(true);
        getMyLocation();

        PubNubManager.subscribe(application.getPubNub(), "fhdgjf", new Callback() {
            @Override
            public void successCallback(String channel, final Object message) {
                super.successCallback(channel, message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ChannelDetailActivity.this, "Location " + message.toString()git, Toast.LENGTH_SHORT).show();
                    }
                });
                try {
                    Logger.d("ihdiugf", message.toString());
                    JSONObject iob = (JSONObject) message;
                    double lat = iob.getDouble("lat");
                    double lon = iob.getDouble("lon");
                    String user = iob.getString("user");
                    updateCamera(new LatLng(lat, lon));
                    updateMarker(new LatLng(lat, lon));

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
                        Toast.makeText(ChannelDetailActivity.this, "Location "+ location.getLongitude(), Toast.LENGTH_SHORT).show();
                    }
                });
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                marker.setPoint(latLng);
                // Do something with the location
                PubNubManager.broadcastLocation(application.getPubNub(), "fhdgjf", location.getLatitude(), location.getLongitude(), ParseUser.getCurrentUser().getObjectId());


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


        marker = new Marker(mv, "", "", new LatLng(userLocationOverlay.getLastFix().getLatitude(), userLocationOverlay.getLastFix().getLongitude()));
        //mv.addMarker(marker);

        PubNubManager.broadcastLocation(application.getPubNub(), "fhdgjf", userLocationOverlay.getLastFix().getLatitude(), userLocationOverlay.getLastFix().getLongitude(), ParseUser.getCurrentUser().getObjectId());

        userTwo = new Marker(mv, "", "", new LatLng(12.9, 76.1));
        mv.addMarker(userTwo);
    }

    private void updateMarker(LatLng l) {
        Logger.d("JJ");
        if (!isFirstMessage) {
            mv.removeMarker(userTwo);
        }
        isFirstMessage = false;
        userTwo = new Marker(mv, "title", "", l);
        mv.addMarker(userTwo);
    }

    private void updateCamera(LatLng l) {
        mv.setCameraDistance(10);
        mv.setCenter(l);
    }


}
