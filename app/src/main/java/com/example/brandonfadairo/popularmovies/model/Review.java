package com.example.brandonfadairo.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    /* String used to store the author name */
    @SerializedName("author")
    private String author;

    /*String used to store the review */
    @SerializedName("content")
    private String mReview;

    public Review() {

    }

    public Review (String author, String review){
        this.author = author;
        this.mReview = review;
    }

    /**
     * Get the name of the author
     */
    public String getAuthor(){
        return author;
    }

    /**
     * Get the content of the review
     */
    public String getReview(){
        return mReview;
    }

}
