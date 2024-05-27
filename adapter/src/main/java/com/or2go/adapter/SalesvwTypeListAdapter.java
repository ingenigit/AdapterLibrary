package com.or2go.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SalesvwTypeListAdapter extends RecyclerView.Adapter<SalesvwTypeListAdapter.TypeListViewHolder>{

    private Context mContext;
    private List<String> list;
    private int layout;
    int mSelectedItem=-1;
    private boolean isLoading;
    private RecyclerViewClickListener mListener;

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


    public SalesvwTypeListAdapter(Context context, List<String> horizontalList, int layout, RecyclerViewClickListener listener) {

        mContext = context;
        mListener = listener;
        this.layout = layout;
        this.list = horizontalList;
        this.isLoading = true;
    }

    @Override
    public TypeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new TypeListViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(final TypeListViewHolder holder, final int position) {

//        holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.design_default_color_background));
        holder.cardView.setCardBackgroundColor(Color.WHITE);
        if (mSelectedItem == position) {
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.dot_light_screen3));
        }

        holder.textView.setText(list.get(position));

    }

    @Override
    public int getItemCount() {
        return isLoading ? 0 : list.size();
    }

    public void updateData(List<String> catList) {
        this.isLoading = false;
        this.list = catList;
        notifyDataSetChanged();
    }

    public void setSelectedCategory(int pos)
    {
        if (mSelectedItem >= 0) notifyItemChanged(mSelectedItem);
        mSelectedItem = pos;
        notifyItemChanged(pos);
    }

    public void clearSelectedCategory(int pos)
    {
        mSelectedItem = -1;
        notifyItemChanged(pos);
    }

    public void clearSelection()
    {
        notifyItemChanged(mSelectedItem);
        mSelectedItem = -1;
    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);

    }


}
