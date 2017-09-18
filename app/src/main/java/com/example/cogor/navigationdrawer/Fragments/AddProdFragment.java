package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.Tasks.AddProdTask;
import com.example.cogor.navigationdrawer.R;
import com.example.cogor.navigationdrawer.Tasks.GetProdNameById;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import java.util.concurrent.ExecutionException;


/**
 * Created by cogor on 06/09/2017.
 */

public class AddProdFragment extends Fragment {

    View myView;
    Button addButton;
    Button scanIdButton;
    TextView prodId;
    TextView prodName;
    TextView prodQuantity;
    TextView prodPrice;
    TextView description;
    String name;
    String quantity;
    String price;
    String descr;
    String prodid;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.add_prod_layout, container, false);
        addButton = (Button) myView.findViewById(R.id.deleteButton);
        prodName = (TextView) myView.findViewById(R.id.editName);
        prodQuantity = (TextView) myView.findViewById(R.id.editQuantity);
        prodPrice = (TextView) myView.findViewById(R.id.editPrice);
        description = (TextView) myView.findViewById(R.id.editDescr);
        scanIdButton = (Button) myView.findViewById(R.id.scanIdButton);
        prodId = (TextView) myView.findViewById(R.id.inoutProdId);

        scanIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callIntentScan();
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = prodName.getText().toString();
                quantity = prodQuantity.getText().toString();
                price = prodPrice.getText().toString();
                descr = description.getText().toString();
                prodid = prodId.getText().toString();
                try {
                    callAddProdTask(prodid, name, quantity, price, descr);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        return myView;
    }


    public void callIntentScan() {
        IntentIntegrator intentIntegrator = IntentIntegrator.forFragment(this);
        intentIntegrator.initiateScan();
        //
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                prodId.setText(result.getContents());
                new GetProdNameById(result.getContents(), prodName).execute();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void callAddProdTask(String id, String name, String quantity, String price, String descr) throws ExecutionException, InterruptedException {
        new AddProdTask(id, name, quantity, price, descr, myView, getActivity()).execute();
    }


}
