package com.superlight.kashingmerchant.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.selector.OnPosEventListener;

public class PosFragment extends Fragment implements View.OnClickListener{

    FrameLayout contentFrame;
    Button btnProducts, btnManual;
    Fragment fragment;
    FragmentManager fragmentManager;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_pos, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity=activity;
    }

    public void initView(View rootView){
        ViewGroup backView = (ViewGroup)rootView.findViewById(R.id.back_view);
        if(backView != null){
            backView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        ((OnPosEventListener)activity).onBackViewPressed();
                    }catch (ClassCastException cce){

                    }
                }
            });
        }
        contentFrame = (FrameLayout)rootView.findViewById(R.id.content_frame);
        btnProducts = (Button)rootView.findViewById(R.id.btn_products);
        btnProducts.setOnClickListener(this);
        btnManual = (Button)rootView.findViewById(R.id.btn_manual);
        btnManual.setOnClickListener(this);
        fragment = new ProductsFragment();
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_manual:
                btnProducts.setTextColor(getResources().getColor(R.color.colorPrimary));
                btnProducts.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                btnManual.setTextColor(getResources().getColor(R.color.whiteColor));
                btnManual.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                fragment = new ManualFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                break;
            case R.id.btn_products:
                btnProducts.setTextColor(getResources().getColor(R.color.whiteColor));
                btnProducts.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btnManual.setTextColor(getResources().getColor(R.color.colorPrimary));
                btnManual.setBackgroundColor(getResources().getColor(R.color.whiteColor));
                fragment = new ProductsFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                break;
        }
    }

}
