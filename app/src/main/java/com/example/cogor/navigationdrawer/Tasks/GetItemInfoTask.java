package com.example.cogor.navigationdrawer.Tasks;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.Database.DbCart;
import com.example.cogor.navigationdrawer.Database.DbCartHelper;
import com.example.cogor.navigationdrawer.Fragments.LogInFragment;
import com.example.cogor.navigationdrawer.Item;
import com.example.cogor.navigationdrawer.MainActivity;
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

public class GetItemInfoTask extends AsyncTask<Object, Object, Item> {
    private static String requestURL = "http://webdev.disi.unige.it/~S4110217/get_item_info.php";
    private long id;
    ImageView imageView2;
    View view;
    Activity activity;
    Bitmap bitmap;

    public GetItemInfoTask(long id, View view, Activity activity) {
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

            data += URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(Long.toString(id), "UTF-8");
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
            Item temp = new Item(jsonObject.getLong("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("quantity"),
                    jsonObject.getString("price"),
                    jsonObject.getString("description"),
                    jsonObject.getString("productImage")
            );
            byte[] encodeByte = Base64.decode(temp.getImage(), Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);


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


        TextView title = (TextView) view.findViewById(R.id.itemname);
        TextView descr = (TextView) view.findViewById(R.id.itemdescr);
        TextView singlePrice = (TextView) view.findViewById(R.id.singlePrice);
        TextView itemQuantity = (TextView) view.findViewById(R.id.itemQuanityShop);
        final Button addProdButton = (Button) view.findViewById(R.id.addToCartButton);
        imageView2 = (ImageView) view.findViewById(R.id.imageView2);

        title.setText(item.getName());
        descr.setText(item.getDescription());
        singlePrice.setText(item.getPrice());
        itemQuantity.setText(item.getQuantity());
        if(bitmap != null)
        imageView2.setImageBitmap(bitmap);

        addProdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
                if (!prefs.contains("Logged") && !prefs.getBoolean("Logged", false)) {
                    Toast.makeText(activity.getApplicationContext(), "You aren't logged", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = activity.getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, new LogInFragment()).addToBackStack(null).commit();
                    return;

                }

                String username = prefs.getString("Username", null);

                DbCartHelper dbCartHelper = new DbCartHelper(activity.getApplicationContext());
                ContentValues contentValues = new ContentValues();
                SQLiteDatabase db = dbCartHelper.getWritableDatabase();


                String[] toCheck = new String[]{username, Long.toString(item.getId())};

                Cursor checkCursor = db.query(DbCart.CartInit.TABLE_NAME, null, "username = ? AND prodid = ?", toCheck, null, null, null);
                if (checkCursor.getCount() < 1) {
                    contentValues.put(DbCart.CartInit.COLUMN_NAME_PRODID, item.getId());
                    contentValues.put(DbCart.CartInit.COLUMN_NAME_QUANTITY, 1);
                    contentValues.put(DbCart.CartInit.COLUMN_NAME_USERNAME, username);
                    contentValues.put(DbCart.CartInit.COLUMN_NAME_PRODNAME, item.getName());
                    contentValues.put(DbCart.CartInit.COLUMN_NAME_SINGLEAMOUNT, item.getPrice());
                    db.insert(DbCart.CartInit.TABLE_NAME, null, contentValues);
                } else {
                    String[] toUpdate = new String[]{"quantity", "singleamount"};
                    Cursor infoCursor = db.query(DbCart.CartInit.TABLE_NAME, toUpdate, "username = ? AND prodid = ?", toCheck, null, null, null);
                    infoCursor.moveToFirst();

                    int currentQuantity = infoCursor.getInt(infoCursor.getColumnIndex(DbCart.CartInit.COLUMN_NAME_QUANTITY));
                    double currentAmount = infoCursor.getDouble(infoCursor.getColumnIndex(DbCart.CartInit.COLUMN_NAME_SINGLEAMOUNT));
                    contentValues.put(DbCart.CartInit.COLUMN_NAME_QUANTITY, currentQuantity + 1);
                    contentValues.put(DbCart.CartInit.COLUMN_NAME_SINGLEAMOUNT, currentAmount + Double.parseDouble(item.getPrice()));
                    db.update(DbCart.CartInit.TABLE_NAME, contentValues, "username = ? AND prodid = ?", toCheck);
                }
                Toast.makeText(activity.getApplicationContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                db.close();
            }
        });
    }

}