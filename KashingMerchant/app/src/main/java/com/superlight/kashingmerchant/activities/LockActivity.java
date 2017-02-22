package com.superlight.kashingmerchant.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.adapters.AccountGridAdapter;


public class LockActivity extends Activity {

    GridView accountsGridView;
    AccountGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        initView();
    }

    public void initView(){
        accountsGridView = (GridView)findViewById(R.id.accounts_grid);
        adapter = new AccountGridAdapter(this);
        accountsGridView.setAdapter(adapter);
        accountsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(LockActivity.this, PosActivity.class));
                finish();
            }
        });
    }

}
