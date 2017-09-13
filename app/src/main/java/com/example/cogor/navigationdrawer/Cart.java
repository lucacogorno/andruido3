package com.example.cogor.navigationdrawer;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by cogor on 13/09/2017.
 */

public class Cart {

    Map<Item, Integer> cartMap;

    double cart_Number = 0;


    public Cart()
    {
        cartMap = new LinkedHashMap<>();
    }

    public void addToCart(Item item) {
        if (cartMap.containsKey(item)) {
            cartMap.put(item, cartMap.get(item) + 1);
            Log.d("Exists", item.toString());
        } else {
            cartMap.put(item, 1);
            Log.d("Doesn't exist", item.toString());
        }
        cart_Number += cartMap.get(item);
    }

    public ArrayList<String> getCartItemsString()
    {
        ArrayList<String> toReturn = new ArrayList<>();
        for (Map.Entry<Item, Integer> entry : cartMap.entrySet())
        {
                toReturn.add(entry.getKey().toString() + "\nQuantity:" + entry.getValue());
        }
        return toReturn;
    }

    public double totalAmount()
    {
        double amount = 0;
        for (Map.Entry<Item, Integer> entry : cartMap.entrySet())
        {
            amount += Double.parseDouble(entry.getKey().getPrice()) * entry.getValue().intValue();
        }
        return amount;
    }

    public Set getItems()
    {
        return cartMap.keySet();
    }

    public Map getCart()
    {
        return cartMap;
    }

    public int getQuantity(Item item)
    {
        return cartMap.get(item);
    }

    public double getValue()
    {
        return cart_Number;
    }

}
