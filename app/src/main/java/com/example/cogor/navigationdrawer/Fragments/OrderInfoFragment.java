package com.example.cogor.navigationdrawer.Fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cogor.navigationdrawer.Item;
import com.example.cogor.navigationdrawer.R;
import com.example.cogor.navigationdrawer.Tasks.CreateCartFromDbTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;

/**
 * Created by cogor on 06/09/2017.
 */

public class OrderInfoFragment extends Fragment{

    ListView lv;
    View myView;
    TextView totalAmount;
    ArrayList<String> stringItems;
    ArrayList<Item> items;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.orderinfo, container, false);




        return myView;
    }



}
