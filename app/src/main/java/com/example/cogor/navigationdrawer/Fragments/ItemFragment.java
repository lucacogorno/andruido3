package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.cogor.navigationdrawer.Cart;
import com.example.cogor.navigationdrawer.MainActivity;
import com.example.cogor.navigationdrawer.Tasks.GetItemInfoTask;
import com.example.cogor.navigationdrawer.Tasks.GetItemsTask;
import com.example.cogor.navigationdrawer.Item;
import com.example.cogor.navigationdrawer.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by cogor on 30/08/2017.
 */

public class ItemFragment extends Fragment implements OnEditorActionListener, OnClickListener{

    View view;
    long id;
    Button addProdButton;
    TextView title;
    TextView descr;
    TextView price;
    Item itemToShow;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_item, container, false);

        id = Long.parseLong(getArguments().get("arg").toString());



        new GetItemInfoTask(id, view, getActivity()).execute();


        return view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }
}
