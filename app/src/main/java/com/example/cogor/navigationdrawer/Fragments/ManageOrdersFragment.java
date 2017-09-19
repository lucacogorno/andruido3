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

public class ManageOrdersFragment extends Fragment {


    View myView;
    Button orderedButton;
    Button deliveredButton;
    Button paiedButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.manageordersfragment, container, false);
            orderedButton = (Button) myView.findViewById(R.id.orderedOrdersButton);
            deliveredButton = (Button) myView.findViewById(R.id.deliveredOrdersButton);
            paiedButton = (Button) myView.findViewById(R.id.paiedOrdersButton);

        orderedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().replace(R.id.content_frame, new OrderedOrderFragment()).addToBackStack(null).commit();
            }
        });

        deliveredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        paiedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return myView;
    }



}
