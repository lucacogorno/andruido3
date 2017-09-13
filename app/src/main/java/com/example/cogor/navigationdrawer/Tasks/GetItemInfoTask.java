package com.example.cogor.navigationdrawer.Tasks;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.cogor.navigationdrawer.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by cogor on 09/08/2017.
 */

public class GetItemInfoTask extends AsyncTask<Object, Object, Item> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/get_item_info.php";
    private int id;


    public GetItemInfoTask(int id)
    {
        this.id = id;
    }

    @Override
    protected Item doInBackground(Object... params) {

        URL reqURL = null;
        String data = "";
        BufferedReader reader;



        try {

            reqURL = new URL(requestURL);

            HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());

            data += URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(id), "UTF-8");
            Log.d("OutputSentUserInfo", data);
            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            int responseCode = urlConnection.getResponseCode();


            InputStream response = urlConnection.getInputStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";



            JSONArray jsonResp = new JSONArray(result);
            JSONObject jsonObject = new JSONObject(result);

            Item temp =  new Item(jsonObject.getInt("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("quantity"),
                    jsonObject.getString("price"),
                    jsonObject.getString("description"));


           return temp;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
