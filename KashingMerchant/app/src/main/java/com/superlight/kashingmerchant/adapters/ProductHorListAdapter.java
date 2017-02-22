package com.superlight.kashingmerchant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.superlight.kashingmerchant.R;

public class ProductHorListAdapter extends BaseAdapter{

    private LayoutInflater layoutinflater;
    private Context context;
    private String[] productNames = {"Double Rooms", "Family Rooms", "Single Rooms", "Greek Things", "Pork", "Salads", "Starter", "Vegans"};
    private int[] colorList = {R.color.itemColor1, R.color.itemColor2, R.color.itemColor3, R.color.itemColor4,
            R.color.itemColor5, R.color.itemColor6, R.color.itemColor7, R.color.itemColor8};

    public ProductHorListAdapter(Context context){
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return productNames.length;
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
            convertView = layoutinflater.inflate(R.layout.list_item_hor_product, parent, false);
            viewHolder.tvProductName = (TextView)convertView.findViewById(R.id.tv_product_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.tvProductName.setText(productNames[position]);
        viewHolder.tvProductName.setTextColor(context.getResources().getColor(colorList[position]));
        return convertView;
    }

    static class ViewHolder{
        TextView tvProductName;
    }

}
