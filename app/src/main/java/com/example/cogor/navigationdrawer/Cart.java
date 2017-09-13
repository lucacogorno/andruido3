package com.example.cogor.navigationdrawer;

import java.util.LinkedHashMap;
import java.util.Map;

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

    public void addToCart(Item item)
    {
        if(cartMap.containsKey(item))
        cartMap.put(item, cartMap.get(item) + 1);
        else
            cartMap.put(item, 1);

        cart_Number += cartMap.get(item);
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
