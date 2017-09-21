package com.example.cogor.navigationdrawer.Fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.Item;
import com.example.cogor.navigationdrawer.MainActivity;
import com.example.cogor.navigationdrawer.R;
import com.example.cogor.navigationdrawer.Tasks.CreateCartFromDbTask;
import com.example.cogor.navigationdrawer.Tasks.ProcessOrderTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by cogor on 06/09/2017.
 */

public class OrderInfoFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    ListView lv;
    View myView;
    TextView totalAmount;
    ArrayList<String> stringItems;
    ArrayList<Item> items;
    Button confirmOrder;
    Button cancelOrder;
    String address;
    GoogleApiClient mGoogleApiClient;
    SearchView addressBox0;
    ArrayList<String> permissions = new ArrayList<>();
    int permission;
    MapView mapView;
    LatLng position;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.orderinfo, container, false);
        cancelOrder = (Button) myView.findViewById(R.id.cancelOrder);
        addressBox0 = (SearchView) myView.findViewById(R.id.addressInserted);
        mapView = (MapView) myView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);


        //
        if (ActivityCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{ACCESS_FINE_LOCATION}, permission);
        }
        MainActivity.mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            position = new LatLng(location.getLatitude(), location.getLongitude());
                            String address = null;
                            Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
                            List<Address> addresses;
                            try {
                                addresses = gcd.getFromLocation(location.getLatitude(),
                                        location.getLongitude(), 1);
                                if (addresses.size() > 0) {
                                    address = addresses.get(0).getAddressLine(0);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            addressBox0.setQuery(address, true);
                            addressBox0.setIconified(false);

                            mapView.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(GoogleMap googleMap) {
                                    Marker marker = googleMap.addMarker(new MarkerOptions().position(position));

                                    //zoom to position with level 16
                                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 16);
                                    googleMap.animateCamera(cameraUpdate);
                                    mapView.onResume();
                                }
                            });
                        }
                    }
                });


        confirmOrder = (Button) myView.findViewById(R.id.confirmOrder);
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = addressBox0.getQuery().toString();
                new ProcessOrderTask(address, myView, getActivity()).execute();
            }
        });

        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
            }
        });
        addressBox0.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String address = query;
                Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
                List<Address> addresses = new ArrayList<Address>();
                try {
                    addresses = gcd.getFromLocationName(address, 1);
                    if (addresses.size() > 0) {
                        address = addresses.get(0).getAddressLine(0);
                        position = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                        mapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                Marker marker = googleMap.addMarker(new MarkerOptions().position(position));
                                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 16);
                                googleMap.animateCamera(cameraUpdate);
                                mapView.onResume();
                            }
                        });
                    }
                    else{
                        Toast.makeText(myView.getContext(),"No match found.",Toast.LENGTH_SHORT).show();
                        addressBox0.setQuery("",true);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return myView;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("CONNECTIONFAILED", "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

}
