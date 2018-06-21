package com.example.brandonfadairo.popularmovies.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Extras {

    @Expose
    public List<Review> reviews;

    @Expose
    public List<Videos> videos;

    public Extras() {

    }

    public Extras(List<Review> movieReviews, List<Videos> movieVideos) {
        this.reviews = movieReviews;
        this.videos = movieVideos;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Videos> getVideos() {
        return videos;
    }
}
