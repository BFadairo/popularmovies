package com.example.brandonfadairo.popularmovies.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Movie {

    //int used to store the id of the movie
    @SerializedName("id")
    private int mId;

    //String used to store the title of the movie
    @SerializedName("title")
    private String mTitle;

    //String used to store the release date of the movie
    @SerializedName("release_date")
    private String mDate;

    //String used to store the link of the movie poster
    @SerializedName("poster_path")
    private String mPoster;

    //String used to store the link of the backdrop path
    @SerializedName("backdrop_path")
    private String mBackdrop;

    //String used to store the vote average
    @SerializedName("vote_average")
    private String mAverage;

    //String used to store synopsis of the movie
    @SerializedName("overview")
    private String mSynopsis;


    /**
     * Constructor
     **/

    public Movie(){
    }

    public Movie(int id, String title, String date, String poster, String backdrop, String average, String synopsis) {
        mId = id;
        mTitle = title;
        mDate = date;
        mPoster = poster;
        mBackdrop = backdrop;
        mAverage = average;
        mSynopsis = synopsis;
    }

    /**
     * Get the ID of the movie (For Future Use)
     */
    public int getId() {
        return mId;
    }

    /**
     * Get the link of the backdrop for the movie
     **/
    public String getBackdrop() {
        return mBackdrop;
    }


    /**
     * Get the Title of the movie
     **/
    public String getTitle() {
        return mTitle;
    }

    /**
     * Get the date the movie was release
     **/
    public String getDate() {
        return mDate;
    }

    /**
     * Get the link of the poster for the movie
     **/
    public String getPoster() {
        return mPoster;
    }

    /**
     * Get the voter average for the movie
     **/
    public String getAverage() {
        return mAverage;
    }

    /**
     * Get the plot synopsis for the movie
     **/
    public String getSynopsis() {
        return mSynopsis;
    }
}