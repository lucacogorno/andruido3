package com.example.cogor.lpaaproject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by cogor on 09/08/2017.
 */

public class GetItems extends AsyncTask<Object, Object, ArrayList<Item>> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/get_items.php";


    @Override
    protected ArrayList<Item> doInBackground(Object... params) {
        ArrayList<Item> temp;
        URL reqURL = null;
        try {
            reqURL = new URL(requestURL);
            HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
            InputStream response = urlConnection.getInputStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            JSONArray jsonResp = new JSONArray(result);
            temp = new ArrayList<>();
            Log.d("RES", jsonResp.getJSONObject(0).getString("quantity"));
            for(int i = 0; i < jsonResp.length(); i++)
            {
                Log.d("OUTSIDE ITEM", jsonResp.getJSONObject(i).getString("name")+
                        jsonResp.getJSONObject(i).getString("quantity")+
                        jsonResp.getJSONObject(i).getString("price"));
                temp.add(

                        new Item(jsonResp.getJSONObject(i).getString("name"),
                                jsonResp.getJSONObject(i).getString("quantity"),
                                jsonResp.getJSONObject(i).getString("price"))
                );
            }
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
