package com.or2go.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.or2go.core.SalesSelectInfo;

import java.util.ArrayList;

public class StoreProductAdapter extends BaseAdapter {
    public Context context;
    private int layout;
    private String OR2GO_SERVER;
    public ArrayList<SalesSelectInfo> salesItems;

    public StoreProductAdapter(Context context, String server, ArrayList<SalesSelectInfo> salesItems, int layout) {
        this.context = context;
        this.OR2GO_SERVER = server;
        this.layout = layout;
        this.salesItems = salesItems;
    }

    @Override
    public int getCount() {
        return salesItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        if (vi == null)
            vi = LayoutInflater.from(context).inflate(layout, viewGroup, false);

        ImageView imageView = vi.findViewById(R.id.product_image);
        TextView textView = vi.findViewById(R.id.product_name);

        SalesSelectInfo selectInfo = salesItems.get(i);
        textView.setText(selectInfo.getName());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.blankitem)
                .error(R.drawable.blankitem);

        Glide.with(context)
                .load(OR2GO_SERVER+"prodimage/"+prodNameToImagePath(selectInfo.getBrand(), selectInfo.getName())+".jpg")
                .apply(options)
                //.override(200, 200) // resizing
                //.fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(imageView);
        return vi;
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
}
