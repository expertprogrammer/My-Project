package com.superlight.kashingmerchant.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.adapters.CheckOutProductListAdapter;
import com.superlight.kashingmerchant.selector.OnPosEventListener;
import com.superlight.kashingmerchant.utilities.CustomFontTextView;


public class KashingFragment extends Fragment implements View.OnClickListener{

    Button btnLogout, btnCheckout;
    LinearLayout phoneFrame;
    CustomFontTextView outputField;
    ListView productsListView;
    CheckOutProductListAdapter adapter;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_kashing, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }

    public void initView(View rootView){
        phoneFrame = (LinearLayout)rootView.findViewById(R.id.phone_frame);
        if(phoneFrame != null){
            btnLogout = (Button)rootView.findViewById(R.id.btn_logout);
            btnLogout.setOnClickListener(this);
            btnCheckout = (Button)rootView.findViewById(R.id.btn_checkout);
            btnCheckout.setOnClickListener(this);
            rootView.findViewById(R.id.btn_c).setOnClickListener(this);
            rootView.findViewById(R.id.btn_0).setOnClickListener(this);
            rootView.findViewById(R.id.btn_00).setOnClickListener(this);
            rootView.findViewById(R.id.btn_1).setOnClickListener(this);
            rootView.findViewById(R.id.btn_2).setOnClickListener(this);
            rootView.findViewById(R.id.btn_3).setOnClickListener(this);
            rootView.findViewById(R.id.btn_4).setOnClickListener(this);
            rootView.findViewById(R.id.btn_5).setOnClickListener(this);
            rootView.findViewById(R.id.btn_6).setOnClickListener(this);
            rootView.findViewById(R.id.btn_7).setOnClickListener(this);
            rootView.findViewById(R.id.btn_8).setOnClickListener(this);
            rootView.findViewById(R.id.btn_9).setOnClickListener(this);
            rootView.findViewById(R.id.select_account).setOnClickListener(this);
            rootView.findViewById(R.id.product_view).setOnClickListener(this);
        }else{
            productsListView = (ListView)rootView.findViewById(R.id.products_list);
            adapter = new CheckOutProductListAdapter(getActivity());
            productsListView.setAdapter(adapter);
            productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        ((OnPosEventListener) activity).onTabletProductItemPressed();
                    }catch (ClassCastException cce){

                    }
                }
            });
            rootView.findViewById(R.id.change_sale).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        ((OnPosEventListener) activity).onTabletCheckOutPressed();
                    }catch (ClassCastException cce){

                    }
                }
            });
            rootView.findViewById(R.id.btn_lock).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        ((OnPosEventListener) activity).onTabletLockPressed();
                    }catch (ClassCastException cce){

                    }
                }
            });
        }
        rootView.findViewById(R.id.btn_setting).setOnClickListener(this);
        outputField = (CustomFontTextView)rootView.findViewById(R.id.tv_output_field);

    }

    @Override
    public void onClick(View v){
        String output = outputField.getText().toString();
        switch (v.getId()){
            case R.id.btn_logout:
                try{
                    ((OnPosEventListener) activity).onLogoutPressed();
                }catch (ClassCastException cce){

                }
                break;
            case R.id.btn_setting:
                try{
                    ((OnPosEventListener) activity).onSettingPressed();
                }catch (ClassCastException cee){

                }
                break;
            case R.id.btn_checkout:
                try{
                    ((OnPosEventListener) activity).onCheckPressed();
                }catch (ClassCastException cee){

                }
                break;
            case R.id.btn_c:
                outputField.setText("0.00");
                break;
            case R.id.btn_0:
                if (output.equals("enter...")){
                    outputField.setText("0.00");
                }else if (!output.equals("0.00")){
                    String newOutput = output + "0";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                break;
            case R.id.btn_00:
                if (output.equals("enter...")){
                    outputField.setText("0.00");
                }
                else if (!output.equals("0.00")){
                    String newOutput = output + "00";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 100));
                }
                break;
            case R.id.btn_1:
                if (output.equals("enter...") || output.equals("0.00")){
                    outputField.setText("0.01");
                }else {
                    String newOutput = output + "1";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                break;
            case R.id.btn_2:
                if (output.equals("enter...") || output.equals("0.00")){
                    outputField.setText("0.02");
                }else {
                    String newOutput = output + "2";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                break;
            case R.id.btn_3:
                if (output.equals("enter...") || output.equals("0.00")){
                    outputField.setText("0.03");
                }else {
                    String newOutput = output + "3";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                break;
            case R.id.btn_4:
                if (output.equals("enter...") || output.equals("0.00")){
                    outputField.setText("0.04");
                }else {
                    String newOutput = output + "4";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                break;
            case R.id.btn_5:
                if (output.equals("enter...") || output.equals("0.00")){
                    outputField.setText("0.05");
                }else {
                    String newOutput = output + "5";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                break;
            case R.id.btn_6:
                if (output.equals("enter...") || output.equals("0.00")){
                    outputField.setText("0.06");
                }else {
                    String newOutput = output + "6";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                break;
            case R.id.btn_7:
                if (output.equals("enter...") || output.equals("0.00")){
                    outputField.setText("0.07");
                }else {
                    String newOutput = output + "7";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                break;
            case R.id.btn_8:
                if (output.equals("enter...") || output.equals("0.00")){
                    outputField.setText("0.08");
                }else {
                    String newOutput = output + "8";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                break;
            case R.id.btn_9:
                if (output.equals("enter...") || output.equals("0.00")){
                    outputField.setText("0.09");
                }else {
                    String newOutput = output + "9";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                break;
            case R.id.select_account:
                try{
                    ((OnPosEventListener) activity).onSelectAccountPressed();
                }catch (ClassCastException cce){

                }
                break;
            case R.id.product_view:
                try{
                    ((OnPosEventListener) activity).onProductPressed();
                }catch (ClassCastException cce){

                }
                break;
        }
    }
}
