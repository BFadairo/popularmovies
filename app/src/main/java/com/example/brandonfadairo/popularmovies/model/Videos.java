package com.example.brandonfadairo.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class Videos {

    /* String used to store the key of the trailer */
    @SerializedName("key")
    private String trailerKey;

    /* String used to store the size of the trailer */
    @SerializedName("size")
    private String trailerSize;

    /* String used to store the site of the trailer */
    @SerializedName("site")
    private String trailerSite;

    /* String used to store the id of the trailer */
    @SerializedName("id")
    private String trailerId;

    public Videos() {

    }

    public Videos(String trailerKey, String trailerSize, String trailerSite, String trailerId) {
        this.trailerKey = trailerKey;
        this.trailerSize = trailerSize;
        this.trailerSite = trailerSite;
        this.trailerId = trailerId;
    }

//TODO GETTER METHODS

    /**
     * Used to retrieve the trailer size
     */
    public String getTrailerSize() {
        return trailerSize;
    }

    /**
     * Used to retrieve the trailer Site
     */
    public String getTrailerSite() {
        return trailerSite;
    }

    /**
     * Used to retrieve the trailer Id
     */
    public String getTrailerId() {
        return trailerId;
    }

    /**
     * Used to retrieve the key from the request
     */
    public String getTrailerKey() {
        return trailerKey;

    }
}
