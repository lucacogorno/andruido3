package com.example.cogor.navigationdrawer.Tasks;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.Fragments.AdminFragment;
import com.example.cogor.navigationdrawer.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

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
    Bitmap photo;
    Activity activity;
    View view;


    public AddProdTask(String id, String name, String quantity, String price, String descr, Bitmap photo, View view, Activity activity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.descr = descr;
        this.photo = photo;
        this.view = view;
        this.activity = activity;

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
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setDoInput(true);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 75, stream); //compress to which format you want.
            byte[] byte_arr = stream.toByteArray();
            String photo_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
            Log.d("BYTE_ARRAY", photo_str);
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("prodid", id)
                    .appendQueryParameter("name", name)
                    .appendQueryParameter("quantity", quantity)
                    .appendQueryParameter("price", price)
                    .appendQueryParameter("description", descr)
                    .appendQueryParameter("productImage", photo_str);
            String query = builder.build().getEncodedQuery();

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();

            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            Log.d("returnedFromAddProd", sb.toString());

            return true;
        } catch (ProtocolException e) {

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        URL reqURL = null;
        String data = "";
        BufferedReader reader;


        try {

            reqURL = new URL(requestURL);

            HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());

            //bitmap
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
            byte[] byte_arr = stream.toByteArray();
            String photo_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);

            data += URLEncoder.encode("prodid", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") +
                    "&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") +
                    "&" + URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(quantity, "UTF-8") +
                    "&" + URLEncoder.encode("price", "UTF-8") + "=" + URLEncoder.encode(price, "UTF-8") +
                    "&" + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(descr, "UTF-8") +
                    "&" + URLEncoder.encode("productImage", "UTF-8") + "=" + URLEncoder.encode(photo_str, "UTF-8");
            Log.d("OutputSent", data);
            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            int responseCode = urlConnection.getResponseCode();


            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            Log.d("returnedFromAddProd", sb.toString());

            return true;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return false;
    }

    @Override
    protected void onPostExecute(Boolean b) {
        if (b) {
            Toast.makeText(view.getContext(), "Product added", Toast.LENGTH_SHORT).show();
            activity.getFragmentManager().popBackStack();
            activity.getFragmentManager().beginTransaction().replace(R.id.content_frame, new AdminFragment()).commit();
        } else Toast.makeText(view.getContext(), "Product not added", Toast.LENGTH_SHORT).show();
    }
}
