package com.ronsolomon.solom.movieproject_v2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.ronsolomon.solom.movieproject_v2.DB.DBHandler;
import com.ronsolomon.solom.movieproject_v2.DB.Movie;

public class EditMovieActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etURL;
    private EditText etOV2;
    private Button buttonSave;
    private Button buttonCancel;
    private Button buttonShow;
    private EditText etRelease;
    private EditText etJsonVote;
    private ImageView iv;
    private int id;
    private EditText tr;
    private String key;
    private RatingBar myVote;
    private CheckBox checkBoxWatch;
    private ImageView imageViewPlay;
    private ImageButton imageButtonMyRate;


//Api Task helper
    Task_GetTrailer getTrailer = new Task_GetTrailer(this);

//Back press Management
    @Override
    public void onBackPressed() {
        if (getIntent().hasExtra("id")){
            Intent intent = new Intent(EditMovieActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else{

            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//Animation for activity transition
        overridePendingTransition(R.anim.act_transition_slide_in, R.anim.act_transition_slide_out);

        checkBoxWatch = (CheckBox)findViewById(R.id.checkBoxWatch);

        DBHandler handler = new DBHandler(EditMovieActivity.this);

        id = getIntent().getIntExtra("id", -1);


        etTitle = (EditText)findViewById(R.id.editTextTitle);
        etOV2 = (EditText)findViewById(R.id.editTextOv);
        buttonSave = (Button)findViewById(R.id.buttonSave);
        buttonCancel = (Button)findViewById(R.id.buttonCancel);
        etURL = (EditText)findViewById(R.id.editTextURL);
        buttonShow = (Button) findViewById(R.id.buttonShow);
        etRelease = (EditText)findViewById(R.id.editTextRD);
        etJsonVote = (EditText)findViewById(R.id.editTextJsonVote);
        iv = (ImageView)findViewById(R.id.imageView1);
        tr = (EditText)findViewById(R.id.editTextTR);
        myVote = (RatingBar)findViewById(R.id.myVote);
        imageViewPlay = (ImageView)findViewById(R.id.imageViewPlay);
        imageButtonMyRate = (ImageButton)findViewById(R.id.imageButtonMyRate);



//Coming from DB (MainActivity)
        if (getIntent().hasExtra("id")){

            etTitle.setText(handler.getMovie(String.valueOf(id)).getTitle().toString());

            etOV2.setText(handler.getMovie(id+"").getOverview().toString()+"");

            etURL.setText(handler.getMovie(id+"").getUrl().toString()+"");

            etRelease.setText(handler.getMovie(id+"").getRelease_date().toString()+"");

            tr.setText(handler.getMovie(id+"").getApi_id().toString()+"");

            etJsonVote.setText(handler.getMovie(id+"").getVote().toString()+"");

            myVote.setRating(Float.parseFloat(handler.getMovie(id+"").getMyVote()+""));

//Watched Feature
            if (handler.getMovie(id+"").getWatched().toString().equals("yes"))
                checkBoxWatch.setChecked(true);

            Task_downloadImage downloadImage = new Task_downloadImage(EditMovieActivity.this);
            String [] imgUrl = {etURL.getText().toString()};
            Animation fadeImage = AnimationUtils.loadAnimation(EditMovieActivity.this, R.anim.fade);
            iv.startAnimation(fadeImage);
            downloadImage.execute(imgUrl);

            String str = tr.getText().toString();

            getTrailer.execute(str);




//Coming from WEB (WebSearch Activity)
        }else if(getIntent().hasExtra("title")) {

            etTitle.setVisibility(View.GONE);
            getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
            etTitle.setText(getIntent().getStringExtra("title"));
            etOV2.setText(getIntent().getStringExtra("overview"));
            etURL.setText("https://image.tmdb.org/t/p/w154" + getIntent().getStringExtra("poster_path"));
            etRelease.setText(getString(R.string.release) + " " +getIntent().getStringExtra("release_date"));
            etJsonVote.setText(getIntent().getStringExtra("vote_average"));
            String api_id = getIntent().getStringExtra("api_id");
            myVote.setRating(0);
            tr.setText("https://api.themoviedb.org/3/movie/" + api_id + "/videos?api_key=c815462b1a8ed6a94723af8fa168bb87&language=en-US");
            String str = tr.getText().toString();
            getTrailer.execute(str);

            Task_downloadImage downloadImage = new Task_downloadImage(EditMovieActivity.this);
            String [] imgUrl = {"https://image.tmdb.org/t/p/w154" + getIntent().getStringExtra("poster_path")};
            Animation fadeImage = AnimationUtils.loadAnimation(EditMovieActivity.this, R.anim.fade);
            iv.startAnimation(fadeImage);
            downloadImage.execute(imgUrl);

        }

//Get image from site
        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Task_downloadImage downloadImage = new Task_downloadImage(EditMovieActivity.this);
                String [] imgUrl = { etURL.getText().toString()};
                Animation fadeImage = AnimationUtils.loadAnimation(EditMovieActivity.this, R.anim.fade);
                iv.startAnimation(fadeImage);
                downloadImage.execute(imgUrl);

            }
        });


//Play trailer on YouTube
        imageViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(iv.getTag().toString()));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditMovieActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHandler handler = new DBHandler(EditMovieActivity.this);

                Intent intent = new Intent(EditMovieActivity.this, MainActivity.class);


//Coming from web or manual
                if (id<0){
                    String title = etTitle.getText().toString();
                    String overview = etOV2.getText().toString();
                    String url = etURL.getText().toString();
                    String release_date = etRelease.getText().toString();
                    String api_id = tr.getText().toString();
                    String vote = etJsonVote.getText().toString();
                    double rate = myVote.getRating();
                    String key = null;
                    try {
                        key = iv.getTag().toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String watched ="no";
                    if(checkBoxWatch.isChecked())
                        watched = "yes";

//In order to prevent empty line in the list
                    if(title.isEmpty()){
                        Toast.makeText(EditMovieActivity.this, getString(R.string.cantSave), Toast.LENGTH_LONG).show();
                    }else{
                        handler.addMovie(new Movie(title,overview, url, release_date, api_id, key, vote, rate,watched));
                        setResult(7, intent);
                        startActivity(intent);
                        finish();
                    }




//Coming from the database
                }else{
                    String title = etTitle.getText().toString()+"";
                    String overview = etOV2.getText().toString()+"";
                    String url = etURL.getText().toString()+"";
                    String release_date = etRelease.getText().toString()+"";
                    String api_id = tr.getText().toString()+"";
                    String vote = etJsonVote.getText().toString()+"";
                    String key = iv.getTag().toString()+"";
                    double rate = myVote.getRating();
                        String watched ="no";
                        if(checkBoxWatch.isChecked())
                            watched = "yes";

                    Movie movie = new Movie(title, overview, url, release_date, api_id, vote, key, rate, watched);
                    handler.updateMovie(movie, String.valueOf(id)+"");
                    setResult(9, intent);

                    finish();
               }
            }
        });

    }

}
