package com.ronsolomon.solom.movieproject_v2;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

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

public class Task_Get_MovieList extends AsyncTask<String, Void, String>{

    private Activity activity;

    public Task_Get_MovieList(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        return sendHttpRequest(strings[0]);
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        final ArrayList<Movie> list = new ArrayList<>();

        if (s != null){
            try {
                JSONObject object = new JSONObject(s);
                JSONArray arr = object.getJSONArray("results");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject item = arr.getJSONObject(i);
                    String api_id = item.getString("id");
                    String title = item.getString("title");
                    String overview = item.getString("overview");
                    String url = item.getString("poster_path");
                    String release_date = item.getString("release_date");
                    String vote = item.getString("vote_average");

                    list.add(new Movie(title, overview, url, release_date, api_id, vote));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayAdapter<Movie> adapter = new ArrayAdapter<Movie>
                    (activity,android.R.layout.simple_list_item_1,list);
            final ListView lv = (ListView)activity.findViewById(R.id.listViewWEB);
            lv.setAdapter( adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View view, int position, long id) {

                    Intent intent = new Intent(activity, EditMovieActivity.class);

                    intent.putExtra("title", list.get(position).getTitle());
                    intent.putExtra("overview", list.get(position).getOverview());
                    intent.putExtra("poster_path", list.get(position).getUrl());
                    intent.putExtra("release_date", list.get(position).getRelease_date());
                    intent.putExtra("api_id", list.get(position).getApi_id());
                    intent.putExtra("vote_average", list.get(position).getVote());

                    activity.startActivity(intent);

                }
            });


//Clean search view and search text
            ImageButton clean= (ImageButton) activity.findViewById(R.id.fab);
            final SearchView searchView=(SearchView)activity.findViewById(R.id.searchViewWEB);
            clean.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lv.setAdapter(null);

                    searchView.setQuery("", false);

                }
            });
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
