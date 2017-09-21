package com.example.cogor.navigationdrawer;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.cogor.navigationdrawer.Database.DbCart;
import com.example.cogor.navigationdrawer.Database.DbCartHelper;
import com.example.cogor.navigationdrawer.Fragments.CartFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by cogor on 19/09/2017.
 */

public class CartListAdapter extends BaseAdapter {

    ArrayList<CartItem> cartItems;
    Activity activity;

    public CartListAdapter(ArrayList<CartItem> cartItems, Activity activity) {
        this.cartItems = cartItems;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.cart_list_item, null);
        final CartItem item = cartItems.get(position);

        TextView itemName = (TextView) view.findViewById(R.id.itemCartName);
        TextView itemQuantity = (TextView) view.findViewById(R.id.itemCartQuantity);
        TextView itemAmount = (TextView) view.findViewById(R.id.itemCartAmount);
        Button increment = (Button) view.findViewById(R.id.incrementCart);
        Button decrement = (Button) view.findViewById(R.id.decrementCart);
        itemName.setText(item.getProdName());
        itemAmount.append(item.getAmount());
        itemQuantity.append(Double.toString(item.getQuantity()));
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbCartHelper dbCartHelper = new DbCartHelper(activity.getApplicationContext());
                ContentValues contentValues = new ContentValues();
                SQLiteDatabase db = dbCartHelper.getWritableDatabase();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
                String username = prefs.getString("Username", null);

                String[] toCheck = new String[]{username, item.getId()};

                String[] toUpdate = new String[]{"quantity", "singleamount"};
                Cursor infoCursor = db.query(DbCart.CartInit.TABLE_NAME, toUpdate, "username = ? AND prodid = ?", toCheck, null, null, null);
                infoCursor.moveToFirst();

                int currentQuantity = infoCursor.getInt(infoCursor.getColumnIndex(DbCart.CartInit.COLUMN_NAME_QUANTITY));
                double currentAmount = infoCursor.getDouble(infoCursor.getColumnIndex(DbCart.CartInit.COLUMN_NAME_SINGLEAMOUNT));
                Double toInsert = currentAmount + (currentAmount / item.getQuantity());
                toInsert = Math.round(toInsert*100.0)/100.0;
                contentValues.put(DbCart.CartInit.COLUMN_NAME_QUANTITY, currentQuantity + 1);
                contentValues.put(DbCart.CartInit.COLUMN_NAME_SINGLEAMOUNT, toInsert);
                db.update(DbCart.CartInit.TABLE_NAME, contentValues, "username = ? AND prodid = ?", toCheck);
                reloadFrag(activity);
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbCartHelper dbCartHelper = new DbCartHelper(activity.getApplicationContext());
                ContentValues contentValues = new ContentValues();
                SQLiteDatabase db = dbCartHelper.getWritableDatabase();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
                String username = prefs.getString("Username", null);

                String[] toCheck = new String[]{username, item.getId()};

                String[] toUpdate = new String[]{"quantity", "singleamount"};
                Cursor infoCursor = db.query(DbCart.CartInit.TABLE_NAME, toUpdate, "username = ? AND prodid = ?", toCheck, null, null, null);
                infoCursor.moveToFirst();

                int currentQuantity = infoCursor.getInt(infoCursor.getColumnIndex(DbCart.CartInit.COLUMN_NAME_QUANTITY));
                double currentAmount = infoCursor.getDouble(infoCursor.getColumnIndex(DbCart.CartInit.COLUMN_NAME_SINGLEAMOUNT));
                int newQuantity = currentQuantity - 1;
                if (newQuantity == 0) {
                    db.delete(DbCart.CartInit.TABLE_NAME, "username = ? AND prodid = ?", toCheck);
                    reloadFrag(activity);
                    return;
                }

                Double toInsert = currentAmount - (currentAmount / item.getQuantity());
                toInsert = Math.round(toInsert*100.0)/100.0;
                contentValues.put(DbCart.CartInit.COLUMN_NAME_QUANTITY, currentQuantity - 1);
                contentValues.put(DbCart.CartInit.COLUMN_NAME_SINGLEAMOUNT, toInsert);
                db.update(DbCart.CartInit.TABLE_NAME, contentValues, "username = ? AND prodid = ?", toCheck);
                reloadFrag(activity);
            }
        });
        return view;
    }

    public void reloadFrag(Activity activity) {
        Fragment currentFragment = activity.getFragmentManager().findFragmentById(R.id.content_frame);
        if (currentFragment instanceof CartFragment) {
            android.app.FragmentTransaction fragTransaction = activity.getFragmentManager().beginTransaction();
            fragTransaction.detach(currentFragment);
            fragTransaction.attach(currentFragment);
            fragTransaction.commit();
        }
    }

}


