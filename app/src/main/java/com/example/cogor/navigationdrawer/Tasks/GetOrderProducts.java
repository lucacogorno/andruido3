package com.example.cogor.navigationdrawer.Tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.Item;
import com.example.cogor.navigationdrawer.ItemListAdapter;
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

public class GetOrderProducts extends AsyncTask<Object, Object, ArrayList<Item>> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/get_order_products.php";
    View view;
    Activity atv;
    String orderid;

    public GetOrderProducts(View view, Activity atv, String orderid)
    {
        this.view = view;
        this.atv = atv;
        this.orderid = orderid;
    }

    @Override
    protected ArrayList<Item> doInBackground(Object... params) {
        ArrayList<Item> temp = new ArrayList<>();
        URL reqURL = null;
        String data = "";
        try {
            reqURL = new URL(requestURL);

            HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());

            data += URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(orderid, "UTF-8");
            Log.d("OutputSentUserInfo", data);
            outputStreamWriter.write(data);
            outputStreamWriter.flush();
            InputStream response = urlConnection.getInputStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            JSONArray jsonResp = new JSONArray(result);
            for(int i = 0; i < jsonResp.length(); i++)
            {
                Log.d("OUTSIDE ITEM",
                        jsonResp.getJSONObject(i).getInt("id") +
                        jsonResp.getJSONObject(i).getString("name")+
                        jsonResp.getJSONObject(i).getString("quantity")+
                        jsonResp.getJSONObject(i).getString("price"));
                temp.add(

                        new Item(jsonResp.getJSONObject(i).getLong("id"),
                                jsonResp.getJSONObject(i).getString("name"),
                                jsonResp.getJSONObject(i).getString("quantity"),
                                jsonResp.getJSONObject(i).getString("price"),
                                jsonResp.getJSONObject(i).getString("description")
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
    protected void onPostExecute(final ArrayList<Item> items) {

        final ArrayList<String> stringItems = new ArrayList<>();
        ListView lv = (ListView) view.findViewById(R.id.order_products);
        if(items.size() == 0)
        {
            Toast.makeText(view.getContext(), "Connection Error", Toast.LENGTH_SHORT);
            return;
        }
        for(int i=0; i<items.size(); i++)
        {
            stringItems.add(items.get(i).toString());
        }
        ItemListAdapter itemListAdapter = new ItemListAdapter(items, atv);

        lv.setAdapter(itemListAdapter);

        lv.setItemsCanFocus(false);

    }
}
