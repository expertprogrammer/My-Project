package com.superlight.kashingmerchant.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.adapters.ColorListAdapter;
import com.superlight.kashingmerchant.selector.OnSettingEventListener;

public class PickColorFragment extends Fragment implements View.OnClickListener{

    ListView colorList;
    ColorListAdapter adapter;

    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_pick_color, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }

    public void initView(View rootView){
        rootView.findViewById(R.id.back_view).setOnClickListener(this);
        colorList = (ListView)rootView.findViewById(R.id.color_list);
        adapter = new ColorListAdapter(getActivity());
        colorList.setAdapter(adapter);
        colorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    ((OnSettingEventListener)activity).onBackEditCategoryPressed();
                }catch (ClassCastException cce){

                }
            }
        });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_view:
                try{
                    ((OnSettingEventListener)activity).onBackEditCategoryPressed();
                }catch (ClassCastException cce){

                }
                break;
        }
    }

}
