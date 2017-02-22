package com.superlight.kashingmerchant.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.fragments.KashingFragment;
import com.superlight.kashingmerchant.fragments.PosFragment;
import com.superlight.kashingmerchant.fragments.ProductsFragment;
import com.superlight.kashingmerchant.fragments.ProductsListFragment;
import com.superlight.kashingmerchant.fragments.SaleTokensFragment;
import com.superlight.kashingmerchant.selector.OnPosEventListener;
import com.superlight.kashingmerchant.utilities.CustomFontTextView;



public class PosActivity extends Activity implements OnPosEventListener{

    FrameLayout kashingFrame, posFrame;
    public static int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        initView();
    }

    public void initView(){
        kashingFrame = (FrameLayout)findViewById(R.id.kashing_frame);
        posFrame = (FrameLayout)findViewById(R.id.pos_frame);

        // In case phone //
        if(kashingFrame != null){
            if (flag == 0){
                KashingFragment kashingFragment= new KashingFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(kashingFrame.getId(), kashingFragment,
                        KashingFragment.class.getName());
                // Commit the transaction
                fragmentTransaction.commit();
            }else{
                PosFragment posFragment = new PosFragment();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(kashingFrame.getId(), posFragment,
                        PosFragment.class.getName());
                // Commit the transaction
                fragmentTransaction.commit();
            }
        }

        // In case tablet //
        if(posFrame != null){
            PosFragment posFragment = new PosFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(posFrame.getId(), posFragment,
                    PosFragment.class.getName());
            // Commit the transaction
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onLogoutPressed(){
        startActivity(new Intent(PosActivity.this, SigninActivity.class));
        finish();
    }

    @Override
    public void onSettingPressed(){
        startActivity(new Intent(PosActivity.this, SettingActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackViewPressed(){
        onBackPressed();
    }

    @Override
    public void onCheckPressed(){
        if(posFrame == null){
            startActivity(new Intent(PosActivity.this, CheckoutActivity.class));
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        }
    }

    @Override
    public void onSelectAccountPressed(){
        SaleTokensFragment saleTokensFragment= new SaleTokensFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(kashingFrame.getId(), saleTokensFragment,
                SaleTokensFragment.class.getName());
        // Commit the transaction
        fragmentTransaction.commit();
    }

    @Override
    public void onProductPressed(){
        ProductsListFragment productsListFragment= new ProductsListFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(kashingFrame.getId(), productsListFragment,
                ProductsListFragment.class.getName());
        // Commit the transaction
        fragmentTransaction.commit();
    }

    @Override
    public void onRootBackViewPressed(){
        KashingFragment kashingFragment= new KashingFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(kashingFrame.getId(), kashingFragment,
                KashingFragment.class.getName());
        // Commit the transaction
        fragmentTransaction.commit();
    }

    @Override
    public void onTabletCheckOutPressed(){
        startActivity(new Intent(PosActivity.this, SaleTokensActivity.class));
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    @Override
    public void onTabletProductItemPressed(){
        popUpDialog();
    }

    @Override
    public void onTabletLockPressed(){
        startActivity(new Intent(PosActivity.this, LockActivity.class));
    }


    public void popUpDialog(){
        final AlertDialog dialog = new AlertDialog.Builder(this, R.style.dialog_style).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_add_product);

        //get display width//
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        window.setLayout(width - 100, 5 * height / 6);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        dialog.getWindow().setAttributes(layoutParams);

        window.findViewById(R.id.btn_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final CustomFontTextView countField = (CustomFontTextView)window.findViewById(R.id.tv_count);

        window.findViewById(R.id.btn_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.valueOf(countField.getText().toString());
                count += 1;
                countField.setText(String.valueOf(count));
            }
        });
        window.findViewById(R.id.btn_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.valueOf(countField.getText().toString());
                count -= 1;
                countField.setText(String.valueOf(count));
            }
        });

    }

}
