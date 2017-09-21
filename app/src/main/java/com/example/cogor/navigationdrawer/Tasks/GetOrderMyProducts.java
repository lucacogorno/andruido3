package com.example.cogor.navigationdrawer.Tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.OrderItem;
import com.example.cogor.navigationdrawer.OrderItemListAdapter;
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

public class GetOrderMyProducts extends AsyncTask<Object, Object, ArrayList<OrderItem>> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/get_order_products.php";
    View view;
    Activity atv;
    String orderid;
    String totalOrderAmount;


    public GetOrderMyProducts(View view, Activity atv, String orderid, String orderAmount)
    {
        this.view = view;
        this.atv = atv;
        this.orderid = orderid;
        this.totalOrderAmount = orderAmount;
    }

    @Override
    protected ArrayList<OrderItem> doInBackground(Object... params) {
        ArrayList<OrderItem> temp = new ArrayList<>();
        URL reqURL = null;
        String data = "";
        try {
            reqURL = new URL(requestURL);

            HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());

            data += URLEncoder.encode("orderid", "UTF-8") + "=" + URLEncoder.encode(orderid, "UTF-8");
            Log.d("OutputSentUserInfo", data);
            outputStreamWriter.write(data);
            outputStreamWriter.flush();
            InputStream response = urlConnection.getInputStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            Log.d("Prova", result);
            JSONArray jsonResp = new JSONArray(result);
            for(int i = 0; i < jsonResp.length(); i++)
            {
                Log.d("OUTSIDE ITEM",
                        jsonResp.getJSONObject(i).getInt("orderid") +
                        jsonResp.getJSONObject(i).getString("prodname")+
                        jsonResp.getJSONObject(i).getString("quantity")+
                        jsonResp.getJSONObject(i).getString("singleamount"));
                temp.add(

                        new OrderItem(jsonResp.getJSONObject(i).getInt("orderid"),
                                jsonResp.getJSONObject(i).getString("prodname"),
                                jsonResp.getJSONObject(i).getInt("quantity"),
                                jsonResp.getJSONObject(i).getDouble("singleamount")
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
    protected void onPostExecute(final ArrayList<OrderItem> orderItems) {


        ListView lv = (ListView) view.findViewById(R.id.my_order_products);
        TextView orderAmount = (TextView) view.findViewById(R.id.totalAmountOrder);

        if(orderItems.size() == 0)
        {
            Toast.makeText(view.getContext(), "Connection Error", Toast.LENGTH_SHORT);
            return;
        }

        orderAmount.setText("Amount: " + totalOrderAmount);

        OrderItemListAdapter orderItemListAdapter = new OrderItemListAdapter(orderItems, atv);

        lv.setAdapter(orderItemListAdapter);

        lv.setItemsCanFocus(false);

    }
}