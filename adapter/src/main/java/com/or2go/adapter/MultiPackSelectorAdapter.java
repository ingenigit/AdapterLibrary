package com.or2go.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.or2go.core.ProductPriceInfo;
import com.or2go.core.ProductSKU;
import com.or2go.core.UnitManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

public class MultiPackSelectorAdapter extends RecyclerView.Adapter<MultiPackSelectorAdapter.MPViewHolder>{

    //private ArrayList<ProductPriceInfo> mPriceList;
    private ArrayList<ProductSKU> mSKUList;
    MultiPackSelectorAdapter.RecyclerViewItemClickListener recyclerViewItemClickListener;
    Currency currency = Currency.getInstance("INR");
    UnitManager mUnitMgr = new UnitManager();
    int layout;

    public MultiPackSelectorAdapter(/*ArrayList<ProductPriceInfo> pricelist*/ ArrayList<ProductSKU> skulist, int layout, MultiPackSelectorAdapter.RecyclerViewItemClickListener listener)
    {
        //mPriceList = pricelist;
        mSKUList = skulist;
        this.layout = layout;
        this.recyclerViewItemClickListener = listener;
    }

    @NonNull
    @Override
    public MultiPackSelectorAdapter.MPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MultiPackSelectorAdapter.MPViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiPackSelectorAdapter.MPViewHolder asViewHolder, int i) {
        /*ProductPriceInfo packInfo = mPriceList.get(i);
        int skuid = packInfo.mSKUId;
        System.out.println("MultipackSelector Adapter : Product price id="+packInfo.mPriceId+" skuid="+skuid);
        System.out.println("MultipackSelector Adapter : SKU list size ="+mSKUList.size());
        ProductSKU SKUInfo=null;
        for (int j=0; j<mSKUList.size();j++)
        {
            System.out.println("MultipackSelector Adapter : SKU List skuid="+mSKUList.get(j).mSKUId);
            if (mSKUList.get(j).mSKUId == skuid)
            {
                SKUInfo = mSKUList.get(j);
                break;}
        }*/

        ProductSKU SKUInfo= mSKUList.get(i);

        ////ProductSKU SKUInfo = mSKUList.get(skuid);
        if (SKUInfo.mColor.equals("")) {
            asViewHolder.mtvColor.setVisibility(View.GONE);
            asViewHolder.mSpaceCard.setVisibility(View.GONE);
        }else {
            asViewHolder.mtvColor.setVisibility(View.VISIBLE);
            asViewHolder.mSpaceCard.setVisibility(View.VISIBLE);
            String text = "Color: " + SKUInfo.mColor;
            String[] words = text.split(" ");
            words[1] = words[1].toUpperCase();
//            System.out.println(firstLetter + "fvfgfvfvfdvfvf/"+ secondLetter);
            asViewHolder.mtvColor.setText(String.join(" ", words));
//            asViewHolder.mtvColor.setText(firstLetter + secondLetter.toUpperCase() + text.substring(2));
//            asViewHolder.mtvColor.setText("Colo/r: " + SKUInfo.mColor);
        }
        if (SKUInfo.mSize.equals("")){
            asViewHolder.mtvSpace.setVisibility(View.GONE);
            asViewHolder.mSpaceSize.setVisibility(View.GONE);
        }else{
            asViewHolder.mtvSpace.setVisibility(View.VISIBLE);
            asViewHolder.mSpaceSize.setVisibility(View.VISIBLE);
            asViewHolder.mtvSpace.setText("Size: " + SKUInfo.mSize);
        }
        if (sendFloatValue(SKUInfo.mAmount).equals("0.0"))
            asViewHolder.mUnit.setText(/*packInfo.mAmount.toString()*/Math.round(SKUInfo.mAmount)+ mUnitMgr.getUnitName(SKUInfo.mUnit));
        else
            asViewHolder.mUnit.setText(/*packInfo.mAmount.toString()*/SKUInfo.mAmount.toString()+ mUnitMgr.getUnitName(SKUInfo.mUnit));
        if (sendFloatValue(SKUInfo.mPrice).equals("0.0") || sendFloatValue(SKUInfo.mMRP).equals("0.0")) {
            asViewHolder.mPrice.setText(currency.getSymbol() + Math.round(SKUInfo.mPrice));
            asViewHolder.mMaxPrice.setText(currency.getSymbol() + Math.round(SKUInfo.mMRP));
        }else{
            asViewHolder.mPrice.setText(currency.getSymbol() + SKUInfo.mPrice);
            asViewHolder.mMaxPrice.setText(currency.getSymbol() + SKUInfo.mMRP);
        }

        asViewHolder.mMaxPrice.setPaintFlags(asViewHolder.mMaxPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
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
        return mSKUList.size();
    }

    public  class MPViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mUnit;
        public TextView mPrice;
        public TextView mMaxPrice;
        public TextView mSpaceCard, mSpaceSize, mSpaceUnit;
        public TextView mtvColor, mtvSpace;


        public MPViewHolder(View v) {
            super(v);
            mtvColor = (TextView) v.findViewById(R.id.textViewColor);
            mSpaceCard = (TextView) v.findViewById(R.id.spaceColor);
            mtvSpace = (TextView) v.findViewById(R.id.textViewSize);
            mSpaceSize = (TextView) v.findViewById(R.id.spaceSize);
            mUnit = (TextView) v.findViewById(R.id.tvpkunit);
            mSpaceUnit = (TextView) v.findViewById(R.id.spaceUnit);
            mPrice = (TextView) v.findViewById(R.id.tvpkprice);
            mMaxPrice = (TextView) v.findViewById(R.id.tvpkmrp);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewItemClickListener.onMultiPackSelectItem(mSKUList.get(this.getAdapterPosition()));
        }
    }

    public interface RecyclerViewItemClickListener {
        void onMultiPackSelectItem(ProductSKU data);
    }

}

