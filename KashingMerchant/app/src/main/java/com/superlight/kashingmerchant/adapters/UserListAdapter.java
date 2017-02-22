package com.superlight.kashingmerchant.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.superlight.kashingmerchant.R;

public class UserListAdapter extends BaseAdapter{

    private LayoutInflater layoutinflater;
    private Context context;
    private String[] userNames = {"Eneko T", "Eneko manager 2", "Jon Arrien"};

    public UserListAdapter(Context context){
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 3;
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
            convertView = layoutinflater.inflate(R.layout.list_item_user, parent, false);
            viewHolder.tvUserName = (TextView)convertView.findViewById(R.id.tv_user_name);
            viewHolder.tvUserType = (TextView)convertView.findViewById(R.id.tv_user_type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvUserName.setText(userNames[position]);
        return convertView;
    }

    static class ViewHolder{
        TextView tvUserName, tvUserType;
    }

}
