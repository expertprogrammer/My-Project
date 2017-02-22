package com.superlight.kashingmerchant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.superlight.kashingmerchant.R;


public class ProductListAdapter extends BaseAdapter{

    private LayoutInflater layoutinflater;
    private Context context;

    public ProductListAdapter(Context context){
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 15;
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
            convertView = layoutinflater.inflate(R.layout.list_item_product, parent, false);
            viewHolder.tvProductName = (TextView)convertView.findViewById(R.id.tv_product_name);
            viewHolder.ivProduct = (ImageView)convertView.findViewById(R.id.iv_product);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    static class ViewHolder{
        TextView tvProductName;
        ImageView ivProduct;
    }

}
