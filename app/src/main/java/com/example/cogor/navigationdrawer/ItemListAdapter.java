package com.example.cogor.navigationdrawer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.cogor.navigationdrawer.Fragments.ItemFragment;

import java.util.ArrayList;

/**
 * Created by cogor on 19/09/2017.
 */

public class ItemListAdapter extends BaseAdapter{

    ArrayList<Item> items;
    Activity activity;
    public ItemListAdapter(ArrayList<Item> items, Activity activity)
    {
        this.items = items;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return items.size();
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
            View view = activity.getLayoutInflater().inflate(R.layout.main_list_item, null);
        final Item item = items.get(position);

        TextView itemName = (TextView) view.findViewById(R.id.itemCartName);
        TextView itemPrice = (TextView) view.findViewById(R.id.itemCartQuantity);
        Button detailButton = (Button) view.findViewById(R.id.viewDetailsButton);
        itemName.setText(item.getName());
        itemPrice.append(item.getPrice());
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemFragment itemFragment = new ItemFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("arg", item.getId());
                itemFragment.setArguments(bundle);
                android.app.FragmentManager fragmentManager = activity.getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, itemFragment).addToBackStack(null).commit();

            }
        });
        return view;
    }
}
