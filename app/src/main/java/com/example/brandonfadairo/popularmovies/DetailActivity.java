package com.example.brandonfadairo.popularmovies;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.brandonfadairo.popularmovies.Adapters.ReviewAdapter;
import com.example.brandonfadairo.popularmovies.Utils.MovieHelper;
import com.example.brandonfadairo.popularmovies.model.Movie;
import com.example.brandonfadairo.popularmovies.model.Review;
import com.example.brandonfadairo.popularmovies.model.Trailer;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Review>> {

    private final String LOG_TAG = DetailActivity.class.getName();

    private ImageView moviePoster;

    private ImageView movieBackdrop;

    private TextView movieTitle, movieDate, movieAverage, movieSynopsis;

    private String title, backdrop, releaseDate, plotOverview, poster, average;

    private RecyclerView recyclerDetailReview;

    private RecyclerView.LayoutManager layoutManager;

    private ReviewAdapter reviewAdapter;

    private ArrayList<Review> mReviews;

    private ArrayList<Trailer> mTrailers;

    private int DETAIL_LOADER_ID = 2;

    private Movie movie;


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
        recyclerDetailReview = findViewById(R.id.recycler_view_detail_review);

        //Get the intent that started the activity and put it into a Movie object
        movie = getIntent().getParcelableExtra(getString(R.string.movie_extra));

        //TODO RECycler view things
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerDetailReview.setLayoutManager(layoutManager);
        mReviews = new ArrayList<>();
        //Create the adapter
        reviewAdapter = new ReviewAdapter(this, mReviews);
        recyclerDetailReview.setAdapter(reviewAdapter);

        //Loader Info
        //Get the LoaderManager
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(DETAIL_LOADER_ID, null, this);


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

    @Override
    public Loader<List<Review>> onCreateLoader(int i, Bundle bundle) {
        return new ExtrasLoader(this, MovieHelper.buildExtraRequest(movie));
    }

    @Override
    public void onLoadFinished(Loader<List<Review>> loader, List<Review> reviews) {
        //Clear the current Data in the DataSet
        mReviews.clear();

        // If there is a valid list of {@link Movies}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (reviews != null && !reviews.isEmpty()) {
            mReviews.addAll(reviews);
        }
        reviewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Review>> loader) {

    }
}
