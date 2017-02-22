package com.superlight.kashingmerchant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.superlight.kashingmerchant.R;

public class ColorListAdapter extends BaseAdapter{

    private LayoutInflater layoutinflater;
    private Context context;
    private int[] colorArray = {R.color.itemColor1, R.color.itemColor2, R.color.itemColor3, R.color.itemColor4,
            R.color.itemColor5, R.color.itemColor6, R.color.itemColor7, R.color.itemColor8, R.color.itemColor9,
            R.color.itemColor10, R.color.itemColor11, R.color.itemColor12, R.color.itemColor13, R.color.itemColor14, R.color.itemColor15};

    public ColorListAdapter(Context context){
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return colorArray.length;
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
            convertView = layoutinflater.inflate(R.layout.list_item_color, parent, false);
            viewHolder.ivColorView = (ImageView) convertView.findViewById(R.id.iv_color);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivColorView.setBackgroundColor(context.getResources().getColor(colorArray[position]));
        return convertView;
    }

    static class ViewHolder{
        ImageView ivColorView;
    }

}
