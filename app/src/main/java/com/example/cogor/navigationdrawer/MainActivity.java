package com.example.cogor.navigationdrawer;


import android.Manifest;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.Fragments.AdminFragment;
import com.example.cogor.navigationdrawer.Fragments.CartFragment;
import com.example.cogor.navigationdrawer.Fragments.LogInFragment;
import com.example.cogor.navigationdrawer.Fragments.MainFragment;
import com.example.cogor.navigationdrawer.Fragments.MyOrdersFragment;
import com.example.cogor.navigationdrawer.Fragments.RegistrationFragment;
import com.example.cogor.navigationdrawer.Fragments.UserFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    ListView lv;
    NavigationView navigationView;
    NavigationView welcomeText;
    View header;
    TextView text;
    int permission;
    public static FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION}, permission);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).commit();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        welcomeText = (NavigationView) findViewById(R.id.nav_view);
        header = welcomeText.getHeaderView(0);
        text = (TextView) header.findViewById(R.id.welcome);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (sharedPreferences.contains("Username")) {
            navigationView.inflateMenu(R.menu.activity_main_logged_drawer);
            navigationView.getMenu().findItem(R.id.myData).setTitle(sharedPreferences.getString("Username", null));

            text.setText("Ciao, " + sharedPreferences.getString("Username", null));
            if (sharedPreferences.getBoolean("isVendor", false)) {
                navigationView.getMenu().findItem((R.id.AdminArea)).setVisible(true);
            }
        } else {
            navigationView.inflateMenu(R.menu.activity_main_drawer);
            text.setText("MyShop");
        }
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        Log.d("length out", "" + fm.getBackStackEntryCount());
        if (fm.getBackStackEntryCount() > 1) {
            //do nothing you are on other fragments according to your work flow
        } else {
            // you are in your activity two or one so pop the fragment
            fm.popBackStack();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            if (!prefs.contains("Logged") && !prefs.getBoolean("Logged", false)) {
                Toast.makeText(getApplicationContext(), "You aren't logged", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new LogInFragment()).addToBackStack(null).commit();
            } else {
                getFragmentManager().beginTransaction().replace(R.id.content_frame, new CartFragment()).addToBackStack(null).commit();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        switch (id) {
            case R.id.Home:
                fragmentManager.popBackStack(null, fragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.content_frame, new MainFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_registration:
                fragmentManager.popBackStack(null, fragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.content_frame, new RegistrationFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_login:
                fragmentManager.popBackStack(null, fragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.content_frame, new LogInFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_logout:
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().remove("Username").commit();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().remove("Logged").commit();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().remove("isVendor").commit();
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.activity_main_drawer);
                fragmentManager.popBackStack(null, fragmentManager.POP_BACK_STACK_INCLUSIVE);
                text.setText("MyShop");
                break;
            case R.id.AdminArea:
                fragmentManager.popBackStack(null, fragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.content_frame, new AdminFragment()).addToBackStack(null).commit();
                break;
            case R.id.myData:
                fragmentManager.popBackStack(null, fragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.content_frame, new UserFragment()).addToBackStack(null).commit();
                break;
            case R.id.myOrders:
                fragmentManager.popBackStack(null, fragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.content_frame, new MyOrdersFragment()).addToBackStack(null).commit();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

