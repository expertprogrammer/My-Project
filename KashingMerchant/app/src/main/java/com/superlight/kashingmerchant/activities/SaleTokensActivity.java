package com.superlight.kashingmerchant.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.adapters.SaleTokenGridAdapter;


public class SaleTokensActivity extends Activity {

    GridView saleTokensGridView;
    SaleTokenGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_tokens);
        initView();
    }

    public void initView(){
        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        saleTokensGridView = (GridView)findViewById(R.id.sale_tokens_grid);
        adapter = new SaleTokenGridAdapter(this);
        saleTokensGridView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom);
    }

}
