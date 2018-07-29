package com.example.nirnaymittal.restraunt;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nirnay Mittal on 28-07-2018.
 */

public class database extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "Restaurant.db";
    private static final String TABLE_NAME = "Requests";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "FIRST_NAME";
    private static final String COL_3 = "LAST_NAME";
    private static final String COL_4 = "PHONE";
    private static final String COL_5 = "ADDRESS";
    private static final String COL_6 = "RESTAURANT_TYPE";
    private static final String COL_7 = "REQUEST_TYPE";

    public database(Context context) {
        super(context,DATABASE_NAME , null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT , FIRST_NAME TEXT , LAST_NAME TEXT , PHONE INTEGER, ADDRESS TEXT , RESTAURANT_NAME  TEXT , REQUEST_TYPE INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME );
    }


    public boolean insert(String firstname, String lastame , String phone , String address , String restaurantname ,int restauranttype)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2 , firstname);
        cv.put(COL_3 , lastame);
        cv.put(COL_4 , phone);
        cv.put(COL_5 , address);
        cv.put(COL_6 , restaurantname);
        cv.put(COL_7 , restauranttype);

        long result = db.insert(TABLE_NAME , null,cv);

        db.close();

        if(result == -1)
            return false;
        else
            return true;

    }

}
