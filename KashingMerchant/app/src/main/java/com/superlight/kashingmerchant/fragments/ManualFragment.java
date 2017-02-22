package com.superlight.kashingmerchant.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.utilities.CustomFontTextView;

public class ManualFragment extends Fragment implements View.OnClickListener{

    CustomFontTextView outputField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_manual, container, false);
        initView(rootView);
        return rootView;
    }

    public void initView(View rootView){
        outputField = (CustomFontTextView)rootView.findViewById(R.id.tv_output_field);
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
    }

    @Override
    public void onClick(View v){
        String output = outputField.getText().toString();
        switch (v.getId()){
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
        }
    }

}
