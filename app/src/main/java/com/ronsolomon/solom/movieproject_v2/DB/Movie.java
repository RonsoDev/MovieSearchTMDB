package com.ronsolomon.solom.movieproject_v2.DB;

import android.widget.RatingBar;

public class Movie {

    private String title;
    private String overview;
    private String url;
    private String release_date;
    private int id;
    private String api_id;
    private String key;
    private String vote;
    private double myVote;
    private String watched;

    public Movie(String title, String overview, String url, String release_date, int id, String api_id, String key, String vote, double myVote) {
        this.title = title;
        this.overview = overview;
        this.url = url;
        this.release_date = release_date;
        this.id = id;
        this.api_id = api_id;
        this.key = key;
        this.vote = vote;
        this.myVote = myVote;
    }

    public Movie(String title, String overview, String url, String release_date, String api_id, String vote, double myVote, String watched) {
        this.title = title;
        this.overview = overview;
        this.url = url;
        this.release_date = release_date;
        this.api_id = api_id;
        this.vote = vote;
        this.myVote = myVote;
        this.watched = watched;
    }

    public Movie(String title, String overview, String url, String release_date, String api_id, String key, String vote, double myVote) {
        this.title = title;
        this.overview = overview;
        this.url = url;
        this.release_date = release_date;
        this.api_id = api_id;
        this.key = key;
        this.vote = vote;
        this.myVote = myVote;
    }

    public Movie(String title, String overview, String url, String release_date, int id, String api_id, String vote, double myVote) {
        this.title = title;
        this.overview = overview;
        this.url = url;
        this.release_date = release_date;
        this.id = id;
        this.api_id = api_id;
        this.vote = vote;
        this.myVote = myVote;
    }

    public Movie(String title, String overview, String url, String release_date, String api_id, String vote, double myVote) {
        this.title = title;
        this.overview = overview;
        this.url = url;
        this.release_date = release_date;
        this.api_id = api_id;
        this.vote = vote;
        this.myVote = myVote;
    }

    public Movie(String title, String overview, String url, String release_date, int id, String api_id, String key, String vote, double myVote, String watched) {
        this.title = title;
        this.overview = overview;
        this.url = url;
        this.release_date = release_date;
        this.id = id;
        this.api_id = api_id;
        this.key = key;
        this.vote = vote;
        this.myVote = myVote;
        this.watched = watched;
    }

    public Movie(String title, String overview, String url, String release_date, int id, String api_id, String vote, double myVote, String watched) {
        this.title = title;
        this.overview = overview;
        this.url = url;
        this.release_date = release_date;
        this.id = id;
        this.api_id = api_id;
        this.vote = vote;
        this.myVote = myVote;
        this.watched = watched;
    }

    public Movie(String title, String overview, String url, String release_date, String api_id, String key, String vote, double myVote, String watched) {
        this.title = title;
        this.overview = overview;
        this.url = url;
        this.release_date = release_date;
        this.api_id = api_id;
        this.key = key;
        this.vote = vote;
        this.myVote = myVote;
        this.watched = watched;
    }

    public String getWatched() {
        return watched;
    }

    public void setWatched(String watched) {
        this.watched = watched;
    }

    public double getMyVote() {
        return myVote;
    }

    public void setMyVote(double myVote) {
        this.myVote = myVote;
    }

    public Movie(String title, String overview, String url, String release_date, int id, String api_id, String key, String vote) {
        this.title = title;
        this.overview = overview;
        this.url = url;
        this.release_date = release_date;
        this.id = id;
        this.api_id = api_id;
        this.key = key;
        this.vote = vote;
    }

    public Movie(String title, String overview, String url, String release_date, String api_id, String vote) {
        this.title = title;
        this.overview = overview;
        this.url = url;
        this.release_date = release_date;
        this.api_id = api_id;
        this.vote = vote;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public Movie(String title, String overview, String url, String release_date, int id, String api_id, String key) {
        this.title = title;
        this.overview = overview;
        this.url = url;
        this.release_date = release_date;
        this.id = id;
        this.api_id = api_id;
        this.key = key;
    }



    public Movie(String key) {
        this.key = key;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Movie(String title, String overview, String url, String release_date, int id, String api_id) {
        this.title = title;
        this.overview = overview;
        this.url = url;
        this.release_date = release_date;
        this.id = id;
        this.api_id = api_id;
    }

    public Movie(String title, String overview, String url, String release_date) {
        this.title = title;
        this.overview = overview;
        this.url = url;
        this.release_date = release_date;
    }

    public Movie(String title, String overview, String url, String release_date, int id) {
        this.title = title;
        this.overview = overview;
        this.url = url;
        this.release_date = release_date;
        this.id = id;
    }

    public Movie(String title, String overview, String url, String release_date, String api_id) {
        this.title = title;
        this.overview = overview;
        this.url = url;
        this.release_date = release_date;
        this.api_id = api_id;
    }

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApi_id() {
        return api_id;
    }

    public void setApi_id(String api_id) {
        this.api_id = api_id;
    }

    @Override
    public String toString() {
        return title;
    }
}
