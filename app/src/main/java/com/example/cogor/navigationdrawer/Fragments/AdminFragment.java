package com.example.cogor.navigationdrawer.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.Fragments.AddProdFragment;
import com.example.cogor.navigationdrawer.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


/**
 * Created by cogor on 06/09/2017.
 */

public class AdminFragment extends Fragment {

    View myView;
    Button addProdButton;
    Button scanButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.admin_layout, container, false);
        scanButton = (Button) myView.findViewById(R.id.scanButton);
        addProdButton = (Button) myView.findViewById(R.id.addProdButton);


        addProdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new AddProdFragment()).addToBackStack(null).commit();
            }
        });



        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callIntentScan();
            }
        });


        return myView;
    }


    public void callIntentScan()
    {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {

                IntentIntegrator intentIntegrator = IntentIntegrator.forFragment(this);
                intentIntegrator.initiateScan();


            }
        }

    }

    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



}
