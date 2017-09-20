package com.example.cogor.navigationdrawer.Tasks;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cogor.navigationdrawer.CartItem;
import com.example.cogor.navigationdrawer.CartListAdapter;
import com.example.cogor.navigationdrawer.Database.DbCart;
import com.example.cogor.navigationdrawer.Database.DbCartHelper;
import com.example.cogor.navigationdrawer.Fragments.LogInFragment;
import com.example.cogor.navigationdrawer.Fragments.OrderInfoFragment;
import com.example.cogor.navigationdrawer.R;
import com.google.zxing.client.android.Intents;

import java.util.ArrayList;

/**
 * Created by cogor on 17/09/2017.
 */

public class CreateCartFromDbTask extends AsyncTask<Object, Object, ArrayList<CartItem>>{
    Activity activity;
    View view;
    double totalAmount;
    public CreateCartFromDbTask(Activity activity, View view)
    {
        this.activity = activity;
        this.view = view;
        totalAmount = 0;
    }

    @Override
    protected ArrayList<CartItem> doInBackground(Object... params) {
        ArrayList<CartItem> toReturn = new ArrayList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        String username = prefs.getString("Username", null);
        DbCartHelper dbCartHelper = new DbCartHelper(activity.getApplicationContext());
        SQLiteDatabase db = dbCartHelper.getReadableDatabase();

        Cursor checkCursor = db.query(DbCart.CartInit.TABLE_NAME , null, "username = ?" , new String[]{username}, null, null, null);

        while(checkCursor.moveToNext())
        {
            Log.d("FROM SQLITE", checkCursor.getString(checkCursor.getColumnIndex("prodid")));

            toReturn.add(
                        new CartItem(checkCursor.getString(checkCursor.getColumnIndex("prodid")),
                                checkCursor.getString(checkCursor.getColumnIndex("username")),
                                checkCursor.getString(checkCursor.getColumnIndex("prodname")),
                                Integer.parseInt(checkCursor.getString(checkCursor.getColumnIndex("quantity"))),
                                        checkCursor.getString(checkCursor.getColumnIndex("singleamount"))));
                totalAmount += checkCursor.getDouble(checkCursor.getColumnIndex("singleamount"));
        }
        db.close();
        return toReturn;
    }

    @Override
    protected void onPostExecute(ArrayList<CartItem> cartItems) {

        ListView lv = (ListView) view.findViewById(R.id.cartItems);
        TextView cartAmount = (TextView) view.findViewById(R.id.totalAmount);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        if(!prefs.contains("Username") || cartItems.isEmpty())
        {
                Toast.makeText(activity.getApplicationContext(), "You aren't logged", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = activity.getFragmentManager();
                fragmentManager.popBackStack();
                fragmentManager.beginTransaction().replace(R.id.content_frame, new LogInFragment()).addToBackStack(null).commit();
                return;
        }

        cartAmount.setText(Double.toString(totalAmount));
        CartListAdapter cartListAdapter = new CartListAdapter(cartItems, activity);
        lv.setAdapter(cartListAdapter);
        Button confirmButton = (Button) view.findViewById(R.id.confirmOrder);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               activity.getFragmentManager().beginTransaction().replace(R.id.content_frame, new OrderInfoFragment()).addToBackStack(null).commit();

            }
        });


    }
}
