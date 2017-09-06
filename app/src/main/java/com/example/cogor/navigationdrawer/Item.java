package com.example.cogor.lpaaproject;

import android.util.Log;

/**
 * Created by cogor on 10/08/2017.
 */

public class Item {
    public String name;
    public String quantity;
    public String price;

    public Item(String name, String quantity, String price)
    {
        this.name = name;
        this.quantity = quantity;
        this.price = price;

        Log.d("INSIDE ITEM", name + quantity + price);
    }

    public Item(String name) {
        this.name = name;
    }

    public String toString()
    {
        return name + ", " + quantity + ", " + price;
    }
}
