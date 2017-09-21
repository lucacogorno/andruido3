package com.example.cogor.navigationdrawer;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by cogor on 10/08/2017.
 */

public class Item {

    private long id;
    private String name;
    private String quantity;
    private String price;
    private String description;
    private String image;

    public Item(long id, String name, String quantity, String price, String description) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }

    public Item(long id, String name, String quantity, String price, String description, String image) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
        this.image = image;
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

    public long getId() {
        return id;
    }

    public String toString() {
        return name + ", " + quantity + ", " + price;
    }

    public String getImage() {
        return image;
    }


}
