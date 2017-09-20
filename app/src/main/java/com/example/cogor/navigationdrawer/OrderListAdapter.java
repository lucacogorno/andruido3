package com.example.cogor.navigationdrawer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.cogor.navigationdrawer.Fragments.OrderProductsFragment;

import java.util.ArrayList;

/**
 * Created by cogor on 19/09/2017.
 */

public class OrderListAdapter extends BaseAdapter{

    ArrayList<Order> orders;
    Activity activity;
    public OrderListAdapter(ArrayList<Order> orders, Activity activity)
    {
        this.orders = orders;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return orders.size();
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
            final View view = activity.getLayoutInflater().inflate(R.layout.order_list_item, null);
        final Order item = orders.get(position);

        TextView orderNumber = (TextView) view.findViewById(R.id.orderNumber);
        TextView orderAdress = (TextView) view.findViewById(R.id.orderAdress);
        TextView orderDate = (TextView) view.findViewById(R.id.orderDate);
        TextView orderUser = (TextView) view.findViewById(R.id.orderUser);
        Button detailButton = (Button) view.findViewById(R.id.orderDetails);

        orderNumber.append(Integer.toString(item.getOrderid()));
        orderDate.append(item.getDate());
        orderAdress.append(item.getAddress());
        orderUser.append(item.getUsername());

        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                OrderProductsFragment orderProductsFragment = new OrderProductsFragment();
                bundle.putInt("OrderId", item.getOrderid());
                orderProductsFragment.setArguments(bundle);
                activity.getFragmentManager().beginTransaction().replace(R.id.content_frame, orderProductsFragment).commit();
            }
        });


        return view;
    }


    }


