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
import com.or2go.core.Or2goVendorInfo;

import java.util.ArrayList;
import java.util.Currency;

//import static genipos.ciproninn.Or2goConstValues.OR2GO_SERVER;

public class VendorSelectorAdapter extends RecyclerView.Adapter<VendorSelectorAdapter.VSViewHolder>{
    private Context mContext;
    //private List<Album> albumList;
    ArrayList<Or2goVendorInfo> mVendorList;
    RecyclerViewClickListener mListener;
//    private RequestQueue mRequestQueue;
//    ImageLoader mImageLoader;
//    int cacheSize = 1 * 1024 * 1024; // 4MiB
    Currency currency = Currency.getInstance("INR");
    int UI_VERSION,layout;
    String OR2GO_SERVER;

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public class VSViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RecyclerViewClickListener mListener;
        public TextView title, tag, loc;
        //public ImageView logo;
        public ImageView logo;
        public ImageView opensts;
        //public RatingBar rating;
        public TextView ratingval, minord, discsts;

        public VSViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvvendname);
            tag = (TextView) view.findViewById(R.id.tvvendtag);
            loc = (TextView) view.findViewById(R.id.tvvendloc);
            //rating= (RatingBar) view.findViewById(R.id.vendrating);
            logo = (ImageView ) view.findViewById(R.id.imgvendlogo);
            opensts = (ImageView)  view.findViewById(R.id.imgopensts);
            ratingval = (TextView) view.findViewById(R.id.tvratingval);
            //storests = (TextView) view.findViewById(R.id.tvopensts);
            discsts =  (TextView) view.findViewById(R.id.tvdiscsts);
            minord = (TextView) view.findViewById(R.id.tvminordval);
            mListener = listener;
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    public VendorSelectorAdapter(Context context, int version, String server, ArrayList<Or2goVendorInfo> itemList, int layout, RecyclerViewClickListener listener)
    {
        this.mContext = context;
        this.mVendorList = itemList;
        this.UI_VERSION = version;
        this.OR2GO_SERVER = server;
        this.layout = layout;
        this.mListener = listener;

        //ImageLoader.ImageCache imageCache = new LruCache(100);
        //mImageLoader = new ImageLoader(Volley.newRequestQueue(mContext), imageCache);
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
    public VSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(layout, parent, false);
        return new VSViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(final VSViewHolder holder, int position) {
        final int selpos = position;
        Or2goVendorInfo oritem = mVendorList.get(position);

        holder.title.setText(oritem.vName);
        //holder.tag.setText(oritem.vDescription);
        holder.tag.setText(oritem.vTagInfo);
        if (UI_VERSION==2)
            holder.loc.setText("");
        else
            holder.loc.setText(oritem.vLocality+" , "+oritem.vPlace);
        //holder.rating.setText("*****");

        //holder.logo.setImageBitmap(oritem.vLogo);
//        holder.logo.setDefaultImageResId(R.drawable.blankitem); // image for loading...
//        holder.logo.setImageUrl(OR2GO_SERVER+oritem.vLogoPath, mImageLoader); //ImgController
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.blankitem)
                .error(R.drawable.blankitem);

        Glide.with(mContext)
                .load(OR2GO_SERVER+oritem.vLogoPath+"logo.png")
                .apply(options)
                //.override(200, 200) // resizing
                //.fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.logo);

        //Float ratingval = Float.valueOf("4.5");
        //holder.rating.setRating(ratingval);
        if (UI_VERSION==2)
            holder.ratingval.setText("");
        else
            holder.ratingval.setText(mContext.getResources().getString(R.string.sym_star)+" : 4.5");

        String minordval = oritem.getMinOrder();
        if ((minordval.equals("0")) || (minordval.isEmpty()))
            holder.minord.setText("");
        else
        {
            holder.minord.setText("Min Order : "+currency.getSymbol()+minordval);
        }

        if (oritem.getDiscountView().isEmpty())
        {
            holder.discsts.setText("");
            holder.discsts.setBackgroundResource(R.color.white);
        }
        else {
            holder.discsts.setText(oritem.getDiscountView());
            //holder.discsts.setBackground(mContext.getResources().getDrawable(R.drawable.storeclosebg));
            holder.discsts.setBackgroundResource(R.color.blue);
        }
        if (oritem.isOpen()) {
            holder.opensts.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_open_96));
            //holder.storests.setText("Open");
            //holder.storests.setBackgroundColor(0xFF00FF00);
            //holder.storests.setBackground(mContext.getResources().getDrawable(R.drawable.storeopenbg));
        }
        else {
            holder.opensts.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_closed_96));
            //holder.storests.setText("Closed");
            //holder.storests.setBackgroundColor(0xFFFF0000);
            //holder.storests.setBackground(mContext.getResources().getDrawable(R.drawable.storeclosebg));
        }
    }

    @Override
    public int getItemCount() {
        return mVendorList.size();
    }

    /*
    public void itemSelectionHandler(int position)
    {
        OrderGridItem oritem = mOrderItemList.get(position);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(oritem.getTitle()+": selected");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    */
}
