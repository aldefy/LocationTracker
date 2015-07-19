package com.blueninjas.aditlal.trackingapp.activity;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blueninjas.aditlal.trackingapp.MainApplication;
import com.blueninjas.aditlal.trackingapp.R;
import com.blueninjas.aditlal.trackingapp.utils.CommonPreferences;
import com.blueninjas.aditlal.trackingapp.utils.Logger;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseUser;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aditlal on 18/07/15.
 */
public class ChannelDetailActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    Bundle b;

    MainApplication application;
    Pubnub pubnub;
    CommonPreferences commonPreferences;

    MarkerOptions him, me;
    Marker himMarker, meMarker;

    GoogleMap map;

    Button clck;

    static final LatLng HAMBURG = new LatLng(13.099407, 77.576887);
    static final LatLng KIEL = new LatLng(13.098979, 77.575050);

    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = MainApplication.getInstance();
        pubnub = application.getPubNub();
        commonPreferences = new CommonPreferences(ChannelDetailActivity.this);

        setContentView(R.layout.activity_channel);
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        }
        clck = (Button) findViewById(R.id.clck);
        clck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject data = new JSONObject();

                try {
                    data.put("lat", KIEL.latitude);
                    data.put("lon", KIEL.longitude);
                    data.put("user", ParseUser.getCurrentUser().getObjectId());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pubnub.publish("map_share", data, new Callback() {
                    @Override
                    public void successCallback(String channel, Object message) {
                        super.successCallback(channel, message);
                        Logger.d("Callback ", message + "");
                        //Toast.makeText(getActivity(), "CallBack msg " + message.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();
        map.setMyLocationEnabled(true);
        if (location != null) {
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);


            map.addMarker(new MarkerOptions().position(latLng).title("Me"));
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera(CameraUpdateFactory.zoomTo(15));

        }

        try {
            pubnub.subscribe("map_share", new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    super.successCallback(channel, message);
                    Logger.d("alfksfh", message + "");
                    final JSONObject j = (JSONObject) message;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LatLng l = new LatLng(j.optDouble("lat"), j.optDouble("lon"));
                        }
                    });



                  /*  JSONObject obj = (JSONObject) message;
                    try {
                        Location loc = (Location) obj.get("loc");
                        double latitude = loc.getLatitude();
                        double longitude = loc.getLongitude();
                        final LatLng latLng = new LatLng(latitude, longitude);
                        himMarker.setPosition(latLng);
                        // map.addMarker(new MarkerOptions().position(latLng));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                }
            });
        } catch (PubnubException e) {
            e.printStackTrace();
        }

    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        this.map = map;
        map.setMyLocationEnabled(true);
        him = new MarkerOptions().position(HAMBURG);


        himMarker = map.addMarker(him);
     /*   Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
                .title("Hamburg"));*/
        //    map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

     /*   Location location = map.getMyLocation();


        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        final LatLng latLng = new LatLng(latitude, longitude);*/


        // map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));

    }


    @Override
    public void onLocationChanged(Location location) {


      /*  double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        final LatLng latLng = new LatLng(latitude, longitude);
        Logger.d("lat long: " + location.getLatitude() + " :  " + location.getLongitude());
       *//* Marker kiel = map.addMarker(new MarkerOptions()
                .position(latLng)
                .title("Kiel")
                .snippet("Kiel is cool")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.icon_fab)));*//*
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));*/
        JSONObject data = new JSONObject();

        try {
            data.put("loc", location);
            data.put("userId", ParseUser.getCurrentUser().getObjectId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        pubnub.publish("map_share", data, new Callback() {
            @Override
            public void successCallback(String channel, Object message) {
                super.successCallback(channel, message);
                Logger.d("Callback ", message + "");
                //Toast.makeText(getActivity(), "CallBack msg " + message.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
