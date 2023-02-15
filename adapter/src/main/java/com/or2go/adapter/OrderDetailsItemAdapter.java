package com.or2go.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.or2go.core.OrderItem;
import com.or2go.core.ProductPriceInfo;
import com.or2go.core.ProductSKU;
import com.or2go.core.UnitManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

public class OrderDetailsItemAdapter extends RecyclerView.Adapter<OrderDetailsItemAdapter.OrderItemViewHolder>{
    private Context mContext;
    ArrayList<OrderItem> mItemList;
    Currency currency = Currency.getInstance("INR");
    UnitManager mUnitMgr = new UnitManager();
    int layout;
    RecyclerViewClickListener mListener;

    public interface RecyclerViewClickListener {
        void onClick(View view, int position);
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RecyclerViewClickListener mListener;
        TextView itemname;
        TextView itemqnty;
        TextView itemtotal;
        //TextView itemstksts;
        //Button itemstkremove;
        public OrderItemViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            itemname = (TextView) view.findViewById(R.id.orderitemname);
            itemtotal = (TextView) view.findViewById(R.id.orderitemtotal);
            itemqnty = (TextView)view.findViewById(R.id.orderitemqnty);
            //itemstksts = (TextView) view.findViewById(R.id.orderitemstksts);
            //itemstkremove= (Button)  view.findViewById(R.id.btremoveitemstk);
            mListener = listener;
            view.setOnClickListener(this);
            //itemstkremove.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }
    }

    public OrderDetailsItemAdapter(Context context, ArrayList<OrderItem> itemList, int layout, RecyclerViewClickListener listener)
    {
        this.mContext = context;
        this.mItemList = itemList;
        this.layout = layout;
        this.mListener = listener;
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(layout, parent, false);
        return new OrderItemViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(final OrderItemViewHolder holder, int position) {
        final int selpos = position;
        OrderItem item = mItemList.get(position);
        if (sendFloatValue(item.getPrice()).equals("0.0"))
            holder.itemqnty.setText(currency.getSymbol() + Math.round(item.getPrice())+" x " +Math.round(Float.parseFloat(item.getQnty())));
        else
            holder.itemqnty.setText(currency.getSymbol() + item.getPrice()+" x " +Math.round(Float.parseFloat(item.getQnty())));
        if (sendFloatValue(item.getItemTotal()).equals("0.0"))
            holder.itemtotal.setText(currency.getSymbol()+(int) item.getItemTotal());
        else
            holder.itemtotal.setText(currency.getSymbol()+item.getItemTotal());

        Log.i("OrderDetailsItemAdapter" , "Order pack id="+item.getPriceId()+" skuid="+item.getSKUId());
        ProductPriceInfo pkinfo = item.getPriceInfo();
        ProductSKU skuinfo = item.getSKUInfo();
        if (pkinfo !=null)
        {
            //pkinfo.dumpInfo();SKUInfo.mAmount.toString()+ packInfo.getUnitName()
            if (skuinfo != null)
                holder.itemname.setText(item.getName() + " [" + Math.round(skuinfo.mAmount) + pkinfo.getUnitName() + "]");
//                holder.itemqnty.setText(skuinfo.mAmount.toString()+ pkinfo.getUnitName()+"  "+item.getPrice().toString()+" x " +item.getQnty());
            else
                holder.itemname.setText(item.getName());


                //+mUnitMgr.getUnitName(item.getOrderUnit())
        }
        /*else {
           Log.i("OrderDetailsItemAdapter" , "Order pack info is NULL!!!");
            holder.itemqnty.setText(item.getQnty());
        }*/

        /*if ((item.getInvControl()>0) && (item.getCurStock() >= 0))
        {
            Float stkval = item.getCurStock();
            if (stkval==0) {
                holder.itemstksts.setText("Item Out Of Stock");
                holder.itemstkremove.setVisibility(View.VISIBLE);
            }
            else if (stkval < item.getQntyVal()) {
                holder.itemstksts.setText("Item Order Quantity Unavailbale ");
                holder.itemstkremove.setVisibility(View.VISIBLE);
            }
            else {
                holder.itemstksts.setText("");
                holder.itemstkremove.setVisibility(View.GONE);
            }
        }
        else
        { holder.itemstksts.setText("");
            holder.itemstkremove.setVisibility(View.GONE);}*/
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public String sendFloatValue(Float value){
        Float ss = value;
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(ss));
        int i = bigDecimal.intValue();//180
        String ll = bigDecimal.subtract(new BigDecimal(i)).toPlainString();
        return ll;
    }
}
