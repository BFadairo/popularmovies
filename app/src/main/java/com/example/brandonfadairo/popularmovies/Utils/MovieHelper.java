package com.example.brandonfadairo.popularmovies.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.example.brandonfadairo.popularmovies.R;


public class MovieHelper {

    private static final String LOG_TAG = MovieHelper.class.getName();

    private static final String BASE_REQUEST_URL = "http://api.themoviedb.org/3/movie/";

    private static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";

    private static final String POPULAR_SEARCH = "popular";

    private static final String TOP_RATED = "top_rated";

    private static final String API_TAG = "api_key";

    private static final String API_KEY = ""; // Insert MovieDB API key here

    private static String sortOrder = "Most Popular";


    /**
     * Helper Method used to determine user sort preference
     *
     * @param context of the activity
     * @return isPopular
     */
    private static String isPopular(Context context) {
        //Get the SharedPreferences
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);

        String keyForSort = context.getString(R.string.pref_sort_key);
        String defaultSort = context.getString(R.string.pref_sort_popular);
        String preferredSort = prefs.getString(keyForSort, defaultSort);
        Log.v(LOG_TAG, "Sort: " + preferredSort);
        String prefSort = POPULAR_SEARCH;

        switch (preferredSort) {
            case "0":
                prefSort = POPULAR_SEARCH;
                sortOrder = "Most Popular";
                break;
            case "1":
                prefSort = TOP_RATED;
                sortOrder = "Top Rated";
                break;
        }

        return prefSort;
    }

    /**
     * Used to determine the imageSize the user has set
     *
     * @param context of the activity
     * @return the preferredSize for images
     */

    private static String imageSizeSet(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String keyForSize = context.getString(R.string.pref_size_key);
        String defaultSize = context.getString(R.string.pref_size_154);
        String preferredSize = prefs.getString(keyForSize, defaultSize);
        String size = context.getString(R.string.pref_size_500);
        //Log.v(LOG_TAG, "Size Sort: " + preferredSize);

        switch (preferredSize) {
            case "0":
                size = context.getString(R.string.pref_size_92);
                break;
            case "1":
                size = context.getString(R.string.pref_size_154);
                break;
            case "2":
                size = context.getString(R.string.pref_size_185);
                break;
            case "3":
                size = context.getString(R.string.pref_size_342);
                break;
            case "4":
                size = context.getString(R.string.pref_size_500);
                break;
            case "5":
                size = context.getString(R.string.pref_size_780);
                break;
        }
        return size;
    }

    /**
     * Used to combine the posterPath with the query path (BASE_IMAGE_URL)
     *
     * @param imagePath of the movie poster
     * @param context   of the activity
     * @return imageLink, the complete image path
     */
    public static String buildImage(String imagePath, Context context) {
        //Create a new Uri called request and parse the BASE_IMAGE_URL
        Uri request = Uri.parse(BASE_IMAGE_URL);
        //Create a string variable to store the Uri
        String imageLink = request.buildUpon()
                //Append the Size of the image
                .appendPath(imageSizeSet(context))
                //Append the image path
                .appendEncodedPath(imagePath)
                .build()
                .toString();

        //Log.v(LOG_TAG, "Image Path: " + link);

        return imageLink;
    }

    /**
     * This method is used to build the queryUrl for MovieDB
     *
     * @param context of the activity
     * @return the completed link
     */
    public static String buildQueryUrl(Context context) {
        //Create a new Uri called request and parse the BASE_IMAGE_URL
        Uri request = Uri.parse(BASE_REQUEST_URL);
        //Create a string variable called link to store the built Uri
        String link = request.buildUpon()
                //Build upon the Uri and append the query
                //With the isPopular Helper Method
                .appendPath(MovieHelper.isPopular(context))
                //Append the API tag and key to the link
                .appendQueryParameter(API_TAG, API_KEY)
                .build()
                .toString();

        //Log.d(LOG_TAG, "Link: " + link);

        return link;
    }

    /**
     * Return the sortOrder to be used in the Detail and Movie Activities
     */
    public static String getSortOrder() {
        return sortOrder;
    }
}
