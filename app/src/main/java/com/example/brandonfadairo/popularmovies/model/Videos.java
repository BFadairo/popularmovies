package com.example.brandonfadairo.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class Videos {

    /* String used to store the key of the trailer */
    @SerializedName("key")
    private String trailerKey;

    public Videos() {

    }

    public Videos(String trailerKey) {
        this.trailerKey = trailerKey;
    }

    /**
     * Used to retrieve the key from the request
     */
    public String getTrailerKey() {
        return trailerKey;

    }
}
