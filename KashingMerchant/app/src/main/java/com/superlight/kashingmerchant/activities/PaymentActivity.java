package com.superlight.kashingmerchant.activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.utilities.CustomFontTextView;


public class PaymentActivity extends Activity implements View.OnClickListener{

    CustomFontTextView outputField;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initView();
    }

    public void initView(){
        outputField = (CustomFontTextView)findViewById(R.id.tv_output_field);
        findViewById(R.id.back_view).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);

        findViewById(R.id.btn_c).setOnClickListener(this);
        findViewById(R.id.btn_0).setOnClickListener(this);
        findViewById(R.id.btn_00).setOnClickListener(this);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        findViewById(R.id.btn_5).setOnClickListener(this);
        findViewById(R.id.btn_6).setOnClickListener(this);
        findViewById(R.id.btn_7).setOnClickListener(this);
        findViewById(R.id.btn_8).setOnClickListener(this);
        findViewById(R.id.btn_9).setOnClickListener(this);
        findViewById(R.id.btn_5_00).setOnClickListener(this);
        findViewById(R.id.btn_10_00).setOnClickListener(this);
        findViewById(R.id.btn_20_00).setOnClickListener(this);
        findViewById(R.id.btn_50_00).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        String output = outputField.getText().toString();
        switch (v.getId()){
            case R.id.back_view:
                onBackPressed();
                break;
            case R.id.btn_cancel:
                onBackPressed();
                break;
            case R.id.btn_c:
                outputField.setText("0.00");
                outputField.setTextColor(getResources().getColor(R.color.greenDarkColor));
                break;
            case R.id.btn_0:
                if (output.equals("please insert card")){
                    outputField.setText("0.00");
                }else if (!output.equals("0.00")){
                    String newOutput = output + "0";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                outputField.setTextColor(getResources().getColor(R.color.greenDarkColor));
                break;
            case R.id.btn_00:
                if (output.equals("please insert card")){
                    outputField.setText("0.00");
                }
                else if (!output.equals("0.00")){
                    String newOutput = output + "00";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 100));
                }
                outputField.setTextColor(getResources().getColor(R.color.greenDarkColor));
                break;
            case R.id.btn_1:
                if (output.equals("please insert card") || output.equals("0.00")){
                    outputField.setText("0.01");
                }else {
                    String newOutput = output + "1";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                outputField.setTextColor(getResources().getColor(R.color.greenDarkColor));
                break;
            case R.id.btn_2:
                if (output.equals("please insert card") || output.equals("0.00")){
                    outputField.setText("0.02");
                }else {
                    String newOutput = output + "2";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                outputField.setTextColor(getResources().getColor(R.color.greenDarkColor));
                break;
            case R.id.btn_3:
                if (output.equals("please insert card.") || output.equals("0.00")){
                    outputField.setText("0.03");
                }else {
                    String newOutput = output + "3";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                break;
            case R.id.btn_4:
                if (output.equals("please insert card") || output.equals("0.00")){
                    outputField.setText("0.04");
                }else {
                    String newOutput = output + "4";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                outputField.setTextColor(getResources().getColor(R.color.greenDarkColor));
                break;
            case R.id.btn_5:
                if (output.equals("please insert card") || output.equals("0.00")){
                    outputField.setText("0.05");
                }else {
                    String newOutput = output + "5";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                outputField.setTextColor(getResources().getColor(R.color.greenDarkColor));
                break;
            case R.id.btn_6:
                if (output.equals("please insert card") || output.equals("0.00")){
                    outputField.setText("0.06");
                }else {
                    String newOutput = output + "6";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                outputField.setTextColor(getResources().getColor(R.color.greenDarkColor));
                break;
            case R.id.btn_7:
                if (output.equals("please insert card") || output.equals("0.00")){
                    outputField.setText("0.07");
                }else {
                    String newOutput = output + "7";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                outputField.setTextColor(getResources().getColor(R.color.greenDarkColor));
                break;
            case R.id.btn_8:
                if (output.equals("please insert card") || output.equals("0.00")){
                    outputField.setText("0.08");
                }else {
                    String newOutput = output + "8";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                outputField.setTextColor(getResources().getColor(R.color.greenDarkColor));
                break;
            case R.id.btn_9:
                if (output.equals("please insert card") || output.equals("0.00")){
                    outputField.setText("0.09");
                }else {
                    String newOutput = output + "9";
                    outputField.setText(String.format("%.2f", Float.valueOf(newOutput) * 10));
                }
                outputField.setTextColor(getResources().getColor(R.color.greenDarkColor));
                break;
            case R.id.btn_5_00:
                outputField.setText("5.00");
                outputField.setTextColor(getResources().getColor(R.color.greenDarkColor));
                break;
            case R.id.btn_10_00:
                outputField.setText("10.00");
                outputField.setTextColor(getResources().getColor(R.color.greenDarkColor));
                break;
            case R.id.btn_20_00:
                outputField.setText("20.00");
                outputField.setTextColor(getResources().getColor(R.color.greenDarkColor));
                break;
            case R.id.btn_50_00:
                outputField.setText("50.00");
                outputField.setTextColor(getResources().getColor(R.color.greenDarkColor));
                break;
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
