package com.or2go.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


public class MartCategoryViewAdapter extends RecyclerView.Adapter<MartCategoryViewAdapter.MartCatViewHolder> {

    Context mContext;
    List<String> mCategoryList;
    //int logos[];
    LayoutInflater inflter;
    RecyclerViewClickListener mListener;
    int layout;
    String OR2GO_SERVER;
//    private RequestQueue mRequestQueue;
//    ImageLoader mImageLoader;
//    int cacheSize = 1 * 1024 * 1024; // 4MiB


    public class MartCatViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecyclerViewClickListener mMartViewListener;
        TextView itemname;
//        NetworkImageView catimg;
        ImageView catimg;

        public MartCatViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            itemname = (TextView) view.findViewById(R.id.martcatlabel);
//            catimg = (NetworkImageView) view.findViewById(R.id.martcatimg);
            catimg = (ImageView) view.findViewById(R.id.martcatimg);
            mMartViewListener = listener;
            view.setOnClickListener(this);
            //itemdel.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mMartViewListener.onClick(view, getAdapterPosition());
        }
    }

    public MartCategoryViewAdapter(Context context, String server, List<String> catlist, int layout, RecyclerViewClickListener listener)
    {
        this.mContext = context;
        this.OR2GO_SERVER = server;
        this.mCategoryList = catlist;
        this.layout = layout;
        this.mListener = listener;

//        mRequestQueue = Volley.newRequestQueue(context);
//        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
//            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(cacheSize);
//            public void putBitmap(String url, Bitmap bitmap) {
//                mCache.put(url, bitmap);
//            }
//            public Bitmap getBitmap(String url) {
//                return mCache.get(url);
//            }
//        });
    }

    @Override
    public MartCatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(layout, parent, false);
        return new MartCatViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(final MartCatViewHolder holder, int position) {
        final int selpos = position;
        //OrderItem item = mItemList.get(position);
        String catname = mCategoryList.get(position);
        holder.itemname.setText(catname);

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.blankitem)
                .error(R.drawable.blankitem);

        Glide.with(mContext)
                .load(OR2GO_SERVER + "categoryimage/" + categoryNameToImagePath(catname) + ".png")
                .apply(options)
                //.override(200, 200) // resizing
                //.fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.catimg);

//        holder.catimg.setDefaultImageResId(R.drawable.blankitem); // image for loading...
//        holder.catimg.setImageUrl(imgurl, mImageLoader); //ImgController

    }

    @Override
    public int getItemCount() {

        return mCategoryList.size();
    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);

    }

    private String categoryNameToImagePath(String name)
    {
        String bname = name.replace(" ", "");
        String fname = bname.replace("&", "_");
        String rname = fname.replace(",", "_");
        return rname;
    }
}
