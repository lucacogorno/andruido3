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

    void addToCart(Item item)
    {
        if(cartMap.containsKey(item))
        cartMap.put(item, cartMap.get(item) + 1);
        else
            cartMap.put(item, 1);

        cart_Number += cartMap.get(item);
    }

    int getQuantity(Item item)
    {
        return cartMap.get(item);
    }

    double getValue()
    {
        return cart_Number;
    }

}
