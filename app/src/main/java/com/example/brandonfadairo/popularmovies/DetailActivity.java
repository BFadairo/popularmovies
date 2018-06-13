package com.example.brandonfadairo.popularmovies;

import android.app.LoaderManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.brandonfadairo.popularmovies.Utils.MovieHelper;
import com.example.brandonfadairo.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity{

    private final String LOG_TAG = DetailActivity.class.getName();

    private ImageView moviePoster;

    private ImageView movieBackdrop;

    private TextView movieTitle, movieDate, movieAverage, movieSynopsis;

    private String title, backdrop, releaseDate, plotOverview, poster, average;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        //Find the views associated with the detail activity
        movieBackdrop = findViewById(R.id.movie_poster_bin);
        movieTitle = findViewById(R.id.movie_title_bin);
        movieDate = findViewById(R.id.movie_release_bin);
        movieAverage = findViewById(R.id.movie_average_bin);
        movieSynopsis = findViewById(R.id.movie_synopsis_bin);
        moviePoster = findViewById(R.id.movie_poster_mini);

        //Get the intent that started the activity and put it into a Movie object
        Movie movie = getIntent().getParcelableExtra(getString(R.string.movie_extra));

        //Retrieve the extras that were put into the Intent
        title = movie.getTitle();
        poster = movie.getPoster();
        backdrop = movie.getBackdrop();
        releaseDate = movie.getDate();
        plotOverview = movie.getSynopsis();
        average = movie.getAverage();


        Log.v(LOG_TAG, "Backdrop Path: " + MovieHelper.buildImage(backdrop, this));
        //Load the Movie backdrop into the backdrop ImageView with Picasso
        Picasso.with(this)
                .load(MovieHelper.buildImage(backdrop, this))
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .fit()
                .into(movieBackdrop);

        //Load the Mini Movie Poster into the Mini Movie POster ImageView with Picasso
        Picasso.with(this)
                .load(MovieHelper.buildImage(poster, this))
                .error(R.drawable.ic_launcher_background)
                .into(moviePoster);

        //Set the movieTitle view to the Text of the Title
        movieTitle.setText(title);
        //Set the Title of the Detail activity to the Selected Movie
        this.setTitle(title);
        //Set the Text of the DateView to the formatted Date
        movieDate.setText(formatDate());
        //Set the text of the Synopsis View to the movie synopsis
        movieSynopsis.setText(plotOverview);
        //Set the text of the Average view to the movie average
        movieAverage.setText(average);
    }

    private String formatDate() {
        String dt = "";
        try {
            //Parse the release date String to convert it to a Date object
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
            //Format the Date to display Month, Day, and Year (Ex. Feb 07 1994
            dt = new SimpleDateFormat("MMM dd yyyy").format(date);
        } catch (Exception e) {
            Log.v(LOG_TAG, "Error Formatting Date");
        }
        return dt;
    }

}
