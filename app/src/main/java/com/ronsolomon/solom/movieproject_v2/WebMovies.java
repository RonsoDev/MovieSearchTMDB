package com.ronsolomon.solom.movieproject_v2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;


import com.ronsolomon.solom.movieproject_v2.DB.Movie;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class WebMovies extends AppCompatActivity {



    @Override
    public void onBackPressed() {

        Intent intent = new Intent(WebMovies.this, MainActivity.class);
        startActivity(intent);
        finish();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//Animation for activity transition
        overridePendingTransition(R.anim.act_transition_slide_in, R.anim.act_transition_slide_out);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        SearchView sv = (SearchView)findViewById(R.id.searchViewWEB);
        sv.setQueryHint(getString(R.string.search));

//Search for a movie online
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Task_Get_MovieList task = new Task_Get_MovieList(WebMovies.this);
                String mov = "";
                try{

                    mov = URLEncoder.encode(query, "UTF-8");
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                String str = "https://api.themoviedb.org/3/search/movie?api_key=c815462b1a8ed6a94723af8fa168bb87&query="+mov;
                task.execute(str);
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.actionDelete:

               break;

        }

        if (id == R.id.actionDelete) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
