package com.example.cogor.navigationdrawer.Tasks;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.Database.DbCart;
import com.example.cogor.navigationdrawer.Database.DbCartHelper;
import com.example.cogor.navigationdrawer.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

/**
 * Created by cogor on 09/08/2017.
 */

public class ProcessOrderTask extends AsyncTask<Object, Object, Boolean> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/place_order.php";
    String name;
    double amount;
    String address;
    String result;
    View view;
    Cursor checkCursor;
    Activity activity;


    public ProcessOrderTask(String address, View view, Activity activity)
    {
        this.view = view;
        this.activity = activity;
        amount = 0;
        this.address = address;
    }

    @Override
    protected Boolean doInBackground(Object... params) {

        URL reqURL = null;
        String data = "";
        BufferedReader reader;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        name = sharedPreferences.getString("Username", null);



        DbCartHelper dbHelper = new DbCartHelper(activity.getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        checkCursor = db.query(DbCart.CartInit.TABLE_NAME , null, "username = ?" , new String[]{name}, null, null, null);

        while(checkCursor.moveToNext())
        {
            amount += checkCursor.getDouble(checkCursor.getColumnIndex(DbCart.CartInit.COLUMN_NAME_SINGLEAMOUNT));
        }


        try {

            reqURL = new URL(requestURL);

            HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());

            data += URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") +
            "&" + URLEncoder.encode("amount", "UTF-8") + "=" + URLEncoder.encode(Double.toString(amount), "UTF-8") +
            "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8");
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

            result = sb.toString();

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
       boolean fromTask = false;

        try {

            fromTask = new InsertSingleItemTask(name, result, activity).execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(b && fromTask) {
            Toast.makeText(view.getContext(), "Order processed, your order id is = " + result, Toast.LENGTH_SHORT);
        }
        else Toast.makeText(view.getContext(), "Error while processing", Toast.LENGTH_SHORT);
    }
}
