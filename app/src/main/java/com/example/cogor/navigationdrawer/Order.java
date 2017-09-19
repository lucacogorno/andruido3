package com.example.cogor.navigationdrawer;

/**
 * Created by cogor on 19/09/2017.
 */

public class Order {

    private int orderid;
    private String username;
    private String address;
    private double amount;
    private String date;
    private String status;


    public Order(int orderid, String username, String address, double amount, String date, String status)
    {
        this.orderid = orderid;
        this.username = username;
        this.address = address;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    public int getOrderid() {
        return orderid;
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
