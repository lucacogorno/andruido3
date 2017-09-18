package com.example.cogor.navigationdrawer.Tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.Item;
import com.example.cogor.navigationdrawer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;


/**
 * Created by cogor on 09/08/2017.
 */

public class GetProdInfoTask extends AsyncTask<Object, Object, Item> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/get_item_info.php";
    private String id;
    View view;
    Activity activity;

    public GetProdInfoTask(String id, View view, Activity activity)
    {
        this.id = id;
        this.view = view;
        this.activity = activity;
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

            data += URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            Log.d("OutputSentUserInfo", data);
            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            int responseCode = urlConnection.getResponseCode();


            InputStream response = urlConnection.getInputStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";

            Log.d("ItemTaskResp", result);


            JSONArray jsonResp = new JSONArray(result);
            JSONObject jsonObject = jsonResp.getJSONObject(0);
            Item temp =  new Item(jsonObject.getLong("id"),
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

    @Override
    protected void onPostExecute(final Item item) {

        if(item == null)
        {
            Toast.makeText(activity.getApplicationContext(), "Connection error", Toast.LENGTH_SHORT).show();
        }

       TextView prodName = (TextView) view.findViewById(R.id.editName);
        TextView prodQuantity = (TextView) view.findViewById(R.id.editQuantity);
       TextView prodPrice = (TextView) view.findViewById(R.id.editPrice);
       TextView description = (TextView) view.findViewById(R.id.editDescr);

        prodName.setText(item.getName());
        prodQuantity.setText(item.getQuantity());
        prodPrice.setText(item.getPrice());
        description.setText(item.getDescription());

    }

}