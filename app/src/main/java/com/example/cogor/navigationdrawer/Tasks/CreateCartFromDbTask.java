package com.example.cogor.navigationdrawer.Tasks;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cogor.navigationdrawer.Database.DbCart;
import com.example.cogor.navigationdrawer.Database.DbCartHelper;
import com.example.cogor.navigationdrawer.R;

import java.util.ArrayList;

/**
 * Created by cogor on 17/09/2017.
 */

public class CreateCartFromDbTask extends AsyncTask<Object, Object, ArrayList<String>>{
    Activity activity;
    View view;
    public CreateCartFromDbTask(Activity activity, View view)
    {
        this.activity = activity;
        this.view = view;
    }

    @Override
    protected ArrayList<String> doInBackground(Object... params) {
        ArrayList<String> toReturn = new ArrayList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        String username = prefs.getString("Username", null);
        DbCartHelper dbCartHelper = new DbCartHelper(activity.getApplicationContext());
        SQLiteDatabase db = dbCartHelper.getReadableDatabase();

        Cursor checkCursor = db.query(DbCart.CartInit.TABLE_NAME , null, "username = ?" , new String[]{username}, null, null, null);
        Log.d("CURSOR", Integer.toString(checkCursor.getCount()));

        while(checkCursor.moveToNext())
        {

                toReturn.add(checkCursor.getInt(checkCursor.getColumnIndex("prodId")) + ", " + checkCursor.getDouble(checkCursor.getColumnIndex("singleamount")) + ", " + checkCursor.getInt(checkCursor.getColumnIndex("quantity")));
        }
        db.close();
        return toReturn;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        ListView lv = (ListView) view.findViewById(R.id.cartItems);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, strings);

        lv.setAdapter(arrayAdapter);


    }
}
