package com.superlight.kashingmerchant.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.selector.OnSettingEventListener;

public class SettingFragment extends Fragment implements View.OnClickListener{

    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }

    public void initView(View rootView){
        LinearLayout backView = (LinearLayout)rootView.findViewById(R.id.back_view);
        if(backView != null){
            backView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        ((OnSettingEventListener)activity).onBackViewPressed();
                    }catch (ClassCastException cce){

                    }
                }
            });
        }
        rootView.findViewById(R.id.btn_pos).setOnClickListener(this);
        rootView.findViewById(R.id.setting_view).setOnClickListener(this);
        rootView.findViewById(R.id.device_view).setOnClickListener(this);
        rootView.findViewById(R.id.users_view).setOnClickListener(this);
        rootView.findViewById(R.id.sale_tokens_view).setOnClickListener(this);
        rootView.findViewById(R.id.categories_view).setOnClickListener(this);
        rootView.findViewById(R.id.products_view).setOnClickListener(this);
        rootView.findViewById(R.id.logout_view).setOnClickListener(this);
        rootView.findViewById(R.id.sales_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pos:
                try {
                    ((OnSettingEventListener) activity).onPosPressed();
                }catch (ClassCastException cce){

                }
                break;
            case R.id.setting_view:
                try {
                    ((OnSettingEventListener) activity).onLayoutPressed(0);
                }catch (ClassCastException cce){

                }
                break;
            case R.id.device_view:
                try {
                    ((OnSettingEventListener) activity).onLayoutPressed(1);
                }catch (ClassCastException cce){

                }
                break;
            case R.id.users_view:
                try {
                    ((OnSettingEventListener) activity).onLayoutPressed(2);
                }catch (ClassCastException cce){

                }
                break;
            case R.id.sale_tokens_view:
                try {
                    ((OnSettingEventListener) activity).onLayoutPressed(3);
                }catch (ClassCastException cce){

                }
                break;
            case R.id.categories_view:
                try {
                    ((OnSettingEventListener) activity).onLayoutPressed(4);
                }catch (ClassCastException cce){

                }
                break;
            case R.id.products_view:
                try {
                    ((OnSettingEventListener) activity).onLayoutPressed(5);
                }catch (ClassCastException cce){

                }
                break;
            case R.id.logout_view:
                try {
                    ((OnSettingEventListener) activity).onLayoutPressed(6);
                }catch (ClassCastException cce){

                }
                break;
            case R.id.sales_view:
                try {
                    ((OnSettingEventListener) activity).onLayoutPressed(7);
                }catch (ClassCastException cce){

                }
                break;
        }
    }

}
