package com.superlight.kashingmerchant.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.utilities.CustomFontTextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryListAdapter extends BaseAdapter{

    private LayoutInflater layoutinflater;
    private Context context;
    private int[] colorArray = {R.color.itemColor1, R.color.itemColor2, R.color.itemColor3, R.color.itemColor4,
            R.color.itemColor5, R.color.itemColor6, R.color.itemColor7, R.color.itemColor8, R.color.itemColor9,
            R.color.itemColor10, R.color.itemColor11, R.color.itemColor12, R.color.itemColor13, R.color.itemColor14, R.color.itemColor15};
    private String[] iconsArray;

    public CategoryListAdapter(Context context){
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        iconsArray = context.getResources().getStringArray(R.array.product_icons);
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
            convertView = layoutinflater.inflate(R.layout.list_item_category, parent, false);
            viewHolder.tvCategoryName = (TextView)convertView.findViewById(R.id.tv_category_name);
            viewHolder.imgCategoryBg = (CircleImageView)convertView.findViewById(R.id.iv_category_bg);
            viewHolder.categoryIcon = (CustomFontTextView)convertView.findViewById(R.id.category_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.categoryIcon.setText(iconsArray[position]);
        viewHolder.imgCategoryBg.setImageResource(colorArray[position]);
        return convertView;
    }

    static class ViewHolder{
        TextView tvCategoryName;
        CircleImageView imgCategoryBg;
        CustomFontTextView categoryIcon;
    }

}
