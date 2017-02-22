package com.superlight.kashingmerchant.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.selector.OnSettingEventListener;

public class GeneralSettingFragment extends Fragment implements View.OnClickListener{

    RelativeLayout pickerView;
    TextView tvCompanyType;
    boolean pickerVisible = false;

    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_general_setting, container, false);
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
            backView.setOnClickListener(this);
        }
        rootView.findViewById(R.id.company_type_view).setOnClickListener(this);
        tvCompanyType = (TextView)rootView.findViewById(R.id.tv_company_type);
        pickerView = (RelativeLayout)rootView.findViewById(R.id.picker_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_view:
                try{
                    ((OnSettingEventListener)activity).onRootBackViewPressed();
                }catch (ClassCastException cce){

                }
                break;
            case R.id.company_type_view:
                if(!pickerVisible){
                    pickerView.setVisibility(View.VISIBLE);
                    pickerVisible = true;
                }else{
                    pickerView.setVisibility(View.GONE);
                    pickerVisible = false;
                }
                break;
        }
    }

}
