package com.or2go.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.or2go.core.SalesSelectInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PromoPageAdapter extends RecyclerView.Adapter<PromoPageAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mInflater;
    //    private ArrayList<SalesSelectInfo> listTagInfo;
    HashMap<String, SalesSelectInfo> storeFavList;
    private int layout;
    private String vendorId;
    RecyclerViewClickListener mListener;
    private String OR2GO_SERVER;



    public interface RecyclerViewClickListener{
        void onItemClick(View view, int position);
    }

    //    public PromoPageAdapter(Context context, String server, String vendorId, ArrayList<SalesSelectInfo> taglist, int layout, RecyclerViewClickListener listener) {
//        this.mContext = context;
//        this.mInflater = LayoutInflater.from(context);
//        this.listTagInfo = taglist;
//        this.layout = layout;
//        this.OR2GO_SERVER = server;
//        this.vendorId = vendorId;
//        this.mListener = listener;
//    }
    public PromoPageAdapter(Context context, String or2goServer, HashMap<String, SalesSelectInfo> storefavlist, int layout, RecyclerViewClickListener listener) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.storeFavList = storefavlist;
        this.layout = layout;
        this.OR2GO_SERVER = or2goServer;
        this.mListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(layout, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Set<String> keys = storeFavList.keySet();
        String[] array = new String[keys.size()];
        array = keys.toArray(array);
        SalesSelectInfo taginfo = storeFavList.get(array[position]);
        vendorId = array[position];
//        SalesSelectInfo taginfo = storeFavList.get(storeFavList.keySet());
//        System.out.println(taginfo.getName());
//        if (storeFavList.get(storeFavList.keySet()).equals(taginfo)) {
//            vendorId = "OD00000030";
//        }
//        SalesSelectInfo taginfo = listTagInfo.get(position);
        holder.productimg.setScaleType(ImageView.ScaleType.FIT_XY);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.blankitem)
                .error(R.drawable.blankitem);

        if(taginfo.getProduct().getImagepath() == 0){
            System.out.println(OR2GO_SERVER+"prodimage/"+prodNameToImagePath(taginfo.getBrand(), taginfo.getName()) + ".jpg");
            Glide.with(mContext)
                    .load(OR2GO_SERVER+"prodimage/"+prodNameToImagePath(taginfo.getBrand(), taginfo.getName()) + ".jpg")
                    .apply(options)
                    //.override(200, 200) // resizing
                    //.fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(holder.productimg);
        }else if (taginfo.getProduct().getImagepath() == 1){
            System.out.println(OR2GO_SERVER+"vendorprodimage/"+vendorId+"/"+taginfo.getId()+ ".jpg");
            Glide.with(mContext)
                    .load(OR2GO_SERVER+"vendorprodimage/"+vendorId+"/"+taginfo.getId()+ ".jpg")
                    .apply(options)
                    //.override(200, 200) // resizing
                    //.fitCenter()
                    //.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(holder.productimg);
        }else
            Toast.makeText(mContext, "No Product Image", Toast.LENGTH_SHORT).show();
        holder.productname.setText(taginfo.getName());
        holder.productdesc.setText(taginfo.getDesc());
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
        int size = storeFavList.size();
        return size > 5 ? 5 : size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView productimg, productbgimg;
        public TextView productname, productdesc;

        public ViewHolder(@NonNull View itemView, RecyclerViewClickListener listner) {
            super(itemView);
            productimg = (ImageView) itemView.findViewById(R.id.popproitm);
            productbgimg = (ImageView) itemView.findViewById(R.id.popprobgimg);
            productname = (TextView) itemView.findViewById(R.id.popproname);
            productdesc = (TextView) itemView.findViewById(R.id.popprodesc);
            mListener = listner;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v, getAdapterPosition());
        }
    }
}
