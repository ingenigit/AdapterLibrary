package com.or2go.adapter.daAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.or2go.adapter.R;
import com.or2go.core.OrderItem;

import java.util.ArrayList;
import java.util.Currency;

public class Or2goItemListAdapter extends RecyclerView.Adapter<Or2goItemListAdapter.ViewHolder> {
    ArrayList<OrderItem> mItemList;
    Activity activity;
    int layout;
    Currency currency = Currency.getInstance("INR");

    public Or2goItemListAdapter(Activity activity, ArrayList<OrderItem> list, int layout) {
        super();
        this.activity = activity;
        this.mItemList = list;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItem item = mItemList.get(position);
        //holder.itemimage.setImageBitmap(item.getImage());
        //holder.itemimage.setImageBitmap(Bitmap.createScaledBitmap(item.getImage(), 96, 96, false));
        holder.itemname.setText(item.getName());
        holder.itemprice.setText(currency.getSymbol()+item.getPrice().toString());//item.getPrice());
        holder.itemtotal.setText(currency.getSymbol()+item.getItemTotal());
        //Integer unit = item.getPriceUnit();
        if (item.isWholeItem())
            holder.itemqnty.setText(item.getQnty().toString());
        else {
            holder.itemqnty.setText(item.getQnty().toString());
            holder.itemunit.setText(item.getUnitName().toString());
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemname, itemprice, itemqnty, itemunit, itemtotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //itemimage = (ImageView) itemView.findViewById(R.id.orderitemimage);
            itemname = (TextView) itemView.findViewById(R.id.or2itemname);
            //itemprice = (TextView) itemView.findViewById(R.id.orderitemprice);
            itemqnty = (TextView)itemView.findViewById(R.id.or2itemqnty);
            itemunit= (TextView) itemView.findViewById(R.id.or2itemunit);;
            itemprice= (TextView) itemView.findViewById(R.id.or2itemprice);
            itemtotal= (TextView) itemView.findViewById(R.id.or2itemtotal);
        }
    }
}
