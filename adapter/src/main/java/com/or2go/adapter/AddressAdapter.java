package com.or2go.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.or2go.core.CustomerAddressModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> implements Filterable {

    private final PlaceAutoCompleteInterface PlaceAutoCompleteInterface;
    private String key;
    private String uri;
    private int layout;
    private Context context;
    private ArrayList<CustomerAddressModel> customerAddressModelArrayList;
    private CardView cardView;

    public interface PlaceAutoCompleteInterface{
        void onLocationClick(String name, String description, int position);
    }

    public AddressAdapter(Context context, int layout, String key, String uri, CardView cardView) {
        this.layout = layout;
        this.context = context;
        this.key = key;
        this.uri = uri;
        this.PlaceAutoCompleteInterface = (PlaceAutoCompleteInterface) context;
        this.cardView = cardView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CustomerAddressModel model = customerAddressModelArrayList.get(position);
        holder.addressName.setText(model.placeName);
        holder.addressAddress.setText(model.placeAddress);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceAutoCompleteInterface.onLocationClick(model.placeName, model.placeAddress, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (customerAddressModelArrayList != null){
            return customerAddressModelArrayList.size();
        }else{
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null){
                    customerAddressModelArrayList = autoComplete((String) constraint);
                    if (customerAddressModelArrayList != null){
//                        Toast.makeText(context, "Lala "+customerAddressModelArrayList.size(), Toast.LENGTH_SHORT).show();
                        filterResults.values = customerAddressModelArrayList;
                        filterResults.count = customerAddressModelArrayList.size();
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0){
                    notifyDataSetChanged();
                }
                else{
                    customerAddressModelArrayList = null;
                    notifyDataSetChanged();
                    cardView.setVisibility(View.GONE);
                    Toast.makeText(context, "no result from API.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        return filter;
    }

    private ArrayList<CustomerAddressModel> autoComplete(String constraint) {
        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            StringBuilder builder = new StringBuilder(uri);
            builder.append("?components=country:in");
            builder.append("&input=").append(constraint);
            builder.append("&key=").append(key);
            URL url = new URL(builder.toString());
            connection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            int read;
            char[] chars = new char[1024];
            while ((read = inputStreamReader.read(chars)) != -1){
                stringBuilder.append(chars, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.disconnect();
            }
        }
        //creating JSONObject
        try {
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("predictions");
            customerAddressModelArrayList = new ArrayList<>(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++){
                String desc = jsonArray.getJSONObject(i).getString("description");
                String [] twoStringArray= desc.split(",", 2);
                customerAddressModelArrayList.add(new CustomerAddressModel(i,twoStringArray[0], twoStringArray[1]));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return customerAddressModelArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView addressName, addressAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rlAddressHolder);
            addressName = (TextView) itemView.findViewById(R.id.tvLocationName);
            addressAddress = (TextView) itemView.findViewById(R.id.tvLocationAddress);
        }
    }
}
