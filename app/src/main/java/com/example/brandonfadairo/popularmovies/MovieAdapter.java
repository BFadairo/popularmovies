package com.example.brandonfadairo.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.brandonfadairo.popularmovies.Utils.MovieHelper;
import com.example.brandonfadairo.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {


    public MovieAdapter (Activity context, ArrayList<Movie> movie) {
        super(context,0, movie);
    }

    public String LOG_TAG = MovieAdapter.class.getName();

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.movie_list, parent, false);
        }

        // Get the (@link Movie) object located at this position in the list
        Movie currentMovie = getItem(position);

        //Find the ImageView in the movie_list for the movie image
        ImageView movieImage = listItemView.findViewById(R.id.movie_poster);
        //Log.v(LOG_TAG, "Path: " + currentMovie.getPoster());
        Context context = getContext();
        //Use Picasso to load the current movie Poster into the movieImage View
        Picasso.with(context)
                .load(MovieHelper.buildImage(currentMovie.getPoster(), context))
                .error(R.drawable.ic_launcher_background)
                .into(movieImage);

        return listItemView;
}

}
