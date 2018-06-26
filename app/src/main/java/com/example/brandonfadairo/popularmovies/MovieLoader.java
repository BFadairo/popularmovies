package com.example.brandonfadairo.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.brandonfadairo.popularmovies.Utils.MovieHelper;
import com.example.brandonfadairo.popularmovies.Utils.NetworkUtils;
import com.example.brandonfadairo.popularmovies.model.Movie;

import java.util.List;

import static com.example.brandonfadairo.popularmovies.MovieActivity.database;

/**
 * Created by Brandon on 1/13/2018.
 */

class MovieLoader extends AsyncTaskLoader<List<Movie>> {


    /**
     * Query URL
     */
    private final String mUrl;

    /**
     * Constructs a new (@Link MovieLoader).
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on the background thread
     */
    @Override
    public List<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        Context context = getContext();
        String topRated = context.getString(R.string.pref_sort_top_rated);
        String mostPopular = context.getString(R.string.pref_sort_popular);
        List<Movie> movies;
        if (MovieHelper.getSortOrder().equals(topRated) || MovieHelper.getSortOrder().equals(mostPopular)) {
            //Perform the network request, parses the response, and extract a list of movies.
            movies = NetworkUtils.fetchMovieJsonData(mUrl);
        } else {
            movies = database.movieDao().getAllFavorites();
        }

        return movies;
    }
}
