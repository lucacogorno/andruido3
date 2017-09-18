package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
        editButton = (Button) myView.findViewById(R.id.editProdButton);
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
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new EditProdFragment()).addToBackStack(null).commit();
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
