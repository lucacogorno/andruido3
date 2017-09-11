package com.example.cogor.navigationdrawer;

import android.os.AsyncTask;

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

public class RegistrationTask extends AsyncTask<Object, Object, Boolean> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/registration.php";
    private String password;
    private String username;
    private String date;
    private String email;
    private String passwordRep;



    public RegistrationTask(String username, String password, String passwordRep, String date, String email)
    {
        this.username = username;
        this.password = password;
        this.date = date;
        this.email = email;
        this.passwordRep = passwordRep;

    }

    @Override
    protected Boolean doInBackground(Object... params) {
        ArrayList<Item> temp;
        URL reqURL = null;
        String data = "";
        BufferedReader reader;

        if(password.equals(passwordRep)) {
            return false;
        }


        try {

            reqURL = new URL(requestURL);

            HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());

            data += "&" + URLEncoder.encode("Username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") +
            "&" + URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            int responseCode = urlConnection.getResponseCode();


            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null)
            {
                    sb.append(line + "\n");
            }

            return Boolean.parseBoolean(line);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
