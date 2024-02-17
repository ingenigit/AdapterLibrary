package com.or2go.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class MainPagerAdapter extends RecyclerView.Adapter<MainPagerAdapter.MainPagerViewHolder>{
    private Context mContext;
    private LayoutInflater mInflater;
//    private RequestQueue mRequestQueue;
//    ImageLoader mImageLoader;

    private String mImagePath;
    private String OR2GO_SERVER;
    private String OR2GO_SP_CODE;
    private int layout;

    public MainPagerAdapter(Context context, String imgpath, String server, String spcode, int layout) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
//        mRequestQueue = Volley.newRequestQueue(context);
//        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
//            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(5);
//            public void putBitmap(String url, Bitmap bitmap) {
//                mCache.put(url, bitmap);
//            }
//            public Bitmap getBitmap(String url) {
//                return mCache.get(url);
//            }
//        });
        this.mImagePath =imgpath;
        this.OR2GO_SERVER = server;
        this.OR2GO_SP_CODE = spcode;
        this.layout = layout;
    }

    @Override
    public MainPagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(layout, parent, false);
        return new MainPagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainPagerViewHolder holder, int position) {
        final Integer selpos = position+1;
        holder.pagerimg.setScaleType(ImageView.ScaleType.FIT_XY);
//        holder.pagerimg.setDefaultImageResId(R.drawable.blankitem); // image for loading...
        ////holder.pagerimg.setImageUrl(OR2GO_SERVER+"appimage/"+OR2GO_SP_CODE+"/headerslider/"+selpos.toString()+".png", mImageLoader);
//        holder.pagerimg.setImageUrl(BuildConfig.OR2GO_SERVER+"appimage/"+BuildConfig.OR2GO_SP_CODE+"/"+mImagePath+"/"+selpos.toString()+".png", mImageLoader);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.blankitem)
                .error(R.drawable.blankitem);
        Glide.with(mContext)
                .load(OR2GO_SERVER+"appimage/"+OR2GO_SP_CODE+"/"+mImagePath+"/"+selpos.toString()+".png")
                .apply(options)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.pagerimg);
    }

    @Override
    public int getItemCount() {
        return 5;//mData.size();
    }


    public class MainPagerViewHolder extends RecyclerView.ViewHolder {

        //private OrderHistoryAdapter.RecyclerViewClickListener mListener;

        //public TextView pagertitle;
//        public NetworkImageView pagerimg;
        public ImageView pagerimg;

        public MainPagerViewHolder(View view) {
            super(view);

            //pagertitle = (TextView) view.findViewById(R.id.textPager);
            pagerimg = (ImageView) view.findViewById(R.id.imgPager);

        }

    }

}
