package com.or2go.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.or2go.core.OrderItem;
import com.or2go.core.UnitManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

public class Or2goItemListAdapter extends RecyclerView.Adapter<Or2goItemListAdapter.ViewHolder> {

    ArrayList<OrderItem> mItemList;
//    Activity activity;
    private Context mContext;
    Currency currency = Currency.getInstance("INR");
    UnitManager mUnitMgr = new UnitManager();
    int layout;

    public Or2goItemListAdapter(Context activity, int layout, ArrayList<OrderItem> list) {
        super();
        this.mContext = activity;
        this.layout = layout;
        this.mItemList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderItem item = mItemList.get(position);
        holder.itemname.setText(item.getName() + " [" + item.getOrderUnit()+ item.getUnitName()+ "]");
        if (sendFloatValue(item.getItemTotal()).equals("0.0"))
            holder.itemtotal.setText(currency.getSymbol()+(int) item.getItemTotal());//item.getPrice());
        else
            holder.itemtotal.setText(currency.getSymbol()+item.getItemTotal());//item.getPrice());
        if (item.isWholeItem())
            holder.itemqnty.setText(item.getQnty());
        else
//            holder.itemqnty.setText(item.getQnty()+mUnitMgr.getUnitName(item.getOrderUnit()));
            if (sendFloatValue(item.getPrice()).equals("0.0"))
                holder.itemqnty.setText( currency.getSymbol() + Math.round(item.getPrice()) + " * " + Math.round(Float.parseFloat(item.getQnty())));
            else
                holder.itemqnty.setText( currency.getSymbol() + item.getPrice() + " * " + Math.round(Float.parseFloat(item.getQnty())));
    }

    public String sendFloatValue(Float value){
        Float ss = value;
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(ss));
        int i = bigDecimal.intValue();//180
        String ll = bigDecimal.subtract(new BigDecimal(i)).toPlainString();
        return ll;
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemname;
        TextView itemtotal;
        TextView itemqnty;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = (TextView) itemView.findViewById(R.id.orderitemname);
            itemqnty = (TextView) itemView.findViewById(R.id.orderitemqnty);
            itemtotal= (TextView) itemView.findViewById(R.id.orderitemtotal);
        }
    }

//    public class Or2goItemViewHolder {
//        TextView itemname;
//        TextView itemtotal;
//        TextView itemqnty;
//        Integer listposition;
//        Or2goItemViewHolder()
//        {
//            listposition = -1;
//        }
//    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // TODO Auto-generated method stub
//        // TODO Auto-generated method stub
//        //ViewHolder holder;
//        Or2goItemListAdapter.Or2goItemViewHolder holder;
//        LayoutInflater inflater =  activity.getLayoutInflater();
//        if (convertView == null){
//            convertView = inflater.inflate(R.layout.listview_orderhistory_item, null);
//            holder = new Or2goItemListAdapter.Or2goItemViewHolder();///ViewHolder();
//            //holder.itemimage = (ImageView) convertView.findViewById(R.id.orderitemimage);
//            holder.itemname = (TextView) convertView.findViewById(R.id.orderitemname);
//            holder.itemqnty = (TextView)convertView.findViewById(R.id.orderitemqnty);
//            holder.itemtotal= (TextView) convertView.findViewById(R.id.orderitemtotal);
//            holder.listposition = position;
//            convertView.setTag(holder);
//        }
//        else{
//            holder = (Or2goItemListAdapter.Or2goItemViewHolder) convertView.getTag();
//        }
//        OrderItem item = mItemList.get(position);
//        holder.itemname.setText(item.getName());
//        holder.itemtotal.setText(currency.getSymbol()+item.getItemTotal());//item.getPrice());
//        if (item.isWholeItem())
//            holder.itemqnty.setText(item.getQnty());
//        else
//            holder.itemqnty.setText(item.getQnty()+mUnitMgr.getUnitName(item.getOrderUnit()));
//        int totalHeight = 0;
//        for (int i = 0; i < mItemList.size(); i++){
//        }
//        return convertView;
//    }

}
