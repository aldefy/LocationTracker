package com.blueninjas.aditlal.trackingapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.blueninjas.aditlal.trackingapp.MainApplication;
import com.blueninjas.aditlal.trackingapp.PubNubManager;
import com.blueninjas.aditlal.trackingapp.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pubnub.api.Pubnub;

/**
 * Created by aditlal on 18/07/15.
 */
public class DetailsShareLocationActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {


    // =============================================================================================
    // Properties
    // =============================================================================================

    private static final String TAG = "Tracker - GMaps Share";
    private boolean mRequestingLocationUpdates = true;
    private MenuItem mShareButton;

    // Google API - Locations
    private GoogleApiClient mGoogleApiClient;

    // Google Maps
    private GoogleMap mGoogleMap;
    private PolylineOptions mPolylineOptions;
    private LatLng mLatLng;

    // PubNub
    private Pubnub mPubnub;
    private String channelName;
    private MainApplication application;
    private LocationManager locationManager;

    // =============================================================================================
    // Activity Life Cycle
    // =============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmaps_view);
        application = MainApplication.getInstance();
        // Get Channel Name
        Intent intent = getIntent();
        channelName = intent.getExtras().getString("channel");
        Log.d(TAG, "Passed Channel Name: " + channelName);


        // Set up View: Map & Action Bar
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


        // Start PubNub
        mPubnub = application.getPubNub();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        mShareButton = menu.findItem(R.id.share_locations);
        return true;
    }


    // =============================================================================================
    // Map CallBacks
    // =============================================================================================

    @Override
    public void onMapReady(GoogleMap map) {
        mGoogleMap = map;
        mGoogleMap.setMyLocationEnabled(true);
        Log.d(TAG, "Map Ready");
    }

    // =============================================================================================
    // Google Location API CallBacks
    // =============================================================================================


    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location Detected");
        mLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        // Broadcast information on PubNub Channel
        PubNubManager.broadcastLocation(mPubnub, channelName, location.getLatitude(),
                location.getLongitude(), location.getAltitude());

        // Update Map
        updateCamera();
        updatePolyline();
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


    // =============================================================================================
    // Button CallBacks
    // =============================================================================================

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.share_locations:
                Log.d(TAG, "'Share Your Location' Button Pressed");
                mRequestingLocationUpdates = !mRequestingLocationUpdates;
                if (mRequestingLocationUpdates) {
                    mShareButton.setTitle("Stop Sharing Your Location");
                }
                if (!mRequestingLocationUpdates) {
                    mShareButton.setTitle("Start Sharing Your Location");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // =============================================================================================
    // Map Editing Methods
    // =============================================================================================

    private void initializePolyline() {
        mGoogleMap.clear();
        mPolylineOptions = new PolylineOptions();
        mPolylineOptions.color(Color.BLUE).width(10);
        mGoogleMap.addPolyline(mPolylineOptions);
    }

    private void updatePolyline() {
        mPolylineOptions.add(mLatLng);
        mGoogleMap.clear();
        mGoogleMap.addPolyline(mPolylineOptions);
    }

    private void updateCamera() {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, 16));
    }
}
