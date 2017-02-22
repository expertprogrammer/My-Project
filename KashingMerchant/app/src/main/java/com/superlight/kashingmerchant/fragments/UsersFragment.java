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
import com.superlight.kashingmerchant.adapters.UserListAdapter;
import com.superlight.kashingmerchant.selector.OnSettingEventListener;


public class UsersFragment extends Fragment implements View.OnClickListener{

    ListView usersList;
    UserListAdapter adapter;

    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_users, container, false);
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
        usersList = (ListView)rootView.findViewById(R.id.users_list);
        adapter = new UserListAdapter(getActivity());
        usersList.setAdapter(adapter);
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    ((OnSettingEventListener)activity).onUsersListItemSelected(position);
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
                break;
            case R.id.add_view:
                try{
                    ((OnSettingEventListener)activity).onAddNewUserPressed();
                }catch (ClassCastException cce){

                }
                break;
        }
    }

}
