package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cogor.navigationdrawer.Tasks.AddProdTask;
import com.example.cogor.navigationdrawer.R;

import java.util.concurrent.ExecutionException;

/**
 * Created by cogor on 06/09/2017.
 */

public class AddProdFragment extends Fragment {

    View myView;
    Button addButton;
    TextView prodName;
    TextView prodQuantity;
    TextView prodPrice;
    TextView description;
    String name;
    String quantity;
    String price;
    String descr;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.add_prod_layout, container, false);
        addButton = (Button) myView.findViewById(R.id.addButton);
        prodName = (TextView) myView.findViewById(R.id.inputName);
        prodQuantity = (TextView) myView.findViewById(R.id.inputQuantity);
        prodPrice = (TextView) myView.findViewById(R.id.inputPrice);
        description = (TextView) myView.findViewById(R.id.prod_descr);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = prodName.getText().toString();
                quantity = prodQuantity.getText().toString();
                price = prodPrice.getText().toString();
                descr = description.getText().toString();

                try {
                    callAddProdTask(name, quantity, price, descr);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        return myView;
    }



    public void callAddProdTask(String name, String quantity, String price, String descr) throws ExecutionException, InterruptedException {
       String response = new AddProdTask(name, quantity, price, descr).execute().get();
        Log.d("AddResponse", response);
    }

}
