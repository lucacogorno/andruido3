package com.example.cogor.navigationdrawer.Tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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


    public LogInTask(String username, String password)
    {
        this.username = username;
        this.password = password;

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
}
