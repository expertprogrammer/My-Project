package com.superlight.kashingmerchant.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.adapters.SaleListAdapter;
import com.superlight.kashingmerchant.selector.OnSettingEventListener;

public class SalesFragment extends Fragment implements View.OnClickListener{

    ListView salesList;
    SaleListAdapter adapter;

    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_sales, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }

    public void initView(View rootView) {
        LinearLayout backView = (LinearLayout) rootView.findViewById(R.id.back_view);
        if (backView != null) {
            backView.setOnClickListener(this);
        }
        salesList = (ListView)rootView.findViewById(R.id.sales_list);
        adapter = new SaleListAdapter(getActivity());
        salesList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_view:
                try{
                    ((OnSettingEventListener)activity).onRootBackViewPressed();
                }catch (ClassCastException cce){

                }
                break;
        }
    }

}
