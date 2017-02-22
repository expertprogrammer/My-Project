package com.superlight.kashingmerchant.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.superlight.kashingmerchant.R;

public class SaleListAdapter extends BaseAdapter{

    private LayoutInflater layoutinflater;
    private Context context;

    public SaleListAdapter(Context context){
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 20;
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
            convertView = layoutinflater.inflate(R.layout.list_item_sale, parent, false);
            viewHolder.tvCustomerNum = (TextView)convertView.findViewById(R.id.tv_customer_num);
            viewHolder.tvDescription = (TextView)convertView.findViewById(R.id.tv_description);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }

    static class ViewHolder{
        TextView tvCustomerNum, tvDescription;
    }

}
