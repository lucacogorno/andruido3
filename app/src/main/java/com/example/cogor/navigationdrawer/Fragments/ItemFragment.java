package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    int id;
    Button addProdButton;
    TextView title;
    TextView descr;
    Cart myCart;
    Item itemToAdd;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_item, container, false);
        title = (TextView) view.findViewById(R.id.itemname);
        descr = (TextView) view.findViewById(R.id.itemdescr);
        addProdButton = (Button) view.findViewById(R.id.addToCartButton);
        myCart = MainActivity.cart;

        id = Integer.parseInt(getArguments().get("arg").toString());


        try {
            itemToAdd = new GetItemInfoTask(id).execute().get();

          //  title.setText(itemToShow.getName());
           // descr.setText(itemToShow.getDescription());
         } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        addProdButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myCart.addToCart(itemToAdd);
            }
        });


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
