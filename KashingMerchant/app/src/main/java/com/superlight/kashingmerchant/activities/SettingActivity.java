package com.superlight.kashingmerchant.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.fragments.AddProductFragment;
import com.superlight.kashingmerchant.fragments.AddUserFragment;
import com.superlight.kashingmerchant.fragments.CategoriesFragment;
import com.superlight.kashingmerchant.fragments.DevicesFragment;
import com.superlight.kashingmerchant.fragments.EditCategoryFragment;
import com.superlight.kashingmerchant.fragments.GeneralSettingFragment;
import com.superlight.kashingmerchant.fragments.PickColorFragment;
import com.superlight.kashingmerchant.fragments.PickIconFragment;
import com.superlight.kashingmerchant.fragments.ProductsListFragment;
import com.superlight.kashingmerchant.fragments.SaleTokensFragment;
import com.superlight.kashingmerchant.fragments.SalesFragment;
import com.superlight.kashingmerchant.fragments.SettingFragment;
import com.superlight.kashingmerchant.fragments.UsersFragment;

import com.superlight.kashingmerchant.selector.OnSettingEventListener;

public class SettingActivity extends Activity implements OnSettingEventListener{

    FrameLayout settingFrame, rootFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    public void initView(){
        settingFrame = (FrameLayout)findViewById(R.id.setting_frame);
        rootFrame = (FrameLayout)findViewById(R.id.root_frame);
        if(settingFrame != null){
            SettingFragment settingFragment= new SettingFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(settingFrame.getId(), settingFragment,
                    SettingFragment.class.getName());
            fragmentTransaction.commit();
        }
        if(rootFrame != null){
            GeneralSettingFragment generalSettingFragment = new GeneralSettingFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(rootFrame.getId(), generalSettingFragment,
                    GeneralSettingFragment.class.getName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onPosPressed(){
        if(rootFrame != null) PosActivity.flag = 0;
        else PosActivity.flag = 1;
        startActivity(new Intent(SettingActivity.this, PosActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackViewPressed(){
        onBackPressed();
    }

    @Override
    public void onLayoutPressed(int position){
        if(rootFrame != null) {
            switch (position) {
                case 0:
                    GeneralSettingFragment generalSettingFragment = new GeneralSettingFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(rootFrame.getId(), generalSettingFragment,
                            GeneralSettingFragment.class.getName());
                    fragmentTransaction.commit();
                    break;
                case 1:
                    DevicesFragment devicesFragment = new DevicesFragment();
                    FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(rootFrame.getId(), devicesFragment,
                            DevicesFragment.class.getName());
                    fragmentTransaction1.commit();
                    break;
                case 2:
                    UsersFragment usersFragment = new UsersFragment();
                    FragmentTransaction fragmentTransaction2 = getFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(rootFrame.getId(), usersFragment,
                            UsersFragment.class.getName());
                    fragmentTransaction2.commit();
                    break;
                case 3:
                    SaleTokensFragment saleTokensFragment = new SaleTokensFragment();
                    FragmentTransaction fragmentTransaction3 = getFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(rootFrame.getId(), saleTokensFragment,
                            SaleTokensFragment.class.getName());
                    fragmentTransaction3.commit();
                    break;
                case 4:
                    CategoriesFragment categoriesFragment = new CategoriesFragment();
                    FragmentTransaction fragmentTransaction4 = getFragmentManager().beginTransaction();
                    fragmentTransaction4.replace(rootFrame.getId(), categoriesFragment,
                            CategoriesFragment.class.getName());
                    fragmentTransaction4.commit();
                    break;
                case 5:
                    ProductsListFragment productsListFragment = new ProductsListFragment();
                    FragmentTransaction fragmentTransaction5 = getFragmentManager().beginTransaction();
                    fragmentTransaction5.replace(rootFrame.getId(), productsListFragment,
                            ProductsListFragment.class.getName());
                    fragmentTransaction5.commit();
                    break;
                case 6:
                    startActivity(new Intent(SettingActivity.this, SigninActivity.class));
                    finish();
                    break;
                case 7:
                    SalesFragment salesFragment = new SalesFragment();
                    FragmentTransaction fragmentTransaction7 = getFragmentManager().beginTransaction();
                    fragmentTransaction7.replace(rootFrame.getId(), salesFragment,
                            SalesFragment.class.getName());
                    fragmentTransaction7.commit();
                    break;
            }
        }
        else {
            switch (position) {
                case 0:
                    GeneralSettingFragment generalSettingFragment = new GeneralSettingFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(settingFrame.getId(), generalSettingFragment,
                            GeneralSettingFragment.class.getName());
                    fragmentTransaction.commit();
                    break;
                case 1:
                    DevicesFragment devicesFragment = new DevicesFragment();
                    FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
                    fragmentTransaction1.replace(settingFrame.getId(), devicesFragment,
                            DevicesFragment.class.getName());
                    fragmentTransaction1.commit();
                    break;
                case 2:
                    UsersFragment usersFragment = new UsersFragment();
                    FragmentTransaction fragmentTransaction2 = getFragmentManager().beginTransaction();
                    fragmentTransaction2.replace(settingFrame.getId(), usersFragment,
                            UsersFragment.class.getName());
                    fragmentTransaction2.commit();
                    break;
                case 3:
                    SaleTokensFragment saleTokensFragment = new SaleTokensFragment();
                    FragmentTransaction fragmentTransaction3 = getFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(settingFrame.getId(), saleTokensFragment,
                            SaleTokensFragment.class.getName());
                    fragmentTransaction3.commit();
                    break;
                case 4:
                    CategoriesFragment categoriesFragment = new CategoriesFragment();
                    FragmentTransaction fragmentTransaction4 = getFragmentManager().beginTransaction();
                    fragmentTransaction4.replace(settingFrame.getId(), categoriesFragment,
                            CategoriesFragment.class.getName());
                    fragmentTransaction4.commit();
                    break;
                case 5:
                    ProductsListFragment productsListFragment = new ProductsListFragment();
                    FragmentTransaction fragmentTransaction5 = getFragmentManager().beginTransaction();
                    fragmentTransaction5.replace(settingFrame.getId(), productsListFragment,
                            ProductsListFragment.class.getName());
                    fragmentTransaction5.commit();
                    break;
                case 6:
                    startActivity(new Intent(SettingActivity.this, SigninActivity.class));
                    finish();
                    break;
                case 7:
                    SalesFragment salesFragment = new SalesFragment();
                    FragmentTransaction fragmentTransaction7 = getFragmentManager().beginTransaction();
                    fragmentTransaction7.replace(settingFrame.getId(), salesFragment,
                            SalesFragment.class.getName());
                    fragmentTransaction7.commit();
                    break;
            }
        }
    }

    @Override
    public void onUsersListItemSelected(int position){
        if(rootFrame != null){
            AddUserFragment addUserFragment = new AddUserFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(rootFrame.getId(), addUserFragment,
                    AddUserFragment.class.getName());
            fragmentTransaction.commit();
        }else{
            AddUserFragment addUserFragment = new AddUserFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(settingFrame.getId(), addUserFragment,
                    AddUserFragment.class.getName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackUsersPressed(){
        if(rootFrame != null){
            UsersFragment usersFragment = new UsersFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(rootFrame.getId(), usersFragment,
                    UsersFragment.class.getName());
            fragmentTransaction.commit();
        }else{
            UsersFragment usersFragment = new UsersFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(settingFrame.getId(), usersFragment,
                    UsersFragment.class.getName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onAddNewUserPressed(){
        if(rootFrame != null){
            AddUserFragment addUserFragment = new AddUserFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(rootFrame.getId(), addUserFragment,
                    AddUserFragment.class.getName());
            fragmentTransaction.commit();
        }else{
            AddUserFragment addUserFragment = new AddUserFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(settingFrame.getId(), addUserFragment,
                    AddUserFragment.class.getName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onRootBackViewPressed(){
        SettingFragment settingFragment= new SettingFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(settingFrame.getId(), settingFragment,
                SettingFragment.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    public void onProductsListItemSelected(int position){
        if(rootFrame != null){
            AddProductFragment addProductFragment = new AddProductFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(rootFrame.getId(), addProductFragment,
                    AddProductFragment.class.getName());
            fragmentTransaction.commit();
        }else{
            AddProductFragment addProductFragment = new AddProductFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(settingFrame.getId(), addProductFragment,
                    AddProductFragment.class.getName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onAddNewProductPressed(){
        if(rootFrame != null){
            AddProductFragment addProductFragment = new AddProductFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(rootFrame.getId(), addProductFragment,
                    AddProductFragment.class.getName());
            fragmentTransaction.commit();
        }else{
            AddProductFragment addProductFragment = new AddProductFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(settingFrame.getId(), addProductFragment,
                    AddProductFragment.class.getName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackProductsPressed(){
        if(rootFrame != null){
            ProductsListFragment productsListFragment = new ProductsListFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(rootFrame.getId(), productsListFragment,
                    ProductsListFragment.class.getName());
            fragmentTransaction.commit();
        }else{
            ProductsListFragment productsListFragment = new ProductsListFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(settingFrame.getId(), productsListFragment,
                    ProductsListFragment.class.getName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackCategoriesPressed(){
        if(rootFrame != null){
            CategoriesFragment categoriesFragment = new CategoriesFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(rootFrame.getId(), categoriesFragment,
                    CategoriesFragment.class.getName());
            fragmentTransaction.commit();
        }else{
            CategoriesFragment categoriesFragment = new CategoriesFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(settingFrame.getId(), categoriesFragment,
                    CategoriesFragment.class.getName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onAddNewCategoryPressed(){
        if(rootFrame != null){
            EditCategoryFragment editCategoryFragment = new EditCategoryFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(rootFrame.getId(), editCategoryFragment,
                    EditCategoryFragment.class.getName());
            fragmentTransaction.commit();
        }else{
            EditCategoryFragment editCategoryFragment = new EditCategoryFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(settingFrame.getId(), editCategoryFragment,
                    EditCategoryFragment.class.getName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onCategoriesListItemSelected(int position){
        if(rootFrame != null){
            EditCategoryFragment editCategoryFragment = new EditCategoryFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(rootFrame.getId(), editCategoryFragment,
                    EditCategoryFragment.class.getName());
            fragmentTransaction.commit();
        }else{
            EditCategoryFragment editCategoryFragment = new EditCategoryFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(settingFrame.getId(), editCategoryFragment,
                    EditCategoryFragment.class.getName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackEditCategoryPressed(){
        if(rootFrame != null){
            EditCategoryFragment editCategoryFragment = new EditCategoryFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(rootFrame.getId(), editCategoryFragment,
                    EditCategoryFragment.class.getName());
            fragmentTransaction.commit();
        }else{
            EditCategoryFragment editCategoryFragment = new EditCategoryFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(settingFrame.getId(), editCategoryFragment,
                    EditCategoryFragment.class.getName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onPickColorPressed(){
        if(rootFrame != null){
            PickColorFragment pickColorFragment = new PickColorFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(rootFrame.getId(), pickColorFragment,
                    PickColorFragment.class.getName());
            fragmentTransaction.commit();
        }else{
            PickColorFragment pickColorFragment = new PickColorFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(settingFrame.getId(), pickColorFragment,
                    PickColorFragment.class.getName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onPickIconPressed(){
        if(rootFrame != null){
            PickIconFragment pickIconFragment = new PickIconFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(rootFrame.getId(), pickIconFragment,
                    PickIconFragment.class.getName());
            fragmentTransaction.commit();
        }else{
            PickIconFragment pickIconFragment = new PickIconFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(settingFrame.getId(), pickIconFragment,
                    PickIconFragment.class.getName());
            fragmentTransaction.commit();
        }
    }

}
