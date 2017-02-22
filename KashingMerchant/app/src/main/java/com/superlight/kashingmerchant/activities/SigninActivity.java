package com.superlight.kashingmerchant.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.utilities.CustomEditText;

import java.io.IOException;
import java.net.URL;

public class SigninActivity extends Activity implements View.OnClickListener{

    RelativeLayout loginView, forgotPassView, signupView;
    CustomEditText usernameField, passField;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initView();

    }

    public void initView(){
        usernameField = (CustomEditText)findViewById(R.id.et_username);
        int pos1 = usernameField.getText().length();
        usernameField.setSelection(pos1);
        passField = (CustomEditText)findViewById(R.id.et_password);
        passField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int pos = passField.getText().length();
                passField.setSelection(pos);
            }
        });
        loginView = (RelativeLayout)findViewById(R.id.login);
        loginView.setOnClickListener(this);
        forgotPassView = (RelativeLayout)findViewById(R.id.forgot_pass);
        forgotPassView.setOnClickListener(this);
        signupView = (RelativeLayout)findViewById(R.id.signup);
        signupView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.signup:
                startActivity(new Intent(SigninActivity.this, Welcome1Activity.class));
                SigninActivity.this.overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                finish();
                break;
            case R.id.login:
                PosActivity.flag = 0;
                startActivity(new Intent(SigninActivity.this, PosActivity.class));
                break;
        }
    }
}
