package com.ronsolomon.solom.movieproject_v2;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ronsolomon.solom.movieproject_v2.DB.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Task_GetTrailer extends AsyncTask<String, Void, String>{


    private Activity activity;
    String key;

    public Task_GetTrailer(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        return sendHttpRequest(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (s != null){
            try {
                JSONObject object = new JSONObject(s);
                JSONArray arr = object.getJSONArray("results");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject item = arr.getJSONObject(i);
                    key = item.getString("key");
                    String site = item.getString("site");
                    if(site.equals("YouTube"))
                        break;
                }

        } catch (JSONException e) {
                e.printStackTrace();
            }

            ImageView iv = (ImageView)activity.findViewById(R.id.imageView1);
            iv.setTag("https://www.youtube.com/watch?v="+key);

        }
    }

    private String sendHttpRequest(String urlString) {
        BufferedReader input = null;
        HttpURLConnection httpCon = null;
        InputStream input_stream =null;
        InputStreamReader input_stream_reader = null;
        StringBuilder response = new StringBuilder();
        try{
            URL url = new URL(urlString);
            httpCon = (HttpURLConnection)url.openConnection();
            if(httpCon.getResponseCode()!=HttpURLConnection.HTTP_OK){
                Log.e("TAG","Cannot Connect to : "+ urlString);
                return null;
            }

            input_stream = httpCon.getInputStream();
            input_stream_reader = new InputStreamReader(input_stream);
            input = new BufferedReader(input_stream_reader);
            String line ;
            while ((line = input.readLine())!= null){
                response.append(line +"\n");
            }



        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(input!=null){
                try {
                    input_stream_reader.close();
                    input_stream.close();
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(httpCon != null){
                    httpCon.disconnect();
                }
            }
        }
        return response.toString();
    }

}
