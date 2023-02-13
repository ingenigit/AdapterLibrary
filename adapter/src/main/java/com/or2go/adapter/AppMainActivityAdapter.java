package com.or2go.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppMainActivityAdapter extends BaseAdapter {
    private Context context;
    private List<String> listTagInfo;
//    private List<ServiceTagInfo> listTagInfo;
//    AppMainActivityAdapter.HandleClickListener handleClickListener;
//    LayoutInflater inflter;
    private int layout;
//    public  interface HandleClickListener{
//        void onCategoryClick(View view, int position);
//    }

    public AppMainActivityAdapter(Context context, List<String> listTagInfo, int layout) {
        this.context = context;
        this.listTagInfo = listTagInfo;
        this.layout = layout;
//        this.handleClickListener = handleClickListener;
//        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return listTagInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        convertView = inflter.inflate(R.layout.categories_item, null);
        View listView = convertView;
        if (listView == null){
            listView = LayoutInflater.from(context).inflate(layout, parent, false);
        }

        ImageView catImage = (ImageView) listView.findViewById(R.id.catImg);
        TextView catName = (TextView) listView.findViewById(R.id.catname);

//        catImage.setImageBitmap(listTagInfo.get(position).getImg());
        catName.setText(listTagInfo.get(position));

//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int width = displayMetrics.widthPixels;
//        catName.getLayoutParams().height = width/3;

        return listView;
    }
}
