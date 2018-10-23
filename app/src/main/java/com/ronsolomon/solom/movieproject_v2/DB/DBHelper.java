package com.ronsolomon.solom.movieproject_v2.DB;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBHelper extends SQLiteOpenHelper{


    public DBHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{

            String cmd = "CREATE TABLE " + Constants.TABLE_NAME+
                    " ("+Constants.COLUMN_NAME_ID +" INTEGER PRIMARY KEY, "
                    +Constants.COLUMN_TITLE + " TEXT, " +
                    Constants.COLUMN_OVERVIEW + " TEXT, " +
                    Constants.COLUMN_URL + " TEXT, " +
                    Constants.COLUMN_RELEASE + " TEXT, " +
                    Constants.COLUMN_API_ID + " TEXT, " +
                    Constants.COLUMN_KEY + " TEXT, " +
                    Constants.COLUMN_VOTE + " TEXT , " +
                    Constants.COLUMN_MY_VOTE + " TEXT , " +
                    Constants.COLUMN_MY_WATCHED + " TEXT);";

            db.execSQL(cmd);

        }catch (SQLException e){
            e.getCause();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
