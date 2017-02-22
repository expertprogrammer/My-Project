package com.superlight.kashingmerchant.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.adapters.TokenListAdapter;
import com.superlight.kashingmerchant.selector.OnPosEventListener;
import com.superlight.kashingmerchant.selector.OnSettingEventListener;


public class SaleTokensFragment extends Fragment implements View.OnClickListener{

    ListView tokensList;
    TokenListAdapter adapter;

    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_sale_tokens, container, false);
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
        tokensList = (ListView)rootView.findViewById(R.id.tokens_list);
        adapter = new TokenListAdapter(getActivity());
        tokensList.setAdapter(adapter);
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

        }
    }

}
