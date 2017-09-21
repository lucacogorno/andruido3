package com.example.cogor.navigationdrawer.Tasks;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.Fragments.ItemFragment;
import com.example.cogor.navigationdrawer.Fragments.MainFragment;
import com.example.cogor.navigationdrawer.Item;
import com.example.cogor.navigationdrawer.ItemListAdapter;
import com.example.cogor.navigationdrawer.MainActivity;
import com.example.cogor.navigationdrawer.R;

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

public class GetItemsTask extends AsyncTask<Object, Object, ArrayList<Item>> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/get_items.php";
    View view;
    Activity atv;

    public GetItemsTask(View view, Activity atv) {
        this.view = view;
        this.atv = atv;
    }

    @Override
    protected ArrayList<Item> doInBackground(Object... params) {
        ArrayList<Item> temp = new ArrayList<>();
        URL reqURL;
        try {
            reqURL = new URL(requestURL);
            HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
            InputStream response = urlConnection.getInputStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            JSONArray jsonResp = new JSONArray(result);
            for (int i = 0; i < jsonResp.length(); i++) {
                Log.d("OUTSIDE ITEM",
                        jsonResp.getJSONObject(i).getInt("id") +
                                jsonResp.getJSONObject(i).getString("name") +
                                jsonResp.getJSONObject(i).getString("quantity") +
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
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        return temp;
    }

    @Override
    protected void onPostExecute(final ArrayList<Item> items) {

        final ArrayList<String> stringItems = new ArrayList<>();
        ListView lv = (ListView) view.findViewById(R.id.listView);
        if (items.size() == 0) {
            Toast.makeText(view.getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            stringItems.add(items.get(i).toString());
        }
        ItemListAdapter itemListAdapter = new ItemListAdapter(items, atv);
        lv.setAdapter(itemListAdapter);
        lv.setItemsCanFocus(false);

    }
}
