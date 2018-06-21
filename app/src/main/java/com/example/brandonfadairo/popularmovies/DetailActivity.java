package com.example.brandonfadairo.popularmovies;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brandonfadairo.popularmovies.Adapters.ReviewAdapter;
import com.example.brandonfadairo.popularmovies.Adapters.VideoAdapter;
import com.example.brandonfadairo.popularmovies.Utils.MovieHelper;
import com.example.brandonfadairo.popularmovies.model.Extras;
import com.example.brandonfadairo.popularmovies.model.Movie;
import com.example.brandonfadairo.popularmovies.model.Review;
import com.example.brandonfadairo.popularmovies.model.Videos;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.brandonfadairo.popularmovies.MovieActivity.database;

public class DetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Extras>> {

    private static final String LOG_TAG = DetailActivity.class.getName();

    private ImageView moviePoster;

    private ImageView movieBackdrop;
    public static FloatingActionButton floatingActionButton;

    private String title, backdrop, releaseDate, plotOverview, poster, average;
    public static boolean isFavorite;

    private RecyclerView.LayoutManager layoutManager;

    private ReviewAdapter reviewAdapter;
    private static Movie movie;

    private ArrayList<Review> mReviews;
    private TextView movieTitle, movieDate, movieAverage, movieSynopsis, trailerTag, reviewTag;
    private RecyclerView recyclerDetailReview, recyclerDetailVideo;

    private int DETAIL_LOADER_ID = 2;
    private VideoAdapter videoAdapter;
    private ArrayList<Videos> mVideos;
    private ArrayList<Extras> mExtras;

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
        trailerTag = findViewById(R.id.trailer_tag);
        reviewTag = findViewById(R.id.review_tag);
        floatingActionButton = findViewById(R.id.favorites_button_fab);
        trailerTag.setVisibility(View.GONE);
        reviewTag.setVisibility(View.GONE);

        //Get the intent that started the activity and put it into a Movie object
        movie = getIntent().getParcelableExtra(getString(R.string.movie_extra));

        //TODO: CHECK IF THE MOVIE IS A FAVORITE ON OPEN ASYNC RELATED STUFF
        new favoriteChecker().execute();

        //TODO Recycler view things
        mReviews = new ArrayList<>();
        mExtras = new ArrayList<>();
        mVideos = new ArrayList<>();

        //Set up both Recyclers
        setupReviewRecycler(mReviews);
        setupVideoRecycler(mVideos);

        //Set up the Loader
        setupLoader();

        recyclerDetailReview.setAdapter(reviewAdapter);
        recyclerDetailVideo.setAdapter(videoAdapter);

        //Retrieve the extras that were put into the Intent
        title = movie.getTitle();
        poster = movie.getPoster();
        backdrop = movie.getBackdrop();
        releaseDate = movie.getDate();
        plotOverview = movie.getSynopsis();
        average = movie.getAverage();


        //Log.v(LOG_TAG, "Backdrop Path: " + MovieHelper.buildImage(backdrop, this));
        //Load the Movie backdrop into the backdrop ImageView with Picasso
        Picasso.with(this)
                .load(MovieHelper.buildImage(backdrop, this))
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .fit()
                .into(movieBackdrop);

        //Load the Mini Movie Poster into the Mini Movie Poster ImageView with Picasso
        Picasso.with(this)
                .load(MovieHelper.buildImage(poster, this))
                .error(R.drawable.ic_launcher_background)
                .into(moviePoster);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatabaseLoader().execute();
                if (isFavorite) {
                    floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.icons8_star));
                    Toast toast = Toast.makeText(getApplicationContext(), "Favorite Deleted", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.icons8_star_filled));
                    Toast toast = Toast.makeText(getApplicationContext(), "Favorite Added", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

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
    public Loader<List<Extras>> onCreateLoader(int i, Bundle bundle) {
        return new ExtrasLoader(this, MovieHelper.buildExtraRequest(movie));
    }

    @Override
    public void onLoadFinished(Loader<List<Extras>> loader, List<Extras> extras) {
        //Clear the current Data in the DataSet
        mExtras.clear();
        mReviews.clear();
        mVideos.clear();
        List<Review> reviews;
        List<Videos> videos;

        // If there is a valid list of {@link Movies}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (extras != null && !extras.isEmpty()) {
            mExtras.addAll(extras);
            for (int i = 0; i < mExtras.size(); i++) {
                reviews = extras.get(i).getReviews();
                mReviews.addAll(reviews);
                //Log.v(LOG_TAG, mReviews.get(i).getAuthor());
                videos = extras.get(i).getVideos();
                mVideos.addAll(videos);
            }
            if (mReviews.size() >= 1) {
                reviewTag.setVisibility(View.VISIBLE);
            }

            if (mVideos.size() >= 1) {
                trailerTag.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Extras>> loader) {
        mVideos.clear();
        mReviews.clear();
        mExtras.clear();
    }

    private void setupVideoRecycler(ArrayList<Videos> videos) {
        recyclerDetailVideo = findViewById(R.id.recycler_view_detail_trailer);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerDetailVideo.setLayoutManager(layoutManager);
        //Create the Video Adapter
        videoAdapter = new VideoAdapter(this, videos);
    }

    private void setupReviewRecycler(ArrayList<Review> reviews) {
        recyclerDetailReview = findViewById(R.id.recycler_view_detail_review);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerDetailReview.setLayoutManager(layoutManager);
        //Create the adapter
        reviewAdapter = new ReviewAdapter(this, reviews);
    }

    private void setupLoader() {
        //Loader Info
        //Get the LoaderManager
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(DETAIL_LOADER_ID, null, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Release the YoutubeThumbnail loader to avoid leaks
        if (VideoAdapter.youtubeMap == null) return;
        for (YouTubeThumbnailLoader thumbnailLoader : VideoAdapter.youtubeMap.values()) {
            thumbnailLoader.release();
        }
    }

    private static class DatabaseLoader extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            if (isFavorite) {
                Log.v(LOG_TAG, "Movie is Favorite: Deleted");
                database.movieDao().deleteMovie(movie);
                for (int i = 0; i < database.movieDao().getAllFavorites().size(); i++) {
                    Log.v(LOG_TAG, "Movie: " + database.movieDao().getAllFavorites().get(i).getTitle());
                }

            } else {
                Log.v(LOG_TAG, "Movie is not Favorite: Added");
                database.movieDao().insertOnlySingleMovie(movie);
                for (int i = 0; i < database.movieDao().getAllFavorites().size(); i++) {
                    Log.v(LOG_TAG, "Movie: " + database.movieDao().getAllFavorites().get(i).getTitle());
                }
            }
            return null;
        }
    }

    private static class favoriteChecker extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            isFavorite = database.movieDao().fetchOneMoviesByMovieId(movie.getId()) != null;
            Log.v(LOG_TAG, "Favorite: " + isFavorite);
            return isFavorite;
        }
    }
}