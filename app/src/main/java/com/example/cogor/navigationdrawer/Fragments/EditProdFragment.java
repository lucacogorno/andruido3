package com.example.cogor.navigationdrawer.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.R;
import com.example.cogor.navigationdrawer.Tasks.DeleteProdTask;
import com.example.cogor.navigationdrawer.Tasks.EditProdTask;
import com.example.cogor.navigationdrawer.Tasks.GetProdInfoTask;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by cogor on 06/09/2017.
 */

public class EditProdFragment extends Fragment {

    View myView;
    Button deleteButton;
    Button editProdButton;
    Button scanIdButton;
    Button searchProdInfo;
    TextView prodId;
    TextView prodName;
    TextView prodQuantity;
    TextView prodPrice;
    TextView description;
    String name;
    String quantity;
    String price;
    String descr;
    String id;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.edit_product, container, false);

        prodName = (TextView) myView.findViewById(R.id.editName);
        prodQuantity = (TextView) myView.findViewById(R.id.editQuantity);
        prodPrice = (TextView) myView.findViewById(R.id.editPrice);
        description = (TextView) myView.findViewById(R.id.editDescr);
        prodId = (TextView) myView.findViewById(R.id.editProdId);
        scanIdButton = (Button) myView.findViewById(R.id.scanIdButton);
        searchProdInfo = (Button) myView.findViewById(R.id.searchProdInfo);
        editProdButton = (Button) myView.findViewById(R.id.editProdButton);
        deleteButton = (Button) myView.findViewById(R.id.deleteButton);

        searchProdInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = prodId.getText().toString();
                if(id.isEmpty() || id == null)
                    Toast.makeText(getActivity().getApplicationContext(), "No id to check", Toast.LENGTH_SHORT).show();
                else
                {
                    Log.d("ProdIdValue", id + "a");
                   new GetProdInfoTask(id, myView, getActivity()).execute();
                }
            }
        });

        editProdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = prodName.getText().toString();
                quantity = prodQuantity.getText().toString();
                descr = description.getText().toString();
                price = prodPrice.getText().toString();
                id = prodId.getText().toString();
                if(name.isEmpty() || quantity.isEmpty() || price.isEmpty() || id.isEmpty())
                {
                    Toast.makeText(getActivity().getApplicationContext(), "No blank fields execpt description", Toast.LENGTH_SHORT).show();
                    return;
                }
                new EditProdTask(id, name, quantity, price, descr, myView, getActivity()).execute();

            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prodId.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity().getApplicationContext(), "No id to delete", Toast.LENGTH_SHORT).show();
                    return;
                }
                    new DeleteProdTask(Long.parseLong(prodId.getText().toString()), myView, getActivity()).execute();

            }
        });

        scanIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callIntentScan();
            }
        });

        return myView;
    }





    public void callIntentScan()
    {
        IntentIntegrator intentIntegrator = IntentIntegrator.forFragment(this);
        intentIntegrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                    prodId.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



}
