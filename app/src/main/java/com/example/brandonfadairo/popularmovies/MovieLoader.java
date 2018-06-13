package com.example.brandonfadairo.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.brandonfadairo.popularmovies.Utils.NetworkUtils;
import com.example.brandonfadairo.popularmovies.model.Movie;

import java.util.List;

/**
 * Created by Brandon on 1/13/2018.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    /** Tag for log messages */
    private static final String LOG_TAG = MovieLoader.class.getName();


    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new (@Link MovieLoader).
     *
     * @param context of the activity
     * @param url to load data from
     */
    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    /**
     * This is on the background thread
     *
     */
    @Override
    public List<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        //Perform the network request, parses the response, and extract a list of movies.
        List<Movie> movies = NetworkUtils.fetchMovieJsonData(mUrl);

        return movies;
    }
}
