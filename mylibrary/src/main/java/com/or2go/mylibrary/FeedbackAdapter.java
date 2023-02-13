package com.or2go.mylibrary;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.or2go.core.OrderItem;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder>{

    private Context mContext;
    private int layout;
    ArrayList<OrderItem> orderItemArrayList;
    RateOnClickListener rateOnClickListener;

    public interface RateOnClickListener{
        void onRatingChanged(Integer id, RatingBar ratingBar, float rating, boolean fromUser);
    }

    public FeedbackAdapter(Context mContext, ArrayList<OrderItem> orderItemArrayList, int layout, RateOnClickListener rateOnClickListener) {
        this.mContext = mContext;
        this.orderItemArrayList = orderItemArrayList;
        this.layout = layout;
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
        OrderItem item = orderItemArrayList.get(position);
        holder.textViewName.setText(item.getName());
        holder.textViewDesc.setText("");
        holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateOnClickListener.onRatingChanged(position, ratingBar, rating, fromUser);
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textViewName, textViewDesc;
        EditText editTextFeedback;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_product_image);
            textViewName = (TextView) itemView.findViewById(R.id.tv_product_name);
            textViewDesc = (TextView) itemView.findViewById(R.id.tv_product_desc);
            editTextFeedback = (EditText) itemView.findViewById(R.id.et_feedback);
            ratingBar = (RatingBar) itemView.findViewById(R.id.orderCompleteRatingBar);
        }
    }
}
