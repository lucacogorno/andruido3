package com.example.cogor.navigationdrawer;

import android.util.Log;

/**
 * Created by cogor on 10/08/2017.
 */

public class Item {
  private String name;
  private String quantity;
  private String price;
  private String description;

    public Item(String name, String quantity, String price, String description)
    {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }

    public Item(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String toString()
    {
        return name + ", " + quantity + ", " + price;
    }



}
