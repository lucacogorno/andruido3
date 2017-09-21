package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.cogor.navigationdrawer.Item;
import com.example.cogor.navigationdrawer.R;
import com.example.cogor.navigationdrawer.Tasks.GetMyOrdersTask;
import com.example.cogor.navigationdrawer.Tasks.GetOrdersTask;

import java.util.ArrayList;


/**
 * Created by cogor on 06/09/2017.
 */

public class MyOrdersFragment extends Fragment {
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.myorders_fragment, container, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String username = sharedPreferences.getString("Username", null);


        new GetMyOrdersTask(myView, getActivity(), username).execute();


        return myView;
    }



}
