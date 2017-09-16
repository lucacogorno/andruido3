package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cogor.navigationdrawer.Item;
import com.example.cogor.navigationdrawer.MainActivity;
import com.example.cogor.navigationdrawer.R;
import com.example.cogor.navigationdrawer.Tasks.CreateCartFromDbTask;
import com.example.cogor.navigationdrawer.Tasks.GetItemsTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by cogor on 06/09/2017.
 */

public class CartFragment extends Fragment {

    ListView lv;
    View myView;
    TextView totalAmount;
    ArrayList<String> stringItems;
    ArrayList<Item> items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_cart, container, false);



        new CreateCartFromDbTask(getActivity(), myView).execute();


        return myView;
    }



}
