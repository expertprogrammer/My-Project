package com.superlight.kashingmerchant.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.adapters.ProductGridAdapter;
import com.superlight.kashingmerchant.adapters.ProductHorListAdapter;
import com.superlight.kashingmerchant.adapters.ProductPosListAdapter;
import com.superlight.kashingmerchant.selector.OnPosEventListener;
import com.superlight.kashingmerchant.utilities.HorizontalListView;

public class ProductsFragment extends Fragment {

    ListView productsList;
    HorizontalListView productsHorList;
    ProductPosListAdapter adapter1;
    ProductHorListAdapter adapter2;

    ProductGridAdapter adapter3;
    GridView productsGridView;

    Activity activity;

    LinearLayout phoneView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_products, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }

    public void initView(View rootView){
        phoneView = (LinearLayout)rootView.findViewById(R.id.phone_view);
        if (phoneView != null){
            productsList = (ListView)rootView.findViewById(R.id.products_list);
            productsHorList = (HorizontalListView) rootView.findViewById(R.id.products_horizontal_list);
            adapter1 = new ProductPosListAdapter(getActivity());
            productsList.setAdapter(adapter1);
            adapter2 = new ProductHorListAdapter(getActivity());
            productsHorList.setAdapter(adapter2);
        }else {
            productsGridView = (GridView)rootView.findViewById(R.id.products_grid);
            adapter3 = new ProductGridAdapter(getActivity());
            productsGridView.setAdapter(adapter3);
            productsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        ((OnPosEventListener) activity).onTabletProductItemPressed();
                    }catch (ClassCastException cce){

                    }
                }
            });
        }
    }

}
