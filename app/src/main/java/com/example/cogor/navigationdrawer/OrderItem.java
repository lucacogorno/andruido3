package com.example.cogor.navigationdrawer;

/**
 * Created by cogor on 10/08/2017.
 */

public class OrderItem {

    private int id;
    private double quantity;
    private double amount;
    private String prodName;


    public OrderItem(int id, String prodName, int quantity, double amount) {
        this.id = id;
        this.quantity = quantity;
        this.amount = amount;
        this.prodName = prodName;
    }

    public OrderItem() {}

    public double getAmount() {
        return amount;
    }

    public String getProdName() {
        return prodName;
    }

    public double getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }


}
