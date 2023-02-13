package com.or2go.adapter;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.distance.calculation.finddistance.TwoLocation;
import com.google.distance.calculation.finddistance.getDistance;
import com.or2go.core.Or2GoStore;

import java.util.ArrayList;

public class AllStoreListAdapter extends RecyclerView.Adapter<AllStoreListAdapter.ViewHolder> {
    ArrayList<Or2GoStore> storelist;
    Context context;
    int layout;
    getDistance getDistance;
    Location cLocation;

    public AllStoreListAdapter(Location currentlocation, ArrayList<Or2GoStore> storelist, Context context, int layout) {
        this.storelist = storelist;
        this.context = context;
        this.layout = layout;
        this.cLocation = currentlocation;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Or2GoStore store = storelist.get(position);
        holder.textViewname.setText(store.vName);
        String currentLocation = cLocation.getLatitude()+","+ cLocation.getLongitude();
        getDistance = new getDistance() {
            @Override
            public void GetTotalDistance(String distance, String time) {
                holder.textViewdistance.setText(distance);
            }
        };
        TwoLocation twoLocation = new TwoLocation(context, store.geolocation, currentLocation, "", "", "false", "AIzaSyAnhTf79xLDcS0zj_cl_rjAVbx-cIBfwa8", "https://maps.googleapis.com/maps/api/distancematrix/json", "driving", getDistance);
        twoLocation.run();
    }

    @Override
    public int getItemCount() {
        return storelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewname, textViewdistance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewname = itemView.findViewById(R.id.tvLocationName);
            textViewdistance = itemView.findViewById(R.id.tvLocationAddress);
        }
    }
}
