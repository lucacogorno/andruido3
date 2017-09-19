package com.example.cogor.navigationdrawer.Tasks;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;


public class GetProdNameById extends AsyncTask<String, Object, String> {
    private String id;
    private TextView textView;

    public GetProdNameById(String id, TextView textView) {
        this.id = id;
        this.textView = textView;
    }

    @Override
    protected String doInBackground(String... params) {
        String name = "";
        URL url;
        try {
            url = new URL("https://api.upcitemdb.com/prod/trial/lookup?upc=" + id);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            InputStream response = urlConnection.getInputStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            JSONObject json = new JSONObject((result));
            JSONArray jsa = json.getJSONArray("items");
            name = jsa.getJSONObject(0).getString("title");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    @Override
    protected void onPostExecute(String s) {
        textView.setText(s);
    }
}
