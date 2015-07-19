package com.blueninjas.aditlal.trackingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blueninjas.aditlal.trackingapp.MainApplication;
import com.blueninjas.aditlal.trackingapp.PubNubManager;
import com.blueninjas.aditlal.trackingapp.R;
import com.blueninjas.aditlal.trackingapp.activity.ChannelDetailActivity;
import com.blueninjas.aditlal.trackingapp.utils.CommonPreferences;
import com.blueninjas.aditlal.trackingapp.utils.Logger;
import com.blueninjas.aditlal.trackingapp.views.MyRecyclerAdapter;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by aditlal on 18/07/15.
 */
public class HomeFragment extends Fragment {


    @InjectView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;

    @InjectView(R.id.fab)
     FloatingActionButton fab;


    MainApplication application;
    Pubnub pubnub;
    CommonPreferences commonPreferences;

    String channelGroup = "family";
    String channel = "map_share";

    List<JSONObject> channels = new ArrayList<JSONObject>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, v);
        application = MainApplication.getInstance();
        commonPreferences = new CommonPreferences(getActivity());

        myRecyclerView.setHasFixedSize(false);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(llm);
        myRecyclerView.setAdapter(new MyRecyclerAdapter(channels, R.layout.channel_rv_item));


        pubnub = PubNubManager.startPubnub();
        application.setPubNub(pubnub);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("create Channel", channel + " " + "gfhdj");
                pubnub.publish(channel, "Welcome", new Callback() {
                    @Override
                    public void successCallback(String channel, Object message) {
                        super.successCallback(channel, message);

                        Logger.d("create Channel", channel + " " + message.toString());
                        startActivity(new Intent(getActivity(), ChannelDetailActivity.class).putExtra("channel", channel));

                    }

                    @Override
                    public void successCallback(String channel, Object message, String timetoken) {
                        super.successCallback(channel, message, timetoken);
                        Logger.d("create Channel", channel + " " + message + " " + timetoken);
                    }

                    @Override
                    public void errorCallback(String channel, PubnubError error) {
                        super.errorCallback(channel, error);
                        Logger.d("create Channel", channel + " " + error.getErrorString());
                    }
                });

              /*  *//* Publish a simple message to the demo_tutorial channel *//*
                JSONObject data = new JSONObject();

                try {
                    data.put("userId", commonPreferences.getUserId());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pubnub.publish("demo_tutorial", data, new Callback() {
                    @Override
                    public void successCallback(String channel, Object message) {
                        super.successCallback(channel, message);
                        Logger.d("Callback ", message + "");
                        //Toast.makeText(getActivity(), "CallBack msg " + message.toString(), Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });
        /* Subscribe to the demo_tutorial channel */
        try {
            Logger.d("Callback", "pubnub here");
            /*pubnub.subscribe("demo_tutorial", new Callback() {
                public void successCallback(String channel, Object message) {
                    Logger.d("CallBack", channel + " you got - " + message);
                    // Toast.makeText(getActivity(), "CallBack msg " + message.toString(), Toast.LENGTH_SHORT).show();
                    System.out.println(message);
                }

                public void errorCallback(String channel, PubnubError error) {
                    Logger.d("Callback", channel + " you got - " + error);
                    //Toast.makeText(getActivity(), "CallBack error " + error.toString(), Toast.LENGTH_SHORT).show();
                    System.out.println(error.getErrorString());
                }
            });


        } catch (PubnubException e) {
            e.printStackTrace();
        }

*/
           /* pubnub.whereNow(ParseUser.getCurrentUser().getObjectId(), new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    super.successCallback(channel, message);
                    Logger.d("CHanngels", message.toString());

                }
            });*/

            ParseUser user = ParseUser.getCurrentUser();
            ParseObject channelObject = new ParseObject("ch");

            return v;

        } catch (Exception e) {

        }

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
