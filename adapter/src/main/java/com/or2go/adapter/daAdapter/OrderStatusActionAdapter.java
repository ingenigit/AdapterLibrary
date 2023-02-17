package com.or2go.adapter.daAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.or2go.adapter.R;

import java.util.List;

public class OrderStatusActionAdapter extends RecyclerView.Adapter<OrderStatusActionAdapter.OrderActionViewHolder> {

    private List<String> mItems;
    private int layout;
    private OrderStatusActionListener mListener;

    public OrderStatusActionAdapter(List<String> items, int layout, OrderStatusActionListener listener) {
        mItems = items;
        this.layout = layout;
        mListener = listener;
    }

    @Override
    public OrderActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderActionViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false));
    }


    @Override
    public void onBindViewHolder(OrderActionViewHolder holder, int position) {
        holder.textView.setText(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class OrderActionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        String item;

        OrderActionViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.tv_order_status_action);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                item = mItems.get(getAdapterPosition());
                mListener.onActionClick(item);
            }
        }
    }

    public interface OrderStatusActionListener {
        void onActionClick(String item);
    }
}
