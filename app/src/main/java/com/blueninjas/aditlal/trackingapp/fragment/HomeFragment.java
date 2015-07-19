package com.blueninjas.aditlal.trackingapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blueninjas.aditlal.trackingapp.MainApplication;
import com.blueninjas.aditlal.trackingapp.PubNubManager;
import com.blueninjas.aditlal.trackingapp.R;
import com.blueninjas.aditlal.trackingapp.activity.ChannelDetailActivity;
import com.blueninjas.aditlal.trackingapp.adapter.MyRecyclerAdapter;
import com.blueninjas.aditlal.trackingapp.utils.CommonPreferences;
import com.blueninjas.aditlal.trackingapp.utils.Logger;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by aditlal on 18/07/15.
 */
public class HomeFragment extends Fragment {


    /* @InjectView(R.id.my_recycler_view)
     RecyclerView myRecyclerView;*/

    MainApplication application;
    Pubnub pubnub;
    CommonPreferences commonPreferences;

    String channel = "fhdgjf";
    @InjectView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;

    @InjectView(R.id.fab)
    FloatingActionButton fab;


    AppCompatDialog fabDialog;
    @InjectView(R.id.cardImageView)
    ImageView cardImageView;
    @InjectView(R.id.cardRichContentTitle)
    TextView cardRichContentTitle;
    @InjectView(R.id.cardRichContentLayout)
    RelativeLayout cardRichContentLayout;
    @InjectView(R.id.cardDescription)
    TextView cardDescription;
    @InjectView(R.id.card_view)
    CardView cardView;
    @InjectView(R.id.main_content)
    CoordinatorLayout mainContent;
    private List<ParseObject> channels;

    ProgressDialog d;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, v);
        application = MainApplication.getInstance();
        channels = new ArrayList<>();
        commonPreferences = new CommonPreferences(getActivity());
        d = new ProgressDialog(getActivity());
        d.setMessage("Creating channel");
        d.setCanceledOnTouchOutside(false);
        fabDialog = new AppCompatDialog(getActivity());
        fabDialog.setTitle("Create a channel");
        fabDialog.setContentView(R.layout.dialog_new_channel);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChannelDetailActivity.class).putExtra("channel", channel));
            }
        });
        ((Button) fabDialog.findViewById(R.id.create)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.show();
                channel = ((EditText) fabDialog.findViewById(R.id.textInput)).getText().toString();
                fabDialog.cancel();
                pubnub.publish(channel, "Welcome", new Callback() {
                    @Override
                    public void successCallback(String channel, Object message) {
                        super.successCallback(channel, message);
                        ParseUser user = ParseUser.getCurrentUser();
                        ParseObject channelObject = new ParseObject("Channels");
                        channelObject.put("user", user);
                        channelObject.put("ChannelName", channel);
                        channelObject.saveInBackground();
                        Logger.d("create Channel", channel + " " + message.toString());
                        d.cancel();
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
                        d.cancel();
                        Logger.d("create Channel", channel + " " + error.getErrorString());
                    }
                });
            }
        });
        ((Button) fabDialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabDialog.cancel();
            }
        });


        // myRecyclerView.setHasFixedSize(true);
        //LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        //myRecyclerView.setLayoutManager(llm);

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
                //  startActivity(new Intent(getActivity(), ChannelDetailActivity.class).putExtra("channel", channel));
                fabDialog.show();

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

            ParseQuery<ParseObject> querySubscribedChannels = new ParseQuery<ParseObject>("Channels");
            querySubscribedChannels.addDescendingOrder("updatedAt");
            querySubscribedChannels.whereEqualTo("user", ParseUser.getCurrentUser());
            querySubscribedChannels.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if (e == null)
                        Logger.d("List returned is ", list.toString());
                }
            });
           /* pubnub.whereNow(ParseUser.getCurrentUser().getObjectId(), new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    super.successCallback(channel, message);
                    Logger.d("CHanngels", message.toString());

                }
            });*/


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
