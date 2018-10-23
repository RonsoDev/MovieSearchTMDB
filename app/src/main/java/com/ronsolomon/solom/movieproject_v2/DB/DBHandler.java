package com.ronsolomon.solom.movieproject_v2.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;

public class DBHandler {

    private DBHelper helper;

    public DBHandler(Context context){
        helper = new DBHelper(context, Constants.DATABASE_NAME, null,
                Constants.DATABASE_VERSION);
    }


    public void addMovie(Movie movie){

        SQLiteDatabase db = null;

        try {

            db = helper.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(Constants.COLUMN_TITLE,movie.getTitle());
            values.put(Constants.COLUMN_OVERVIEW,movie.getOverview());
            values.put(Constants.COLUMN_URL, movie.getUrl());
            values.put(Constants.COLUMN_RELEASE, movie.getRelease_date());
            values.put(Constants.COLUMN_API_ID, movie.getApi_id());
            values.put(Constants.COLUMN_KEY, movie.getKey());
            values.put(Constants.COLUMN_VOTE, movie.getVote());
            values.put(Constants.COLUMN_MY_VOTE, movie.getMyVote());
            values.put(Constants.COLUMN_MY_WATCHED, movie.getWatched());



            db.insert(Constants.TABLE_NAME,null,values);

        }catch (SQLiteException e){

        }finally {
            if(db.isOpen())
                db.close();
        }
    }


    public void deleteMovie(String id){

        SQLiteDatabase db = helper.getWritableDatabase();
        try {


            db.delete(Constants.TABLE_NAME,"_id=?",new String[]{id});
        }catch (SQLiteException e){

        }finally {
            if(db.isOpen())
                db.close();
        }
    }




    public void updateMovie(Movie movie, String id){

        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(Constants.COLUMN_TITLE,movie.getTitle());
            values.put(Constants.COLUMN_OVERVIEW,movie.getOverview());
            values.put(Constants.COLUMN_URL, movie.getUrl());
            values.put(Constants.COLUMN_RELEASE, movie.getRelease_date());
            values.put(Constants.COLUMN_API_ID, movie.getApi_id());
            values.put(Constants.COLUMN_KEY, movie.getKey());
            values.put(Constants.COLUMN_VOTE, movie.getVote());
            values.put(Constants.COLUMN_MY_VOTE, movie.getMyVote());
            values.put(Constants.COLUMN_MY_WATCHED, movie.getWatched());


            db.update(Constants.TABLE_NAME,values, "_id=?",
                    new String[]{id});
        }catch (SQLiteException e){

        }finally {
            if(db.isOpen())
                db.close();
        }
    }


    public ArrayList<Movie> getAllMovies(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = null;
        try{

            cursor = db.query(Constants.TABLE_NAME,null,null,
                    null,null,null,null);

        }catch (SQLiteException e){
            e.getCause();
        }

        ArrayList<Movie> table = new ArrayList<>() ;

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String overview = cursor.getString(2);
            String url = cursor.getString(3);
            String release_date = cursor.getString(4);
            String api_id = cursor.getString(5);
            String key = cursor.getString(6);
            String vote = cursor.getString(7);
            double myVote = cursor.getDouble(8);
            String watched = cursor.getString(9);
            table.add(new Movie(title, overview, url, release_date, id, api_id, key, vote, myVote,watched));

        }
        return table;
    }


    public Movie getMovie(String id1){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = null;
        try{

            cursor = db.query(Constants.TABLE_NAME,null,"_id=?",
                    new String[]{id1},null,null,null);

        }catch (SQLiteException e){
            e.getCause();
        }

        Movie table;

        cursor.moveToFirst();
        int id = cursor.getInt(0);
        String title = cursor.getString(1);
        String overview = cursor.getString(2);
        String url = cursor.getString(3);
        String release_date = cursor.getString(4);
        String api_id = cursor.getString(5);
        String key = cursor.getString(6);
        String vote = cursor.getString(7);
        double myVote = cursor.getDouble(8);
        String watched = cursor.getString(9);


        table = new Movie(title, overview, url, release_date, id, api_id, key, vote, myVote,watched);

        return table;
    }

   public void deleteAll (){

        SQLiteDatabase db = helper.getWritableDatabase();
        try {


            db.delete(Constants.TABLE_NAME,null,null);
        }catch (SQLiteException e){

        }finally {
            if(db.isOpen())
                db.close();
        }


    }
}
