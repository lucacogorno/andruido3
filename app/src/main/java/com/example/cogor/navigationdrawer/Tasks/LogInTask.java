package com.example.cogor.navigationdrawer.Tasks;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by cogor on 09/08/2017.
 */

public class LogInTask extends AsyncTask<Object, Object, String> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/logIn.php";
    private String password;
    private String username;
    View view;
    Activity activity;

    public LogInTask(String username, String password, View view, Activity activity)
    {
        this.username = username;
        this.password = password;
        this.view = view;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Object... params) {

        URL reqURL = null;
        String data = "";
        BufferedReader reader;



        try {

            reqURL = new URL(requestURL);

            HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());

            data += URLEncoder.encode("lname", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") +
            "&" + URLEncoder.encode("lpsw", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            Log.d("OutputSent", data);
            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            int responseCode = urlConnection.getResponseCode();


            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null)
            {
                    sb.append(line);
            }

            Log.d("returnedFromLogIn", sb.toString());

            return sb.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";
    }

    @Override
    protected void onPostExecute(String s) {
        if(s.contains("true"))
        {
            Log.d("Response", s);
            Toast.makeText(view.getContext(), "Log in success", Toast.LENGTH_SHORT).show();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
            prefs.edit().putString("Username", username).commit();
            NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
            Menu menu = navigationView.getMenu();
            menu.clear();
            navigationView.inflateMenu(R.menu.activity_main_logged_drawer);
            navigationView.getMenu().findItem(R.id.myData).setTitle(username);
            if(s.contains("1")) {
                navigationView.getMenu().findItem(R.id.AdminArea).setVisible(true);
                prefs.edit().putBoolean("isVendor", true).commit();
            }
            activity.getFragmentManager().popBackStack();
        }
        else

            Toast.makeText(view.getContext(), "Log in failed", Toast.LENGTH_SHORT).show();
    }



    }

