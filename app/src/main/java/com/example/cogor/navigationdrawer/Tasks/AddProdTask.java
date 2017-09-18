package com.example.cogor.navigationdrawer.Tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by cogor on 09/08/2017.
 */

public class AddProdTask extends AsyncTask<Object, Object, Boolean> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/create_product.php";
    String name;
    String quantity;
    String price;
    String descr;
    String id;
    View view;


    public AddProdTask(String id, String name, String quantity, String price, String descr, View view)
    {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.descr = descr;
        this.view = view;

    }

    @Override
    protected Boolean doInBackground(Object... params) {

        URL reqURL = null;
        String data = "";
        BufferedReader reader;


        try {

            reqURL = new URL(requestURL);

            HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());

            data += URLEncoder.encode("prodid", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") +
                    "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") +
            "&" + URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(quantity, "UTF-8") +
            "&" + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(price, "UTF-8") +
            "&" + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(descr, "UTF-8");
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

            Log.d("returnedFromAddProd", sb.toString());

            return Boolean.parseBoolean(sb.toString());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean b) {
        if(b) Toast.makeText(view.getContext(), "Product added", Toast.LENGTH_SHORT);
        else Toast.makeText(view.getContext(), "Product not added", Toast.LENGTH_SHORT);
    }
}
