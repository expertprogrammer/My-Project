package com.superlight.kashingmerchant.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.superlight.kashingmerchant.R;

public class DeviceListAdapter extends BaseAdapter{

    private LayoutInflater layoutinflater;
    private Context context;
    private String[] deviceTypes = {"CASH REGISTERS", "CHIP & PIN DEVICES"};

    public DeviceListAdapter(Context context){
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 2;
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
            convertView = layoutinflater.inflate(R.layout.list_item_device, parent, false);
            viewHolder.tvDeviceType = (TextView)convertView.findViewById(R.id.tv_device_type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvDeviceType.setText(deviceTypes[position]);
        return convertView;
    }

    static class ViewHolder{
        TextView tvDeviceType;
    }

}
