package com.example.brandonfadairo.popularmovies.Utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.brandonfadairo.popularmovies.model.Movie;
import com.example.brandonfadairo.popularmovies.model.Review;
import com.example.brandonfadairo.popularmovies.model.Trailer;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {

    /**
     * Tag for Log messages
     */
    private static String LOG_TAG = NetworkUtils.class.getName();

    /**
     * Query the MovieDB dataset and return as (@link Movie) object to represent multiple movies.
     */
    public static List<Movie> fetchMovieJsonData(String requestURL) {
        //Create URL object
        URL url = createUrl(requestURL);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            Thread.sleep(2000);
            jsonResponse = makeHttpRequest(url);
            Log.v(LOG_TAG, "Json Response: " + jsonResponse);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Extract the relevant fields from the JSON with helper method parseJson
        List<Movie> movies = parseMovieJson(jsonResponse);

        //return the jsonResponse
        return movies;
    }

    /**
     * Query the MovieDB dataset and return as (@link Movie) object to represent multiple movies.
     */
    public static List<Review> fetchReviewJsonData(String requestURL) {
        //Create URL object
        URL url = createUrl(requestURL);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            Thread.sleep(2000);
            jsonResponse = makeHttpRequest(url);
            Log.v(LOG_TAG, "Json Response: " + jsonResponse);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Extract the relevant fields from the JSON with helper method parseJson
        List<Review> reviews = parseExtrasJson(jsonResponse);

        //return the jsonResponse
        return reviews;
    }

    private static URL createUrl(String requestURL) {
        URL url = null;
        try {
            url = new URL(requestURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving Movie Information.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private NetworkUtils() {

    }

    public static List<Movie> parseMovieJson(String json) {

        if (TextUtils.isEmpty(json)) {
            return null;
        }

        Gson gson = new Gson();

        List<Movie> movieStuff = new ArrayList<>();
        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(json);

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of items (or movies).
            JSONArray movieArray = baseJsonResponse.getJSONArray("results");


            for (int i = 0; i < movieArray.length(); i++) {

                JSONObject currentMovie = movieArray.getJSONObject(i);
                //Log.v(LOG_TAG, "Movie: " + currentMovie + movieArray.length());
                Movie movies = gson.fromJson(currentMovie.toString(), Movie.class);

                movieStuff.add(movies);
            }

        } catch (Exception e) {
            Log.v(LOG_TAG, "Error Parsing MovieJSON");
        }

        return movieStuff;
    }

    public static List<Review> parseExtrasJson(String json){

        if (TextUtils.isEmpty(json)){
            return null;
        }

        Gson gson = new Gson();

        List<Review> reviewStuff = new ArrayList<>();
        List<Trailer> trailerStuff = new ArrayList<>();
        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(json);

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of items (or reviews).
            JSONObject baseReviewArray = baseJsonResponse.getJSONObject("reviews");

            JSONArray reviewArray = baseReviewArray.getJSONArray("results");


            for (int i = 0; i < reviewArray.length(); i++) {

                JSONObject currentReview = reviewArray.getJSONObject(i);
                Log.v(LOG_TAG, "Review: " + currentReview + reviewArray.length());
                Review reviews = gson.fromJson(currentReview.toString(), Review.class);

                reviewStuff.add(reviews);
            }

        } catch (Exception e){
            Log.v(LOG_TAG, "Error Parsing Review Json");
        }

        return reviewStuff;
    }


}
