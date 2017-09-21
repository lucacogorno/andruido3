package com.example.cogor.navigationdrawer;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cogor on 19/09/2017.
 */

public class MyOrderItemListAdapter extends BaseAdapter {

    ArrayList<OrderItem> orderItems;
    Activity activity;

    public MyOrderItemListAdapter(ArrayList<OrderItem> orderItems, Activity activity) {
        this.orderItems = orderItems;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return orderItems.size();
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
        View view = activity.getLayoutInflater().inflate(R.layout.order_list_product, null);
        final OrderItem orderItem = orderItems.get(position);

        TextView itemListName = (TextView) view.findViewById(R.id.itemProductlistName);
        TextView quantity = (TextView) view.findViewById(R.id.itemProductlistQuantity);
        TextView amount = (TextView) view.findViewById(R.id.itemProductlistAmount);

        itemListName.setText(orderItem.getProdName());
        quantity.setText(String.valueOf(orderItem.getQuantity()));
        amount.setText(String.valueOf(orderItem.getAmount()));

        return view;
    }

}


