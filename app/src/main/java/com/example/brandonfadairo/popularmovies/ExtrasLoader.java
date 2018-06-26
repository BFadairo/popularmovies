package com.example.brandonfadairo.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.brandonfadairo.popularmovies.Utils.NetworkUtils;
import com.example.brandonfadairo.popularmovies.model.Extras;

import java.util.List;

class ExtrasLoader extends AsyncTaskLoader<List<Extras>> {
    /**
     * Tag for log messages
     */

    private static final String LOG_TAG = MovieLoader.class.getName();

    /**
     * Query URL
     */
    private final String mUrl;


    /**
     * Constructs a new (@Link ExtrasLoader).
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public ExtrasLoader(Context context, String url) {
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
    public List<Extras> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        //Perform the network request, parses the response, and extract a list of reviews.

        return NetworkUtils.fetchExtrasJsonData(mUrl);
    }
}
