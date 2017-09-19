package com.example.cogor.navigationdrawer;

/**
 * Created by cogor on 10/08/2017.
 */

public class CartItem {

  private String id;
  private String name;
  private int quantity;
  private String amount;
    private String prodName;


    public CartItem(String id, String name,String prodName, int quantity, String amount)
    {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.amount = amount;
        this.prodName = prodName;
    }

    public CartItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getProdName() {
        return prodName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getId() {
        return id;
    }





}
