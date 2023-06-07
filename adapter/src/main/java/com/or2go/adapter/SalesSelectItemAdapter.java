package com.or2go.adapter;

import static com.or2go.core.Or2goConstValues.OR2GO_PRODUCT_TAG_FOOD_NONVEG;
import static com.or2go.core.Or2goConstValues.OR2GO_PRODUCT_TAG_FOOD_VEG;
import static com.or2go.core.UnitManager.GPOS_PROD_UNIT_PC;
import static com.or2go.core.UnitManager.GPOS_PROD_UNIT_PLT;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.or2go.core.ProductPriceInfo;
import com.or2go.core.ProductSKU;
import com.or2go.core.SalesSelectInfo;
import com.or2go.core.UnitManager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;

public class SalesSelectItemAdapter extends RecyclerView.Adapter<SalesSelectItemAdapter.SSViewHolder>{

    private Context mContext;
    //private List<Album> albumList;
    ArrayList<SalesSelectInfo> mOrderItemList;
    String vendorId;
    RecyclerViewClickListener mListener;
    private int layout;
    private String OR2GO_SERVER;
    //private RequestQueue mRequestQueue;
    //ImageLoader mImageLoader;
    //int cacheSize = 4 * 1024 * 1024; // 4MiB
    Currency currency = Currency.getInstance("INR");
    UnitManager mUnitMgr = new UnitManager();
    DecimalFormat df = new DecimalFormat("0");

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);

    }

    public class SSViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecyclerViewClickListener mListener;

        public TextView title, price, qnty, desc, unit, mrp, brand, disc;
        //Spinner multiunits;
        public ImageView multisel;
        public ImageView prodimg;//public NetworkImageView prodimg;
        public ImageView itemedit;
        public MaterialButton addbtn, decbtn, editbtn;
        public Button additem;

        //Tags
        public ImageView foodtag;//, vtag2, vtag3, vtag4, vtag5;
        public ImageView[] vtags = new ImageView[4];
        public TextView[]  ttags = new TextView[3];//tag1, tag2, tag3;

        public CardView cardView;
        public CardView cardViewStockOut;

        public SSViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            title = (TextView) view.findViewById(R.id.itemname);
            brand= (TextView) view.findViewById(R.id.brandname);
            price = (TextView) view.findViewById(R.id.itemprice);
            qnty = (TextView) view.findViewById(R.id.itemqnty);
            desc = (TextView) view.findViewById(R.id.itemdesc);
            unit = (TextView) view.findViewById(R.id.itempackunit);
            mrp = (TextView) view.findViewById(R.id.itemmaxprice);
            disc = (TextView) view.findViewById(R.id.itemdiscval);
            //disc.setTextColor(mContext.getResources().getColor(R.color.colorAccent));

            prodimg = (ImageView ) view.findViewById(R.id.gimgprodprop);//(NetworkImageView ) view.findViewById(R.id.imgprodprop);

            multisel=(ImageView) view.findViewById(R.id.imgmultipacksel) ;
            //multiunits = (Spinner) view.findViewById(R.id.multiunit);
            //thumbnail = (ImageView) view.findViewById(R.id.ic_itemdetail);

            cardView = (CardView) view.findViewById(R.id.card_view);
            cardViewStockOut = (CardView) view.findViewById(R.id.cardView_OutStock);
            //itemedit = (ImageView)  view.findViewById(R.id.itemedit);
            addbtn = (MaterialButton) view.findViewById(R.id.itemadd) ;
            decbtn = (MaterialButton) view.findViewById(R.id.itemdec) ;

            additem = (Button) view.findViewById(R.id.btAddItem) ;

            foodtag = (ImageView) view.findViewById(R.id.tagimg1) ;
            vtags[0] = (ImageView) view.findViewById(R.id.tagimg2) ;
            vtags[1] = (ImageView) view.findViewById(R.id.tagimg3) ;
            vtags[2] = (ImageView) view.findViewById(R.id.tagimg4) ;
            vtags[3] = (ImageView) view.findViewById(R.id.tagimg5) ;

            ttags[0] = (TextView) view.findViewById(R.id.tvtag1) ;
            ttags[1] = (TextView) view.findViewById(R.id.tvtag2) ;
            ttags[2] = (TextView) view.findViewById(R.id.tvtag3) ;

            ttags[0].setTextColor(mContext.getResources().getColor(R.color.tag1Color));
            ttags[1].setTextColor(mContext.getResources().getColor(R.color.tag2Color));
            ttags[2].setTextColor(mContext.getResources().getColor(R.color.tag3Color));

            mListener = listener;
            view.setOnClickListener(this);
            //thumbnail.setOnClickListener(this);

            //itemedit.setOnClickListener(this);
            addbtn.setOnClickListener(this);
            decbtn.setOnClickListener(this);
            multisel.setOnClickListener(this);
            prodimg.setOnClickListener(this);
            additem.setOnClickListener(this);
        }

        @Override

        public void onClick(View view) {

            mListener.onClick(view, getAdapterPosition());

        }
    }

    public SalesSelectItemAdapter(Context context, String server, String vendorId, ArrayList<SalesSelectInfo> itemList, int layout, RecyclerViewClickListener listener)
    {
        this.mContext = context;
        this.mOrderItemList = itemList;
        this.layout = layout;
        this.OR2GO_SERVER = server;
        this.vendorId = vendorId;
        this.mListener = listener;

    }

    public void updateData(ArrayList<SalesSelectInfo> itemList) {

        mOrderItemList.clear();

        mOrderItemList = itemList;

        notifyDataSetChanged();

    }

    public void updateView(int position)
    {
        SalesSelectInfo oritem = mOrderItemList.get(position);

    }

    @Override
    public SSViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(layout, parent, false);

        return new SSViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(final SSViewHolder holder, int position) {
        final int selpos = position;
        SalesSelectInfo oritem = mOrderItemList.get(position);

        ///Log.i("SalesSelectItemAdapter", " ItemListSize="+mOrderItemList.size()+ " showeing item pos="+position);

        //Currency currency = Currency.getInstance("INR");

        holder.title.setText(oritem.getName());
        holder.brand.setText(oritem.getBrand());
        holder.desc.setText(oritem.getDesc());

        //holder.prodimg.setDefaultImageResId(R.drawable.blankitem); // image for loading...
        //holder.prodimg.setImageUrl(BuildConfig.OR2GO_SERVER+"prodimage/"+prodNameToImagePath(oritem.getBrand(), oritem.getName())+".jpg", mImageLoader); //ImgController
        System.out.println("Radha Radha" + oritem.getProduct().getImagepath());
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.blankitem)
                .error(R.drawable.blankitem);

        if(oritem.getProduct().getImagepath() == 0){
            System.out.println(OR2GO_SERVER+"prodimage/"+prodNameToImagePath(oritem.getBrand(), oritem.getName()) + ".jpg");
            Glide.with(mContext)
                    .load(OR2GO_SERVER+"prodimage/"+prodNameToImagePath(oritem.getBrand(), oritem.getName()) + ".jpg")
                    .apply(options)
                    //.override(200, 200) // resizing
                    //.fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(holder.prodimg);
        }else if (oritem.getProduct().getImagepath() == 1){
            System.out.println(OR2GO_SERVER+"vendorprodimage/"+vendorId+"/"+oritem.getId()+ ".jpg");
            Glide.with(mContext)
                    .load(OR2GO_SERVER+"vendorprodimage/"+vendorId+"/"+oritem.getId()+ ".jpg")
                    .apply(options)
                    //.override(200, 200) // resizing
                    //.fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(holder.prodimg);
        }else
            Toast.makeText(mContext, "No Product Image", Toast.LENGTH_SHORT).show();

        ArrayList<ProductSKU> SKUList = oritem.getSKUList();
        //ArrayList<ProductPriceInfo> pricelist = oritem.getPriceList();
        if (SKUList!= null) {
            if (oritem.isMultiPack() && (SKUList.size() > 1)) {
                holder.multisel.setVisibility(View.VISIBLE);
                //holder.unit.setVisibility(View.VISIBLE);
            } else {
                holder.multisel.setVisibility(View.GONE);
                ///holder.unit.setVisibility(View.GONE);
            }
        }
        //if (oritem.isMultiPack())
        //{
            //holder.multiunits.setVisibility(View.VISIBLE);
            //holder.multisel.setVisibility(View.VISIBLE);


            if (SKUList!=null) {
                //ProductPriceInfo packinfo = oritem.getSelectedPriceInfo();//pricelist.get(0);
                ProductSKU skuinfo = oritem.getSelectedSKUInfo();
                //System.out.println("Item info:"+oritem.getName()+" amount: "+packinfo.getPackAmount() + "  unit:"+packinfo.mUnit+"  uname:"+packinfo.getUnitName());

                String smrp = getMRPStr(skuinfo);

                if (sendFloatValue(String.valueOf(skuinfo.mPrice)).equals("0.0"))
                    holder.price.setText(currency.getSymbol() + Math.round(skuinfo.mPrice));
                else
                    holder.price.setText(currency.getSymbol() + skuinfo.mPrice);
                if (smrp.isEmpty()) {
                    holder.mrp.setText("");
                    holder.disc.setText("");
                    holder.disc.setVisibility(View.GONE);
                }
                else {
                    if (sendFloatValue(smrp).equals("0.0"))
                        holder.mrp.setText(currency.getSymbol() + Math.round(Float.parseFloat(smrp)));
                    else
                        holder.mrp.setText(currency.getSymbol() + smrp);
                    Float discamnt= getDiscountValue(skuinfo);
                    if (discamnt != null) {

                        if (discamnt > 5) {
                            holder.disc.setText(df.format(discamnt) + "% Off");
                            holder.disc.setVisibility(View.VISIBLE);
                        }
                        else {
                            Float discrs = skuinfo.mMRP - skuinfo.mPrice;
                            if (discrs >= 1)
                            {
                                holder.disc.setText(currency.getSymbol() + df.format(discrs) + " Off");
                                holder.disc.setVisibility(View.VISIBLE);
                            }
                            else {
                                holder.disc.setText("");
                                holder.disc.setVisibility(View.GONE);
                            }


                        }
                    }
                    else {
                        holder.disc.setText("");
                        holder.disc.setVisibility(View.GONE);
                    }

                }

                if ((skuinfo.mUnit==GPOS_PROD_UNIT_PC) || (skuinfo.mUnit==GPOS_PROD_UNIT_PLT))
                    holder.unit.setVisibility(View.GONE);
                else
                    holder.unit.setVisibility(View.VISIBLE);
                if (sendFloatValue(skuinfo.mAmount.toString()).equals("0.0"))  ///???
                    holder.unit.setText(Math.round(skuinfo.mAmount) + mUnitMgr.getUnitName(skuinfo.mUnit));
                else
                    holder.unit.setText(skuinfo.mAmount.toString() + mUnitMgr.getUnitName(skuinfo.mUnit));

                oritem.mSKUSelectId = skuinfo.mSKUId;

//                if(!oritem.isInventoryControl() /*&& (packinfo.mCurStk == 0)*/) {
//                    holder.cardViewStockOut.setVisibility(View.VISIBLE);
//                    holder.additem.setEnabled(false);
//                }
//                else
//                    holder.cardViewStockOut.setVisibility(View.GONE);

            }
            else
            {
                System.out.println("Price list is null for:"+oritem.getName());
            }
        //}

        /*else
            {
                String smrp = oritem.getMRPStr();
                //holder.multiunits.setVisibility(View.GONE);
                holder.multisel.setVisibility(View.GONE);

                holder.price.setText(currency.getSymbol()+oritem.getPrice());
                if (smrp.isEmpty())
                    holder.mrp.setText("");
                else
                holder.mrp.setText(currency.getSymbol()+smrp);
                holder.unit.setText(oritem.getViewQnty());

            }*/

        holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        Float fQnty= oritem.mapQuantity.get(oritem.mSKUSelectId);
        if (fQnty==null) fQnty=Float.parseFloat("0");
        //if (!oritem.isQntyEmpty())
        if (fQnty!=0){

            holder.additem.setVisibility(View.GONE);
            holder.qnty.setText(String.valueOf(Math.round(fQnty)));//oritem.getViewQnty());
            /*
            if (oritem.getPriceUnit() == GPOS_PROD_UNIT_PC)
                holder.qnty.setText(oritem.getQnty().toString());
            else
                holder.qnty.setText(oritem.getQnty().toString()+mUnitMgr.getUnitName(oritem.getOrderUnit()));
                */

        }
        else {
            holder.qnty.setText("");
            holder.additem.setVisibility(View.VISIBLE);
        }

//        if(!oritem.isInventoryControl())
//            holder.stocksts.setVisibility(View.GONE);

        if (oritem.mFoodType == OR2GO_PRODUCT_TAG_FOOD_VEG)
        {holder.foodtag.setVisibility(View.VISIBLE);
            holder.foodtag.setImageResource(R.drawable.veg_24);}
        else if (oritem.mFoodType == OR2GO_PRODUCT_TAG_FOOD_NONVEG)
        {
            holder.foodtag.setVisibility(View.VISIBLE);
            holder.foodtag.setImageResource(R.drawable.non_veg_24);
        }
        else
        {
            holder.foodtag.setVisibility(View.GONE);
        }

        int vtagcnt = oritem.mVisualTags.size();
        //int curtagno=0;
        if (vtagcnt > 4) vtagcnt = 4;
        for(int i=0;i<vtagcnt;i++)
        {
            String vtag =  oritem.mVisualTags.get(i);
            //System.out.println("Product "+ oritem.getName()+" visual tag:"+vtag);
            if(vtag.equals("New")) {
                holder.vtags[i].setVisibility(View.VISIBLE);
                holder.vtags[i].setImageResource(R.drawable.ic_item_new);
            }
            else if(vtag.equals("Best Seller")) {
                holder.vtags[i].setVisibility(View.VISIBLE);
                holder.vtags[i].setImageResource(R.drawable.ic_item_bestseller);
            }
            else if(vtag.equals("Must Try")) {
                holder.vtags[i].setVisibility(View.VISIBLE);
                holder.vtags[i].setImageResource(R.drawable.ic_item_recomended);
            }
            else if(vtag.equals("Popular")) {
                holder.vtags[i].setVisibility(View.VISIBLE);
                holder.vtags[i].setImageResource(R.drawable.ic_item_popular);
            }
            else if(vtag.equals("On Sale")) {
                holder.vtags[i].setVisibility(View.VISIBLE);
                holder.vtags[i].setImageResource(R.drawable.ic_item_discount);
            }
        }

        if(vtagcnt<4)
        {
            int blankvtagcnt = 4-vtagcnt;
            for(int j =0; j < blankvtagcnt; j++)
            {
                holder.vtags[4-j-1].setVisibility(View.GONE);
            }
        }

        holder.ttags[0].setText("");
        holder.ttags[1].setText("");
        holder.ttags[2].setText("");
        int ttagcnt = oritem.mTextTags.size();
        for(int i=0;i<ttagcnt;i++)
        {
            String ttag =  oritem.mTextTags.get(i);
            holder.ttags[i].setText(ttag);
        }

    }

    public String sendFloatValue(String value){
        String ss = value;
        BigDecimal bigDecimal = new BigDecimal(ss);
        int i = bigDecimal.intValue();
        String ll = bigDecimal.subtract(new BigDecimal(i)).toPlainString();
        return ll;
    }

    @Override
    public int getItemCount() {

        return mOrderItemList.size();
    }


    private String prodNameToImagePath(String brand, String name)
    {
        String lname = name.toLowerCase();
        String sname;

        if (brand != null && (!brand.isEmpty()) && (!brand.equals("null"))) {
            String lbrand = brand.toLowerCase();
            if (lname.contains(lbrand))
                sname=lname;
            else
                sname = lbrand + "_" + lname;
        }
        else
            sname = lname;

        String bname = sname.replace(" ", "_");
        String fname = bname.replace("&", "_");
        String rname = fname.replace(",", "_");

        return (rname);
    }

    public String getMRPStr(ProductSKU skuinfo)
    {
        if (skuinfo.mMRP == null) return "";
        if (skuinfo.mMRP <= skuinfo.mPrice) return "";

        return skuinfo.mMRP.toString();
    }

    public Float getDiscountValue(ProductSKU skuinfo)
    {
        if (skuinfo.mMRP == null) return null;
        if (skuinfo.mMRP <= skuinfo.mPrice) return null;

        //Float offAmnt = mMaxPrice-mSalePrice;
        Float discPerc = ((skuinfo.mMRP-skuinfo.mPrice)/skuinfo.mMRP) *100;

        return discPerc;
    }

    /*
    public void itemSelectionHandler(int position)
    {
        ProductSalesViewInfo oritem = mOrderItemList.get(position);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(oritem.getName()+": selected");
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
    }*/
}
