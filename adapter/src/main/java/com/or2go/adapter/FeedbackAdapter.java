package com.or2go.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.or2go.core.OrderItem;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {

    private Context mContext;
    private int layout;
    private String OR2GO_SERVER;
    private String vendorId;
    ArrayList<OrderItem> orderItemArrayList;
    RateOnClickListener rateOnClickListener;

    public interface RateOnClickListener {
        void onRatingChanged(Integer id, RatingBar ratingBar, float rating, boolean fromUser);
    }

    public FeedbackAdapter(Context mContext,String server, String vendorId, ArrayList<OrderItem> orderItemArrayList, int layout, RateOnClickListener rateOnClickListener) {
        this.mContext = mContext;
        this.orderItemArrayList = orderItemArrayList;
        this.layout = layout;
        this.OR2GO_SERVER = server;
        this.vendorId = vendorId;
        this.rateOnClickListener = rateOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i = position;
        OrderItem item = orderItemArrayList.get(i);
        holder.textViewName.setText(item.getName());
        holder.textViewDesc.setText("");
        holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateOnClickListener.onRatingChanged(i, ratingBar, rating, fromUser);
            }
        });
        holder.editTextFeedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setFeebackText(s.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewDesc;
        EditText editTextFeedback;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.tv_product_name);
            textViewDesc = (TextView) itemView.findViewById(R.id.tv_product_desc);
            editTextFeedback = (EditText) itemView.findViewById(R.id.et_feedback);
            ratingBar = (RatingBar) itemView.findViewById(R.id.orderCompleteRatingBar);
        }
    }
}
