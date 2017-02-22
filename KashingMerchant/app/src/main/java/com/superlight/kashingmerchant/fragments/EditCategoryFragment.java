package com.superlight.kashingmerchant.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.selector.OnSettingEventListener;

public class EditCategoryFragment extends Fragment implements View.OnClickListener{

    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_edit_category, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }

    public void initView(View rootView){
        rootView.findViewById(R.id.back_view).setOnClickListener(this);
        rootView.findViewById(R.id.color_view).setOnClickListener(this);
        rootView.findViewById(R.id.icon_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_view:
                try{
                    ((OnSettingEventListener)activity).onBackCategoriesPressed();
                }catch (ClassCastException cce){

                }
                break;
            case R.id.color_view:
                try{
                    ((OnSettingEventListener)activity).onPickColorPressed();
                }catch (ClassCastException cce){

                }
                break;
            case R.id.icon_view:
                try{
                    ((OnSettingEventListener)activity).onPickIconPressed();
                }catch (ClassCastException cce){

                }
                break;
        }
    }

}
