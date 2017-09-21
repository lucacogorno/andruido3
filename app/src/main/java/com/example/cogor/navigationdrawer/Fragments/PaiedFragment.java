package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.cogor.navigationdrawer.R;
import com.example.cogor.navigationdrawer.Tasks.GetOrdersTask;

/**
 * Created by cogor on 06/09/2017.
 */

public class PaiedFragment extends Fragment {

    View myView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.ordered_fragment, container, false);

        new GetOrdersTask(myView, getActivity(), "paied").execute();


        return myView;
    }



}
