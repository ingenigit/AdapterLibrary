package com.or2go.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SalesSubCatListAdapter extends RecyclerView.Adapter<SalesSubCatListAdapter.TypeListViewHolder>{
    private Context mContext;
    private List<String> list;
    private int layout;
    private RecyclerViewClickListener mListener;
    int mSelectedItem=-1;

    public SalesSubCatListAdapter(Context context, List<String> horizontalList, int layout, RecyclerViewClickListener listener) {
        mContext = context;
        mListener = listener;
        this.layout = layout;
        this.list = horizontalList;
    }

    @NonNull
    @Override
    public TypeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new TypeListViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeListViewHolder holder, int position) {
//        holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.design_default_color_background));
        holder.cardView.setCardBackgroundColor(Color.WHITE);
        holder.textView.setText(list.get(position));
        if (mSelectedItem == position) {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.dot_light_screen3));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TypeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RecyclerViewClickListener mListener;
        public TextView textView;
        public CardView cardView;

        public TypeListViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            textView = (TextView) view.findViewById(R.id.tvcatname);
            cardView = (CardView) view.findViewById(R.id.sales_category_cardview);
            mListener = listener;
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());

        }
    }

    public void setSelectedCategory(int pos) {
        if (mSelectedItem >= 0) notifyItemChanged(mSelectedItem);
        mSelectedItem = pos;
        notifyItemChanged(pos);
    }

    public void clearSelectedCategory(int pos) {
        mSelectedItem = -1;
        notifyItemChanged(pos);
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }
}
