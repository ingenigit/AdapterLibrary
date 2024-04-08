package com.or2go.adapter;

import static com.or2go.core.Or2goConstValues.DISCOUNT_AMOUNT_TYPE_VALUE;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.or2go.core.DiscountInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;

public class CouponSelectorAdapter extends RecyclerView.Adapter<CouponSelectorAdapter.CSViewHolder>{
    private ArrayList<DiscountInfo> mDataset;
    RecyclerViewItemClickListener recyclerViewItemClickListener;
    private int layout;
    Currency currency = Currency.getInstance("INR");
    DecimalFormat df;

    public CouponSelectorAdapter(ArrayList<DiscountInfo> myDataset, int layout, RecyclerViewItemClickListener listener) {
        mDataset = myDataset;
        this.recyclerViewItemClickListener = listener;
        this.layout = layout;
        df = new DecimalFormat("0");
    }

    @NonNull
    @Override
    public CSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        CSViewHolder vh = new CSViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CSViewHolder csViewHolder, int i) {

        DiscountInfo discInfo = mDataset.get(i);
        csViewHolder.mName.setText(discInfo.mdName);
        csViewHolder.mDesc.setText(discInfo.mdDescription);

        Float val = discInfo.mdAmount;
        if (discInfo.mdAmntType == DISCOUNT_AMOUNT_TYPE_VALUE)
        {
            csViewHolder.mInfo.setText(currency.getSymbol()+val.toString() + " Off");
        }
        else
        {
            csViewHolder.mInfo.setText(val.toString()+"%" + " Off");
        }

        String discCond="";
        if ((discInfo.minordamnt != null) && (discInfo.minordamnt > 0))
            discCond = "Minimum Amount : "+ currency.getSymbol()+df.format(discInfo.minordamnt);

        if ((discInfo.maxdiscamnt != null) && (discInfo.maxdiscamnt > 0))
            discCond += "Maximum Discount : "+ currency.getSymbol()+df.format(discInfo.maxdiscamnt);

        csViewHolder.mCond.setText(discCond);


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public  class CSViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mName;
        public TextView mDesc;
        public TextView mInfo;
        public TextView mCond;

        public CSViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.tvDiscountName);
            mDesc = (TextView) v.findViewById(R.id.tvDiscountDesc);
            mInfo = (TextView) v.findViewById(R.id.tvDiscountInfo);
            mCond = (TextView) v.findViewById(R.id.tvDiscountCond);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewItemClickListener.onCouponSelection(mDataset.get(this.getAdapterPosition()));
        }
    }

    public interface RecyclerViewItemClickListener {
        void onCouponSelection(DiscountInfo data);
    }
}
