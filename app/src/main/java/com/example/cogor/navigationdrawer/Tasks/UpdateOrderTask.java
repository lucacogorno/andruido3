package com.example.cogor.navigationdrawer.Tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by cogor on 21/09/2017.
 */

public class UpdateOrderTask extends AsyncTask<Void, Void, Boolean> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/update_order_status.php";
    Activity activity;
    View view;
    String status;
    String id;


    public UpdateOrderTask(Activity activity, View view, String status, String id) {
        this.activity = activity;
        this.view = view;
        this.status = status;
        this.id = id;
    }


    @Override
    protected Boolean doInBackground(Void... params) {
        URL reqURL = null;
        String data = "";
        try {
            reqURL = new URL(requestURL);
            HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());

            data += URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8") + "&" +
            URLEncoder.encode("orderid", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

            outputStreamWriter.write(data);
            outputStreamWriter.flush();
            Log.d("OUTPUTUPDATE", data);

            InputStream response = urlConnection.getInputStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";

            Log.d("RESULTUPDATEORDER", result);

            return true;


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(!aBoolean)
        {
            Toast.makeText(activity.getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();

        }
        else
        {
            activity.getFragmentManager().popBackStack();
        }

    }
}

