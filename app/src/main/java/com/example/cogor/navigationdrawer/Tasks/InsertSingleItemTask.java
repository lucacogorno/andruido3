package com.example.cogor.navigationdrawer.Tasks;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.Database.DbCart;
import com.example.cogor.navigationdrawer.Database.DbCartHelper;

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

public class InsertSingleItemTask extends AsyncTask<Object, Object, Boolean> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/single_item.php";

    View view;
    Activity activity;
    String name;
    Cursor cursor;
    String id;
    boolean noErrors;

    public InsertSingleItemTask(String name, String id, Activity activity)
    {
        this.activity = activity;
        this.name = name;
        this.id = id;
        noErrors = true;

    }

    @Override
    protected Boolean doInBackground(Object... params) {

        URL reqURL = null;
        String data;
        BufferedReader reader;

        DbCartHelper dbHelper = new DbCartHelper(activity.getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        cursor = db.query(DbCart.CartInit.TABLE_NAME , null, "username = ?" , new String[]{name}, null, null, null);
        while(cursor.moveToNext()) {
            data = "";
            Log.d("DENTRO", cursor.getCount() + ".");
            try {

                reqURL = new URL(requestURL);

                HttpURLConnection urlConnection = (HttpURLConnection) reqURL.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
                Log.d("CI PROVA", "prova");
                data += URLEncoder.encode("orderid", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") +
                        "&" + URLEncoder.encode("prodid", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(cursor.getInt(cursor.getColumnIndex(DbCart.CartInit.COLUMN_NAME_PRODID))), "UTF-8") +
                        "&" + URLEncoder.encode("prodname", "UTF-8") + "=" + URLEncoder.encode(cursor.getString(cursor.getColumnIndex(DbCart.CartInit.COLUMN_NAME_PRODNAME)), "UTF-8") +
                        "&" + URLEncoder.encode("quantity", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(cursor.getInt(cursor.getColumnIndex(DbCart.CartInit.COLUMN_NAME_QUANTITY))), "UTF-8") +
                        "&" + URLEncoder.encode("singleamount", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(cursor.getDouble(cursor.getColumnIndex(DbCart.CartInit.COLUMN_NAME_SINGLEAMOUNT))), "UTF-8");
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

                Log.d("RESPONSEINSERTITEM", sb.toString());
                continue;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return noErrors;
    }

}
