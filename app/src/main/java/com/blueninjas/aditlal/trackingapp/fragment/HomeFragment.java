package com.blueninjas.aditlal.trackingapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blueninjas.aditlal.trackingapp.MainApplication;
import com.blueninjas.aditlal.trackingapp.R;
import com.blueninjas.aditlal.trackingapp.utils.Logger;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, v);
        application = MainApplication.getInstance();
        myRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(llm);
        pubnub = application.getPubNub();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Publish a simple message to the demo_tutorial channel */
                JSONObject data = new JSONObject();

                try {
                    data.put("color", "blue");

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
                });
            }
        });
        /* Subscribe to the demo_tutorial channel */
        try {
            Logger.d("Callback", "pubnub here");
            pubnub.subscribe("demo_tutorial", new Callback() {
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

        return v;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
