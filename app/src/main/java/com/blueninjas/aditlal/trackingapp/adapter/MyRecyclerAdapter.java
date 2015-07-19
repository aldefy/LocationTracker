package com.blueninjas.aditlal.trackingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blueninjas.aditlal.trackingapp.R;
import com.blueninjas.aditlal.trackingapp.utils.Logger;
import com.parse.ParseObject;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Apple on 19/07/15.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {


    private List<ParseObject> items;
    private int itemLayout;

    public MyRecyclerAdapter(List<ParseObject> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_rv_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ParseObject item = items.get(position);
        try {
            holder.cardRichContentTitle.setText(item.getString("ChannelName"));
            holder.cardDescription.setText(item.getString("ChannelDescription"));
        } catch (Exception e) {
            Logger.d("Exception in MyRecyclerAdapter");
        }
        //Picasso.with(holder.image.getContext()).cancelRequest(holder.image);
        //Picasso.with(holder.image.getContext()).load(item.getImage()).into(holder.image);
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cardDescription;
        public TextView cardRichContentTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            cardDescription = (TextView) itemView.findViewById(R.id.cardDescription);
            cardRichContentTitle = (TextView) itemView.findViewById(R.id.cardRichContentTitle);
        }
    }

    public void add(ParseObject item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(JSONObject item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }
}