package com.or2go.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.or2go.core.BaseCartItem;
import com.or2go.core.CartItem;
import com.or2go.core.CartItemView;
import com.or2go.core.ProductPriceInfo;
import com.or2go.core.ProductSKU;
import com.or2go.core.UnitManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

public class OrderCartItemAdapter extends RecyclerView.Adapter<OrderCartItemAdapter.CartItemViewHolder>{
    private Context mContext;
    //private List<Album> albumList;
    ArrayList<CartItemView> mItemList;

    Currency currency = Currency.getInstance("INR");
    UnitManager mUnitMgr = new UnitManager();
    int layout;
    String OR2GO_SERVER;
    RecyclerViewClickListener mListener;
    String StoreID;
    int InvControl;

    //private RequestQueue mRequestQueue;
    //ImageLoader mImageLoader;

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
//        void onLongClick(View view, int position);

    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecyclerViewClickListener mListener;

        TextView itemname;
        TextView itemprice;
        TextView itemquantity;
        TextView itemstksts;
        ImageView itemproImg;
        //ImageView itemdec;
        ImageView itemdel;
        LinearLayout linearLayout;
        //        Button    itemstkremove;
        ImageView itemstkremove;
        //added
//        CardView cardView;
//        Button linearLayoutCartView;
        //end

        public CartItemViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);

            itemname = (TextView) view.findViewById(R.id.orderitemname);
            itemprice = (TextView) view.findViewById(R.id.orderitemprice);
            itemquantity = (TextView)view.findViewById(R.id.orderitemqnty);
            itemproImg = (ImageView) view.findViewById(R.id.product_Image);
            //itemdec = (ImageView) view.findViewById(R.id.qntydecreasebt);
            itemdel = (ImageView) view.findViewById(R.id.btcartitemedit);
            linearLayout = (LinearLayout) view.findViewById(R.id.layoutshownotonstock);
            itemstksts = (TextView) view.findViewById(R.id.orderitemstksts);
//            itemstkremove= (Button)  view.findViewById(R.id.btremoveitemstk);
            itemstkremove= (ImageView)  view.findViewById(R.id.btremoveitemstk);
            //added
//            cardView = (CardView) view.findViewById(R.id.vendor_info_cardview);
//            linearLayoutCartView = (Button) view.findViewById(R.id.buttonvirw);
            //end
            mListener = listener;
            view.setOnClickListener(this);
            itemdel.setOnClickListener(this);
            itemstkremove.setOnClickListener(this);
//            cardView.setOnLongClickListener(this);
        }

        @Override

        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }

//        @Override
//        public boolean onLongClick(View v) {
//            mListener.onLongClick(v, getAdapterPosition());
//            return true;
//        }
    }

    public OrderCartItemAdapter(Context context, String server, String storeId, Integer invControl, ArrayList<CartItemView> itemList, int layout, RecyclerViewClickListener listener)
    {
        this.mContext = context;
        this.mItemList = itemList;
        this.OR2GO_SERVER = server;
        this.StoreID = storeId;
        this.InvControl = invControl;
        this.layout = layout;
        this.mListener = listener;


    }

    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(layout, parent, false);

        return new CartItemViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(final CartItemViewHolder holder, int position) {
        final int selpos = position;
        CartItemView item = mItemList.get(position);
        //holder.itemimage.setImageBitmap(item.getImage());
        //holder.itemimage.setImageBitmap(Bitmap.createScaledBitmap(item.getImage(), 96, 96, false));

        //added
//        holder.cardView.clearAnimation();
//        holder.linearLayoutCartView.clearAnimation();
//        Animation animation = AnimationUtils.loadAnimation(mContext,
//                (position == 0) ? R.anim.right_left_bounce: R.anim.no_animation);
//        animation.setRepeatCount(0);
//        holder.cardView.setAnimation(animation);
//        holder.linearLayoutCartView.setAnimation(animation);
        //end
        if(sendFloatValue(item.getItemTotal(item.getBCI())).equals("0.0"))
            holder.itemprice.setText(currency.getSymbol()+(int) item.getItemTotal(item.getBCI()));
        else
            holder.itemprice.setText(currency.getSymbol() + item.getItemTotal(item.getBCI()));

        //load image
        holder.itemproImg.setScaleType(ImageView.ScaleType.FIT_XY);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.blankitem)
                .error(R.drawable.blankitem);
        if(item.getImagePath() == 0){
            System.out.println(OR2GO_SERVER+"prodimage/"+prodNameToImagePath(item.getBrandName(), item.getName()) + ".jpg");
            Glide.with(mContext)
                    .load(OR2GO_SERVER+"prodimage/"+prodNameToImagePath(item.getBrandName(), item.getName()) + ".jpg")
                    .apply(options)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.itemproImg);
        }else if (item.getImagePath() == 1){
            System.out.println(OR2GO_SERVER+"vendorprodimage/"+StoreID+"/"+item.getId()+ ".jpg");
            Glide.with(mContext)
                    .load(OR2GO_SERVER+"vendorprodimage/"+StoreID+"/"+item.getId()+ ".jpg")
                    .apply(options)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.itemproImg);
        }else
            Toast.makeText(mContext, "No Product Image", Toast.LENGTH_SHORT).show();
        //ProductPriceInfo pkinfo = item.getPriceInfo();
        ProductSKU skuinfo = item.getSKUInfo();
        //if (item.isWholeItem())
        if (skuinfo !=null)
        {
            //holder.itemquantity.setText(item.getQnty() + mUnitMgr.getUnitName(item.getOrderUnit()));
            ///pkinfo.dumpInfo();
            if (item.isWholeItem()) {
                holder.itemname.setText(item.getName());
                holder.itemquantity.setText(currency.getSymbol() + item.getPrice().intValue() + " x " +item.getQnty());
            }
            else {
                if (sendFloatValue(item.getPrice()).equals("0.0")){
                    holder.itemquantity.setText(currency.getSymbol() + item.getPrice().intValue() + " x " + (int) Float.parseFloat(item.getQnty()));
                }else{
                    holder.itemquantity.setText(currency.getSymbol() + item.getPrice() + " x " + (int) Float.parseFloat(item.getQnty()));
                }
                holder.itemname.setText(item.getName() + " [" + skuinfo.mAmount.intValue() + mUnitMgr.getUnitName(skuinfo.mUnit) + "]");
            }
        }
        else
            holder.itemquantity.setText(item.getQnty());
        if ((InvControl > 0))
        {
            Float stkval = item.getCurStock();
            if (stkval == 0) {
                holder.linearLayout.setVisibility(View.VISIBLE);
                holder.itemstksts.setText("Item Out Of Stock");
                holder.itemstkremove.setVisibility(View.VISIBLE);
            }
            else if (stkval < item.getQntyVal()) {
                holder.linearLayout.setVisibility(View.VISIBLE);
                holder.itemstksts.setText("Only "+ (int) Math.round(stkval) +" "+ mUnitMgr.getUnitName(skuinfo.mUnit) +" available.");
                holder.itemstkremove.setVisibility(View.VISIBLE);
            }
            else {
                holder.linearLayout.setVisibility(View.GONE);
                holder.itemstksts.setText(stkval.toString());
                holder.itemstkremove.setVisibility(View.GONE);
            }
        }
        else
        {
            holder.linearLayout.setVisibility(View.GONE);
            holder.itemstksts.setText("");
            holder.itemstkremove.setVisibility(View.GONE);}

            /*
        String unit = item.getPriceUnit();
        if (unit.equals("Pc"))

        else
            holder.itemquantity.setText(item.getRetailsQnty().toString()+item.getOrderUnit());
        */

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
}
