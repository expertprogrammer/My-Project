package com.superlight.kashingmerchant.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.adapters.CheckOutProductListAdapter;

public class CheckoutActivity extends Activity implements View.OnClickListener{

    ListView productsListView;
    CheckOutProductListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        initView();
    }

    public void initView(){
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_purchase).setOnClickListener(this);
        productsListView = (ListView)findViewById(R.id.products_list);
        adapter = new CheckOutProductListAdapter(this);
        productsListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_cancel:
                onBackPressed();
                break;
            case R.id.btn_purchase:
                startActivity(new Intent(CheckoutActivity.this, PaymentActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
    }

}
