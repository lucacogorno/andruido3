package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cogor.navigationdrawer.Item;
import com.example.cogor.navigationdrawer.R;
import com.example.cogor.navigationdrawer.Tasks.CreateCartFromDbTask;

import java.util.ArrayList;

/**
 * Created by cogor on 06/09/2017.
 */

public class ManageOrdersFragment extends Fragment {


    View myView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.manageordersfragment, container, false);



        return myView;
    }



}
