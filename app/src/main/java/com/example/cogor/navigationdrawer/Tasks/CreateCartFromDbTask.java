package com.example.cogor.navigationdrawer.Tasks;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cogor.navigationdrawer.Database.DbCart;
import com.example.cogor.navigationdrawer.Database.DbCartHelper;
import com.example.cogor.navigationdrawer.Fragments.OrderInfoFragment;
import com.example.cogor.navigationdrawer.R;

import java.util.ArrayList;

/**
 * Created by cogor on 17/09/2017.
 */

public class CreateCartFromDbTask extends AsyncTask<Object, Object, ArrayList<String>>{
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
    protected ArrayList<String> doInBackground(Object... params) {
        ArrayList<String> toReturn = new ArrayList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        String username = prefs.getString("Username", null);
        DbCartHelper dbCartHelper = new DbCartHelper(activity.getApplicationContext());
        SQLiteDatabase db = dbCartHelper.getReadableDatabase();

        Cursor checkCursor = db.query(DbCart.CartInit.TABLE_NAME , null, "username = ?" , new String[]{username}, null, null, null);

        while(checkCursor.moveToNext())
        {

                toReturn.add(checkCursor.getString(checkCursor.getColumnIndex("prodname")) + ", " + + checkCursor.getDouble(checkCursor.getColumnIndex("singleamount")) + ", " + checkCursor.getInt(checkCursor.getColumnIndex("quantity")));
                totalAmount += checkCursor.getDouble(checkCursor.getColumnIndex("singleamount"));
        }
        db.close();
        return toReturn;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        ListView lv = (ListView) view.findViewById(R.id.cartItems);
        TextView cartAmount = (TextView) view.findViewById(R.id.totalAmount);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, strings);
        cartAmount.setText(Double.toString(totalAmount));
        lv.setAdapter(arrayAdapter);

        Button confirmButton = (Button) view.findViewById(R.id.confirmOrder);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               activity.getFragmentManager().beginTransaction().replace(R.id.content_frame, new OrderInfoFragment()).addToBackStack(null).commit();

            }
        });


    }
}
