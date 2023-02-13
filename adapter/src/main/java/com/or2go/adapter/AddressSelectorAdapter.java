package com.or2go.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.or2go.core.DeliveryAddrInfo;

import java.util.ArrayList;

public class AddressSelectorAdapter extends RecyclerView.Adapter<AddressSelectorAdapter.ASViewHolder>{
    private ArrayList<DeliveryAddrInfo> mDataset;
    RecyclerViewItemClickListener recyclerViewItemClickListener;
    int layout;

    public AddressSelectorAdapter(ArrayList<DeliveryAddrInfo> myDataset, int layout, RecyclerViewItemClickListener listener) {
        mDataset = myDataset;
        this.layout = layout;
        this.recyclerViewItemClickListener = listener;
    }

    @NonNull
    @Override
    public ASViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ASViewHolder vh = new ASViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ASViewHolder asViewHolder, int i) {
        DeliveryAddrInfo addrInfo = mDataset.get(i);
        asViewHolder.mAddr.setText(addrInfo.getAddress());
        asViewHolder.mName.setText(addrInfo.getAddrName());
        asViewHolder.mPlace.setText(addrInfo.getPlace());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public  class ASViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mName;
        public TextView mAddr;
        public TextView mPlace;

        public ASViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.selname);
            mAddr = (TextView) v.findViewById(R.id.seladdr);
            mPlace = (TextView) v.findViewById(R.id.selplace);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewItemClickListener.clickOnItem(mDataset.get(this.getAdapterPosition()));
        }
    }

    public interface RecyclerViewItemClickListener {
        void clickOnItem(DeliveryAddrInfo data);
    }
}
