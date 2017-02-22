package com.superlight.kashingmerchant.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.adapters.ProductListAdapter;
import com.superlight.kashingmerchant.selector.OnPosEventListener;
import com.superlight.kashingmerchant.selector.OnSettingEventListener;

public class ProductsListFragment extends Fragment implements View.OnClickListener{

    ListView productsList;
    ProductListAdapter adapter;

    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_products_list, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }

    public void initView(View rootView){
        LinearLayout backView = (LinearLayout)rootView.findViewById(R.id.back_view);
        if(backView != null){
            backView.setOnClickListener(this);
        }
        rootView.findViewById(R.id.add_view).setOnClickListener(this);
        productsList = (ListView)rootView.findViewById(R.id.products_list);
        adapter = new ProductListAdapter(getActivity());
        productsList.setAdapter(adapter);
        productsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    ((OnSettingEventListener)activity).onProductsListItemSelected(position);
                }catch (ClassCastException cce){

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_view:
                try{
                    ((OnSettingEventListener)activity).onRootBackViewPressed();
                }catch (ClassCastException cce){

                }
                try{
                    ((OnPosEventListener)activity).onRootBackViewPressed();
                }catch (ClassCastException cce){

                }
                break;
            case R.id.add_view:
                try{
                    ((OnSettingEventListener)activity).onAddNewProductPressed();
                }catch (ClassCastException cce){

                }
                break;
        }
    }

}
