package com.or2go.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.or2go.core.PaymentMethodInfo;

import java.util.ArrayList;

public class PaymentSelectorAdapter extends RecyclerView.Adapter<PaymentSelectorAdapter.PSViewHolder>{

    private ArrayList<PaymentMethodInfo> mDataset;
    int layout;
    RecyclerViewItemClickListener recyclerViewItemClickListener;

    public PaymentSelectorAdapter(ArrayList<PaymentMethodInfo> myDataset, int layout, RecyclerViewItemClickListener listener) {
        mDataset = myDataset;
        this.layout = layout;
        this.recyclerViewItemClickListener = listener;
    }

    @NonNull
    @Override
    public PSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new PSViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull PSViewHolder asViewHolder, int i) {
        PaymentMethodInfo payInfo = mDataset.get(i);
        asViewHolder.mMethod.setText(payInfo.sMethod);
        asViewHolder.mDesc.setText(payInfo.sDescription);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }



    public  class PSViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mMethod;
        public TextView mDesc;
        //public TextView mPlace;

        public PSViewHolder(View v) {
            super(v);
            mMethod = (TextView) v.findViewById(R.id.selPayMethod);
            mDesc = (TextView) v.findViewById(R.id.selPayDesc);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewItemClickListener.clickOnItem(mDataset.get(this.getAdapterPosition()));
        }
    }

    public interface RecyclerViewItemClickListener {
        void clickOnItem(PaymentMethodInfo data);
    }
}
