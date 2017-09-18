package com.example.cogor.navigationdrawer.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cogor.navigationdrawer.R;



/**
 * Created by cogor on 06/09/2017.
 */

public class AdminFragment extends Fragment {

    View myView;
    Button addProdButton;
    Button editButton;
    Button manageOrdersButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.admin_layout, container, false);
        editButton = (Button) myView.findViewById(R.id.editProductButton);
        addProdButton = (Button) myView.findViewById(R.id.addProdButton);
        manageOrdersButton = (Button) myView.findViewById(R.id.manageOrdersButton);


        addProdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new AddProdFragment()).addToBackStack(null).commit();
            }
        });


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new AddProdFragment()).addToBackStack(null).commit();
            }
        });

        manageOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new AddProdFragment()).addToBackStack(null).commit();
            }
        });


        return myView;
    }



}
