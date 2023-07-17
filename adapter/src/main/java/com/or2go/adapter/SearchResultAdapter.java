package com.or2go.adapter;

import static com.or2go.core.Or2goConstValues.OR2GO_SEARCH_PRODUCT_NAME;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.or2go.core.SearchInfo;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SRViewHolder>{

    private Context mContext;
    //private List<Album> albumList;
    ArrayList<SearchInfo> mResultList;
    int layout;
    RecyclerViewClickListener mListener;

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public class SRViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecyclerViewClickListener mListener;
        public TextView title, tag, loc;
        public ImageView descimg;


        public SRViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvsearchitemname);
            tag = (TextView) view.findViewById(R.id.tvsearchitemtype);
            descimg = (ImageView)  view.findViewById(R.id.imgsearchtype);
            mListener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    public SearchResultAdapter(Context context, ArrayList<SearchInfo> itemList, int layout, RecyclerViewClickListener listener)
    {
        this.mContext = context;
        this.mResultList = itemList;
        this.layout = layout;
        this.mListener = listener;
    }

    @Override
    public SRViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(layout, parent, false);
        return new SRViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(final SRViewHolder holder, int position) {
        final int selpos = position;
        SearchInfo oritem = mResultList.get(position);
        holder.title.setText(oritem.name);
        //holder.tag.setText(oritem.type);
        if (oritem.searchtype==null)
            holder.tag.setText(oritem.store);
        else if (oritem.searchtype==OR2GO_SEARCH_PRODUCT_NAME)
        {
            holder.tag.setText("");
            holder.tag.setVisibility(View.GONE);
        }
        else {
            holder.tag.setText(oritem.store);
//            if (oritem.type.equals("Vendor") || (oritem.type.equals("Store"))) {
//                holder.tag.setText("Restaurant");
//                holder.descimg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.restaurant1));
//            } else {
//                holder.descimg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.food2));
//                holder.tag.setText("Cuisine");
//            }
        }
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public void updateData(ArrayList<SearchInfo> datalist){
        mResultList = datalist;
        notifyDataSetChanged();
    }

}
