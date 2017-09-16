package com.example.cogor.navigationdrawer.Database;

import android.provider.BaseColumns;

/**
 * Created by cogor on 16/09/2017.
 */

public final class DbCart {

    private DbCart() {}


public static class CartInit implements BaseColumns
{
    public static final String TABLE_NAME = "cart";
    public static final String COLUMN_NAME_USERNAME = "username";
    public static final String COLUMN_NAME_PRODID = "prodId";
    public static final String COLUMN_NAME_QUANTITY = "quantity";
    public static final String COLUMN_NAME_SINGLEAMOUNT = "singleamount";
}



}
