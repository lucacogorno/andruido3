package com.example.cogor.navigationdrawer.Tasks;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.Fragments.ItemFragment;
import com.example.cogor.navigationdrawer.Item;
import com.example.cogor.navigationdrawer.Order;
import com.example.cogor.navigationdrawer.OrderListAdapter;
import com.example.cogor.navigationdrawer.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
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

public class GetOrdersTask extends AsyncTask<Object, Object, ArrayList<Order>> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/get_all_orders.php";
    View view;
    Activity atv;
    String status;

    public GetOrdersTask(View view, Activity atv, String status)
    {
        this.view = view;
        this.atv = atv;
        this.status = status;
    }

    @Override
    protected ArrayList<Order> doInBackground(Object... params) {
        ArrayList<Order> temp = new ArrayList<>();
        URL reqURL = null;
        String data = "";
        try {
            reqURL = new URL(requestURL);
            HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());

            data += URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8");

            outputStreamWriter.write(data);
            outputStreamWriter.flush();


            InputStream response = urlConnection.getInputStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            Log.d("ORDERSRESP", result);
            JSONArray jsonResp = new JSONArray(result);
            for(int i = 0; i < jsonResp.length(); i++)
            {

                temp.add(

                        new Order(jsonResp.getJSONObject(i).getInt("id"),
                                jsonResp.getJSONObject(i).getString("username"),
                                jsonResp.getJSONObject(i).getString("address"),
                                jsonResp.getJSONObject(i).getDouble("amount"),
                                jsonResp.getJSONObject(i).getString("date"),
                                jsonResp.getJSONObject(i).getString("status")
                        )
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


        return temp;
    }

    @Override
    protected void onPostExecute(final ArrayList<Order> orders) {
        String intoButton;
        ListView lv = (ListView) view.findViewById(R.id.ordered_listview);

        if(orders.size() == 0)
        {
            Toast.makeText(view.getContext(), "Connection Error", Toast.LENGTH_SHORT);
            return;
        }
        OrderListAdapter orderListAdapter = new OrderListAdapter(orders, atv);

        lv.setAdapter(orderListAdapter);




    }
}
