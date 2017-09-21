package com.example.cogor.navigationdrawer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.cogor.navigationdrawer.Fragments.MyOrderProductsFragment;
import com.example.cogor.navigationdrawer.Fragments.OrderProductsFragment;

import java.util.ArrayList;

/**
 * Created by cogor on 19/09/2017.
 */

public class MyOrderListAdapter extends BaseAdapter{

    ArrayList<Order> orders;
    Activity activity;
    public MyOrderListAdapter(ArrayList<Order> orders, Activity activity)
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
            final View view = activity.getLayoutInflater().inflate(R.layout.my_order_list_item, null);
        final Order item = orders.get(position);

        TextView myorderNumber = (TextView) view.findViewById(R.id.myorderNumber);
        TextView myorderAdress = (TextView) view.findViewById(R.id.myorderAdress);
        TextView myorderDate = (TextView) view.findViewById(R.id.myorderDate);
        TextView myorderState = (TextView) view.findViewById(R.id.myorderState);
        Button detailButton = (Button) view.findViewById(R.id.myorderDetails);

        myorderNumber.append(Integer.toString(item.getOrderid()));
        myorderDate.append(item.getDate());
        myorderAdress.append(item.getAddress());
        myorderState.append(item.getStatus());

        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                MyOrderProductsFragment orderProductsFragment = new MyOrderProductsFragment();
                bundle.putInt("OrderId", item.getOrderid());
                orderProductsFragment.setArguments(bundle);
                activity.getFragmentManager().beginTransaction().replace(R.id.content_frame, orderProductsFragment).addToBackStack(MyOrderProductsFragment.class.getName()).commit();
            }
        });


        return view;
    }


    }


