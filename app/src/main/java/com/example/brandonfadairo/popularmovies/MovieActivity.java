package com.example.brandonfadairo.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.brandonfadairo.popularmovies.Adapters.MovieAdapter;
import com.example.brandonfadairo.popularmovies.Utils.MovieHelper;
import com.example.brandonfadairo.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Movie>>,
        SharedPreferences.OnSharedPreferenceChangeListener,
        MovieAdapter.AdapterOnClick{

    private static String LOG_TAG = MovieActivity.class.getName();

    private static final int MOVIE_LOADER_ID = 1;

    private MovieAdapter movieAdapter;

    private TextView emptyView;

    private ProgressBar loadingView;

    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //Find the RecyclerView list ID
        recyclerView = findViewById(R.id.recycler_view_main);

        //Create a GridLayoutManager
        int numberOfColumns = 2;
        layoutManager = new GridLayoutManager(this, numberOfColumns);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.hasFixedSize();

        //Find the Empty TextView ID and set it to the EmptyView
        emptyView = findViewById(R.id.empty_view);

        //Find the ProgressBar view ID
        loadingView = findViewById(R.id.bar_view);



        //Check the internet connection of the phone
        ConnectivityManager cm =
                (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        mMovies = new ArrayList<>();
        // Create a new adapter that takes an empty list of movies as input
        movieAdapter = new MovieAdapter(this, mMovies, this);

        if (isConnected) {
            //Get the LoaderManager
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(MOVIE_LOADER_ID, null, this);
        } else {
            //Set the empty view to No Connection when no internet connection is found
            emptyView.setText(R.string.no_connection);
            // Sets the loading bar to gone when loading is finished
            loadingView.setVisibility(View.GONE);
        }

        // Set the Adapter on the (@link GridView)
        // so the list can be populated in the user interface
        recyclerView.setAdapter(movieAdapter);
        //Enable the vertical scrollbar on the view
        recyclerView.setVerticalScrollBarEnabled(true);

        //Set the title on the MovieActivity to the initial sort order
        this.setTitle(MovieHelper.getSortOrder());

        /*
         * Register MovieActivity as an OnPreferenceChangedListener to receive a callback when a
         * SharedPreference has changed. Please note that we must unregister MovieActivity as an
         * OnSharedPreferenceChanged listener in onDestroy to avoid any memory leaks.
         */
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        return new MovieLoader(this, MovieHelper.buildQueryUrl(this));
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        //Clear the current Data in the DataSet
        mMovies.clear();
        // Set empty state text to display "No Movie found."
        emptyView.setText(R.string.no_movies_found);
        // Sets the loading bar to gone when loading is finished
        loadingView.setVisibility(View.GONE);

        // If there is a valid list of {@link Movies}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (movies != null && !movies.isEmpty()) {
          mMovies.addAll(movies);
          //Set the EmptyView Visibility to Gone once Loaded and data is not null
          emptyView.setVisibility(View.GONE);
        }
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        //Clear the Adapter of previous Data
        mMovies.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Get the MenuInflater and Inflate the Movie Menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //If the Settings button is hit in the Menu, Open the Settings Activity
        if (id == R.id.movie_settings) {
            //Create a new Intent to start the SettingsActivity
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            //Start the settings Activity
            startActivity(startSettingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        //Clear The previous Data
        mMovies.clear();

        // Get the loader manager
        LoaderManager loaderManager = getLoaderManager();
        //Restart the loader when SharedPreferences are changed
        loaderManager.restartLoader(MOVIE_LOADER_ID, null, this);

        //Set the progress bar to visible
        loadingView.setVisibility(View.VISIBLE);

        //Set the emptyView TextView to visible
        emptyView.setVisibility(View.VISIBLE);
        //Set the emptyView text to searching
        emptyView.setText(R.string.searching_movies);

        //Set the title of the activity to the new Sort Order (I.e. Most Popular/Top Rated)
        this.setTitle(MovieHelper.getSortOrder());

        //Create a new adapter
        movieAdapter = new MovieAdapter(this, mMovies, this);

        //Set the new adapter
        recyclerView.setAdapter(movieAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Unregister the OnSharedPreferenceChangeListener
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    //The On Click Listener for the Recycler View
    //When a MoviePoster is selected
    //Open the DetailActivity
    //And put that Movie object into an Intent
    @Override
    public void onClick(Movie movie) {
        Intent detailActivity = new Intent(this, DetailActivity.class);
        detailActivity.putExtra(getString(R.string.movie_extra), movie);
        startActivity(detailActivity);
    }
}
