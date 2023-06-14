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
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.blankitem)
                .error(R.drawable.blankitem);
        if(item.getImagePath() == 0){
            System.out.println(OR2GO_SERVER+"prodimage/"+prodNameToImagePath(item.getBrandName(), item.getName()) + ".jpg");
            Glide.with(mContext)
                    .load(OR2GO_SERVER+"prodimage/"+prodNameToImagePath(item.getBrandName(), item.getName()) + ".jpg")
                    .apply(options)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(holder.imageView);
        }else if (item.getImagePath() == 1){
            System.out.println(OR2GO_SERVER+"vendorprodimage/"+vendorId+"/"+item.getId()+ ".jpg");
            Glide.with(mContext)
                    .load(OR2GO_SERVER+"vendorprodimage/"+vendorId+"/"+item.getId()+ ".jpg")
                    .apply(options)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(holder.imageView);
        }else
            Toast.makeText(mContext, "No Product Image", Toast.LENGTH_SHORT).show();
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

    private String prodNameToImagePath(String brand, String name) {
        String lname = name.toLowerCase();
        String sname;

        if (brand != null && (!brand.isEmpty()) && (!brand.equals("null"))) {
            String lbrand = brand.toLowerCase();
            if (lname.contains(lbrand))
                sname=lname;
            else
                sname = lbrand + "_" + lname;
        }
        else
            sname = lname;

        String bname = sname.replace(" ", "_");
        String fname = bname.replace("&", "_");
        String rname = fname.replace(",", "_");

        return (rname);
    }

    @Override
    public int getItemCount() {
        return orderItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
