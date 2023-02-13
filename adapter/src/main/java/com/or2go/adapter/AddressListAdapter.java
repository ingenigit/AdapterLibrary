package com.or2go.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.or2go.core.DeliveryAddrInfo;

import java.util.ArrayList;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressListViewHolder>{
    private Context mContext;

    ArrayList<DeliveryAddrInfo> mAddressList;
    int layout;
    RecyclerViewClickListener mListener;

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);

    }

    public class AddressListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecyclerViewClickListener mListener;
        public TextView addrname, address, landmark, place, pin, contact;
        public ImageView orderckout, itemedit;
        public Button btEdit, btDelete;

        public AddressListViewHolder(View view, RecyclerViewClickListener listener) {
            super(view);
            addrname = (TextView) view.findViewById(R.id.addrname);
            address = (TextView) view.findViewById(R.id.address);
            landmark = (TextView) view.findViewById(R.id.landmark);
            place = (TextView) view.findViewById(R.id.place);
            contact = (TextView) view.findViewById(R.id.contact);
            pin = (TextView) view.findViewById(R.id.pin);
            btEdit = (Button)view.findViewById(R.id.btAddrEdit);
            btDelete = (Button)view.findViewById(R.id.btAddrDelete);
            mListener = listener;
            view.setOnClickListener(this);
            btDelete.setOnClickListener(this);
            btEdit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            mListener.onClick(view, getAdapterPosition());

        }
    }


    public AddressListAdapter(Context context, ArrayList<DeliveryAddrInfo> orderList, int layout, RecyclerViewClickListener listener)
    {
        this.mContext = context;
        this.mAddressList = orderList;
        this.layout = layout;
        this.mListener = listener;
    }

    @Override
    public AddressListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(layout, parent, false);

        return new AddressListViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(final AddressListViewHolder holder, int position) {
        //Log.i("OrderListAdapter"," order position = "+position);
        final int selpos = position;
        DeliveryAddrInfo oritem = mAddressList.get(position);
        //Integer orid = oritem.getId();
        holder.addrname.setText(oritem.getAddrName());
        //System.out.println("OrderDetails : Order status="+oritem.oStatus+ "  text="+oritem.getStatusText());
        holder.address.setText(oritem.getAddress());
        holder.landmark.setText(oritem.getLandmark());
        holder.place.setText(oritem.getPlace());
        holder.contact.setText(oritem.getAltcontact());
        holder.pin.setText(oritem.getZipCode());
    }

    @Override
    public int getItemCount() {
        return mAddressList.size();
    }

    public boolean updateList(ArrayList<DeliveryAddrInfo> addrList)
    {
        mAddressList = addrList;
        return true;
    }
}
