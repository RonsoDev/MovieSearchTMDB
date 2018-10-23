package com.ronsolomon.solom.movieproject_v2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ronsolomon.solom.movieproject_v2.DB.DBHandler;
import com.ronsolomon.solom.movieproject_v2.DB.Movie;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayList<Movie> list;
    private ArrayAdapter<Movie> adapter;
    private TextView tvMsg;

    boolean doubleBackToExitPressedOnce = false;

//handling safe exit from app
    @Override
    public void onBackPressed() {



        if (doubleBackToExitPressedOnce) {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.exitTwice), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;

            }

        }, 2000);

    }

//Setting Language

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", Activity.MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings",Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

 //Load language from file
        loadLocale();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//Setting animation for main activity
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);

        final TextView tvMsg = (TextView)findViewById(R.id.textViewMsg);
        final DBHandler handler = new DBHandler(MainActivity.this);
        list = handler.getAllMovies();
        lv =(ListView)findViewById(R.id.listViewMyFilms);
        adapter = new ArrayAdapter<Movie>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);


//Showing welcome message if list is empty
        if (list.isEmpty())
            lv.setVisibility(View.INVISIBLE);
        else{
            tvMsg.setVisibility(View.INVISIBLE);
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogTheme);
                dialog.setTitle(Html.fromHtml("<font color='#919191'>"+(getString(R.string.addMovie)+"</font>")));

                dialog.setPositiveButton(getString(R.string.manual), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, EditMovieActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.setNegativeButton(getString(R.string.web), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, WebMovies.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog.show();
            }
        });


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this, R.style.MyAlertDialogTheme);
                dialog.setTitle(Html.fromHtml("<font color='#919191'>"+(getString(R.string.plsChoose)+"</font>")));
                dialog.setPositiveButton(getString(R.string.share), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//Giving the option to share a trailer within multiple apps
                        Intent waIntent = new Intent(Intent.ACTION_SEND);
                        waIntent.setType("text/plain");
                        String text = "Check Out This Movie:" +
                                " " + list.get(position).getTitle().toString() +"\n";

//if the movie doesn't have a key from Json, then user will get a link to youtube, showing search window with the movie title
                        String text1 = null;
                        try {
                            text1 = list.get(position).getKey().toString();
                        } catch (Exception e) {
                            e.printStackTrace();
                            text1 = "https://www.youtube.com/results?search_query="+list.get(position).getTitle().toString()+"+Trailer";

                        }

                        waIntent.putExtra(Intent.EXTRA_TEXT, text + text1);

                        try {
                            startActivity(Intent.createChooser(waIntent, "Share with a friend"));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(MainActivity.this, "There are no Sharing clients installed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                dialog.setNegativeButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = list.get(position).getId();
                        handler.deleteMovie(id+"");
                        list = handler.getAllMovies();
                        ArrayAdapter<Movie> adapter = new ArrayAdapter<Movie>(MainActivity.this,
                                android.R.layout.simple_list_item_1, list);
                        lv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        if (list.isEmpty()) {
                            lv.setVisibility(View.INVISIBLE);
                            tvMsg.setVisibility(View.VISIBLE);
                        } else{
                            tvMsg.setVisibility(View.INVISIBLE);
                        }
                    }
                });
                dialog.setNeutralButton(R.string.cancel, null);
                dialog.show();

                return true;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, EditMovieActivity.class);
                intent.putExtra("id", list.get(position).getId());
                startActivityForResult(intent, 7);

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        DBHandler handler = new DBHandler(MainActivity.this);


//Choosing language
        if (id == R.id.change_lang) {
            return true;
        }
        if (id == R.id.English){
            setLocale("en");
            finish();
            startActivity(getIntent());
            recreate();

        }else if (id == R.id.Hebrew){
            setLocale("iw");
            finish();
            startActivity(getIntent());
            recreate();
        }

        else if (id == R.id.Spanish){
            setLocale("es");
            finish();
            startActivity(getIntent());
            recreate();
        }
        else if (id == R.id.Arabic){
            setLocale("ar");
            finish();
            startActivity(getIntent());
            recreate();
        }

        switch (id){
//Delete list from Database
            case R.id.deleteList:
                handler.deleteAll();
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                finish();
                startActivity(getIntent());

                break;
//Safe exit from app
            case R.id.exitapp:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();


        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.deleteList) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 7){

            DBHandler handler = new DBHandler(MainActivity.this);
            list=handler.getAllMovies();
            ArrayAdapter<Movie> adapter = new ArrayAdapter<Movie>(MainActivity.this,
                    android.R.layout.simple_list_item_1, list);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Movie Added", Toast.LENGTH_LONG).show();


        }else if (resultCode == 9){
            DBHandler handler = new DBHandler(MainActivity.this);
            list = handler.getAllMovies();
            ArrayAdapter<Movie> adapter = new ArrayAdapter<Movie>(MainActivity.this,
                    android.R.layout.simple_list_item_1, list);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, getString(R.string.updated), Toast.LENGTH_SHORT).show();
        }

    }


}
