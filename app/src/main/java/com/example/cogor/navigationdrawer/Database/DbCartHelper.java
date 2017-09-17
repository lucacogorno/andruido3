package com.example.cogor.navigationdrawer.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cogor on 16/09/2017.
 */

public class DbCartHelper extends SQLiteOpenHelper {

    public static final String SQL_CREATE_TABLE = " CREATE TABLE IF NOT EXISTS " + DbCart.CartInit.TABLE_NAME + "(" +
            DbCart.CartInit.COLUMN_NAME_USERNAME + " TEXT, " + DbCart.CartInit.COLUMN_NAME_PRODID + " INTEGER, "
            + DbCart.CartInit.COLUMN_NAME_QUANTITY + " INTEGER, " + DbCart.CartInit.COLUMN_NAME_SINGLEAMOUNT + " REAL, " + "PRIMARY KEY(" + DbCart.CartInit.COLUMN_NAME_USERNAME + ", " + DbCart.CartInit.COLUMN_NAME_PRODID + "));";
    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + DbCart.CartInit.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DbCart.db";


    public DbCartHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DROP_TABLE);
            onCreate(db);
    }

    public void destroyDb(SQLiteDatabase db)
    {
        db.execSQL(SQL_DROP_TABLE);
    }
}
