package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cogor.navigationdrawer.R;
import com.example.cogor.navigationdrawer.Tasks.GetOrderMyProducts;
import com.example.cogor.navigationdrawer.Tasks.GetOrderProducts;

/**
 * Created by cogor on 06/09/2017.
 */

public class MyOrderProductsFragment extends Fragment {

    View myView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.my_order_products, container, false);

        Bundle bundle = getArguments();
        int orderid = bundle.getInt("OrderId");
        new GetOrderMyProducts(myView, getActivity(), Integer.toString(orderid)).execute();


        return myView;
    }



}
