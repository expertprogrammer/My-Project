package com.superlight.kashingmerchant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.utilities.CustomFontTextView;

import java.util.ArrayList;

public class IconGridAdapter extends BaseAdapter{

    private LayoutInflater layoutinflater;
    private Context context;
    private String[] iconsArray;

    public IconGridAdapter(Context context){
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        iconsArray = context.getResources().getStringArray(R.array.product_icons);
    }

    @Override
    public int getCount() {
        return iconsArray.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.grid_item_icon, parent, false);
            viewHolder.tvIconView = (CustomFontTextView)convertView.findViewById(R.id.tv_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvIconView.setText(iconsArray[position]);
        return convertView;
    }

    static class ViewHolder{
        CustomFontTextView tvIconView;
    }

}
