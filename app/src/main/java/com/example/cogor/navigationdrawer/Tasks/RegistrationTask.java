package com.example.cogor.navigationdrawer.Tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.Fragments.LogInFragment;
import com.example.cogor.navigationdrawer.Item;
import com.example.cogor.navigationdrawer.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by cogor on 09/08/2017.
 */

public class RegistrationTask extends AsyncTask<Object, Object, String> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/registration.php";
    private String password;
    private String username;
    private String date;
    private String email;
    private String gender;
    View view;
    Activity activity;



    public RegistrationTask(String username, String password, String date, String email, String gender, View view, Activity activity)
    {
        this.username = username;
        this.password = password;
        this.date = date;
        this.email = email;
        this.view = view;
        this.activity = activity;
        this.gender = gender;
    }

    @Override
    protected String doInBackground(Object... params) {
        ArrayList<Item> temp;
        URL reqURL = null;
        String data = "";
        BufferedReader reader;


        try {

            reqURL = new URL(requestURL);

            HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());

            data +=       URLEncoder.encode("rUser", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") +
                    "&" + URLEncoder.encode("rPsw", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") +
                    "&" + URLEncoder.encode("rBirth", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") +
                    "&" + URLEncoder.encode("rEmail", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") +
                    "&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8");

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

            Log.d("ReturnedFromRegTask", sb.toString());


            if(sb.toString().equals("username"))
            return "WrongUser";

            return sb.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";
    }

    @Override
    protected void onPostExecute(String response) {
        if(response.equals("WrongUser")) {
            Toast.makeText(view.getContext(), "Existent username", Toast.LENGTH_SHORT).show();
            return;
        }


        if(!Boolean.parseBoolean(response))
        {
            Toast.makeText(view.getContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(view.getContext(), "Registration Success", Toast.LENGTH_SHORT).show();
            activity.getFragmentManager().popBackStack();
            activity.getFragmentManager().beginTransaction().replace(R.id.content_frame, new LogInFragment()).addToBackStack(null).commit();
        }



    }
}
