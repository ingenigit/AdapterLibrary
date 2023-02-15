package com.or2go.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.or2go.core.ProductPriceInfo;
import com.or2go.core.ProductSKU;
import com.or2go.core.SalesSelectInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;

public class SelectPackSizeAdapter extends RecyclerView.Adapter<SelectPackSizeAdapter.ViewHolder> {
    ArrayList<ProductPriceInfo> priceInfos;
    ArrayList<ProductSKU> productSKUS;
    SalesSelectInfo mOrderItemList;
    Currency currency = Currency.getInstance("INR");
    RecyclerViewItemClickListener recyclerViewItemClickListener;
    DecimalFormat df = new DecimalFormat("0");
    private int layout;
    private final DecimalFormat decfor = new DecimalFormat("0.00");
    //int mSelectedItem;

    public SelectPackSizeAdapter(ArrayList<ProductPriceInfo> arrayList, ArrayList<ProductSKU> arrayList1, SalesSelectInfo oritem, int layout, RecyclerViewItemClickListener listener) {
        this.priceInfos = arrayList;
        this.productSKUS = arrayList1;
        //this.mSelectedItem = selectedPosition;
        this.mOrderItemList = oritem;
        this.layout = layout;
        this.recyclerViewItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductPriceInfo packInfo = priceInfos.get(position);
        /*if (packInfo.mPriceId == mSelectedItem){
            holder.relativeLayout.setBackgroundResource(R.drawable.highlight_select_bg);
        }else{
            holder.relativeLayout.setBackgroundResource(R.drawable.pack_size_bg);
        }*/
//        holder.radioButton.setChecked(position == mSelectedItem);

        Float fQnty= mOrderItemList.mapQuantity.get(packInfo.mPriceId);
        System.out.println("SelectPackAdapter: priceid="+packInfo.mPriceId+"  Qnty="+fQnty);
        //String totalQTY; //mOrderItemList.getViewQnty();
        if (fQnty==null)
            fQnty=Float.parseFloat("0");
        //else
        //    totalQTY=fQnty.toString();
        //if (Integer.parseInt(totalQTY) == 0)
        if (fQnty==0){
            holder.addItem.setVisibility(View.VISIBLE);
            holder.itemqnty.setText("");
        }else{
            //if (packInfo.mPriceId == mSelectedItem){
                holder.addItem.setVisibility(View.GONE);
                holder.itemqnty.setText(fQnty.toString());//(totalQTY);
            //}else{
//                holder.addItem.setVisibility(View.VISIBLE);
//                holder.itemqnty.setText("");
            //}
        }


        int skuid = packInfo.mSKUId;
        ProductSKU SKUInfo = null;
        if (skuid!=0) {
            for (int j = 0; j < productSKUS.size(); j++) {
                if (productSKUS.get(j).mSKUId == skuid) {
                    SKUInfo = productSKUS.get(j);
                    break;
                }
            }
        }
        holder.textViewSP.setText(currency.getSymbol() +packInfo.mSalePrice.toString());
        if (SKUInfo !=null)
            holder.textViewQty.setText(SKUInfo.mAmount.toString()+ packInfo.getUnitName());
        else
            holder.textViewQty.setText(packInfo.mAmount.toString()+ packInfo.getUnitName());
        String MRP = packInfo.mMaxPrice.toString();
        if(MRP.isEmpty()){
            holder.textViewDic.setText("");
            holder.textViewDic.setVisibility(View.GONE);
        }else{
            holder.textViewMRP.setText(currency.getSymbol() +packInfo.mMaxPrice.toString());
            Float discamnt= packInfo.getDiscountValue();
            if (discamnt != null) {
                if (discamnt > 5) {
                    holder.textViewDic.setText(df.format(discamnt) + "% Off");
                    holder.textViewDic.setVisibility(View.VISIBLE);
                } else {
                    Float discrs = packInfo.mMaxPrice - packInfo.mSalePrice;
                    if (discrs >= 1) {
                        holder.textViewDic.setText(currency.getSymbol() + df.format(discrs) + " Off");
                        holder.textViewDic.setVisibility(View.VISIBLE);
                    } else {
                        holder.textViewDic.setText("");
                        holder.textViewDic.setVisibility(View.GONE);
                    }
                }
            } else {
                holder.textViewDic.setText("");
                holder.textViewDic.setVisibility(View.GONE);
            }
        }

        float SP = packInfo.mSalePrice;
        String QUnit = packInfo.getUnitName();
        int aam = Math.round(packInfo.mAmount);
        System.out.println("sbs " + aam + QUnit);
        if (aam >= 1 && QUnit.equals("Kg")){
            float aak = (SP/aam);
            float amk = (aak/4);
            holder.textViewPerQty.setText(currency.getSymbol() + decfor.format(amk) + "/250g");
            System.out.println(amk);
        }else if(aam >= 1 && QUnit.equals("g")){
            float aak = (SP/aam);
            float amk = (aak*25);
            holder.textViewPerQty.setText(currency.getSymbol() + decfor.format(amk) + "/25g");
            System.out.println(amk);
        }else if(QUnit.equals("L")){
            float aak = (SP/aam);
            float amk = (aak/4);
            holder.textViewPerQty.setText(currency.getSymbol() + decfor.format(amk) + "/250l");
            System.out.println(amk);
        }else if(QUnit.equals("ml")){
            float aak = (SP/aam);
            float amk = (aak*25);
            holder.textViewPerQty.setText(currency.getSymbol() + decfor.format(amk) + "/25ml");
            System.out.println(amk);
        }else{
            System.out.println("sorry");
        }
        holder.textViewMRP.setPaintFlags(holder.textViewMRP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return priceInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout linearLayout;
        RelativeLayout relativeLayout;
        TextView textViewMRP, textViewSP, textViewDic, textViewQty, textViewPerQty;
        TextView itemqnty;
        Button addItem;
        MaterialButton itemAdd, itemDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.selectedOne);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.showQtyWant);
            textViewMRP = (TextView) itemView.findViewById(R.id.tv_mrp);
            textViewSP = (TextView) itemView.findViewById(R.id.tv_sp);
            textViewDic = (TextView) itemView.findViewById(R.id.tv_dic);
            textViewQty = (TextView) itemView.findViewById(R.id.tv_qty);
            textViewPerQty = (TextView) itemView.findViewById(R.id.tv_per_qty);
            itemqnty = (TextView) itemView.findViewById(R.id.itmqnty);
            itemAdd = (MaterialButton) itemView.findViewById(R.id.itmadd);
            itemDelete = (MaterialButton) itemView.findViewById(R.id.itmdec);
            addItem = (Button) itemView.findViewById(R.id.buttonAdd);
            itemView.setOnClickListener(this);
            addItem.setOnClickListener(this);
            itemAdd.setOnClickListener(this);
            itemDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewItemClickListener.onMultiPackSelectItem(v, priceInfos.get(this.getAdapterPosition()), getAdapterPosition());
        }
    }

    /*public void setSelectedPosition(int position)
    {
        if (mSelectedItem >= 0) notifyItemChanged(mSelectedItem);
        mSelectedItem = position;
        notifyItemChanged(position);
    }*/

    public interface RecyclerViewItemClickListener {
        void onMultiPackSelectItem(View view, ProductPriceInfo data, int position);
    }
}
