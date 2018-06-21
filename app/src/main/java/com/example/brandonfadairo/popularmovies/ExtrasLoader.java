package com.example.brandonfadairo.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.brandonfadairo.popularmovies.Utils.NetworkUtils;
import com.example.brandonfadairo.popularmovies.model.Extras;

import java.util.List;

import static com.example.brandonfadairo.popularmovies.DetailActivity.floatingActionButton;
import static com.example.brandonfadairo.popularmovies.DetailActivity.isFavorite;

public class ExtrasLoader extends AsyncTaskLoader<List<Extras>> {
    /**
     * Tag for log messages
     */

    private static final String LOG_TAG = MovieLoader.class.getName();


    /**
     * Query URL
     */
    private String mUrl;

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
        if (isFavorite) {
            floatingActionButton.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icons8_star_filled));
        } else {
            floatingActionButton.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icons8_star));
        }
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
        List<Extras> extras = NetworkUtils.fetchExtrasJsonData(mUrl);

        return extras;
    }
}
