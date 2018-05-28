package com.example.brandonfadairo.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brandonfadairo.popularmovies.Utils.MovieHelper;
import com.example.brandonfadairo.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Movie>>, SharedPreferences.OnSharedPreferenceChangeListener{

    private static String LOG_TAG = MovieActivity.class.getName();

    private static final int MOVIE_LOADER_ID = 1;

    private MovieAdapter movieAdapter;

    private GridView movieGridView;

    private TextView emptyView;

    private ProgressBar loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        //Find the GridView list ID
        movieGridView = findViewById(R.id.grid_view);

        //Find the Empty TextView ID and set it to the EmptyView
        emptyView = findViewById(R.id.empty_view);
        movieGridView.setEmptyView(emptyView);

        //Find the ProgressBar view ID
        loadingView = findViewById(R.id.bar_view);



        //Check the internet connection of the phone
        ConnectivityManager cm =
                (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        // Create a new adapter that takes an empty list of books as input
        movieAdapter = new MovieAdapter(this, new ArrayList<Movie>());

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
        movieGridView.setAdapter(movieAdapter);
        //Enable the vertical scrollbar on the view
        movieGridView.setVerticalScrollBarEnabled(true);

        //Create a new OnItemClickListener so that we may open the DetailActivity
        //And feed in data from the MovieActivity
        movieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Get the current Movie so we can extract fields from it
                Movie currentMovie = (Movie) adapterView.getItemAtPosition(position);

                //Display a Toast that shows the current movie title. FOR TESTING PURPOSES
                /*Toast movieToast = Toast.makeText(getApplicationContext(), "Movie: " + currentMovie.getTitle(), Toast.LENGTH_LONG);
                movieToast.show();
                */

                //Create a new Intent to open the DetailActivity
                //Put the String extras from the movie fields into the Intents
                Intent detailOpen = new Intent(MovieActivity.this, DetailActivity.class);
                detailOpen.putExtra(getString(R.string.title), currentMovie.getTitle());
                detailOpen.putExtra(getString(R.string.date), currentMovie.getDate());
                detailOpen.putExtra(getString(R.string.poster), currentMovie.getPoster());
                detailOpen.putExtra(getString(R.string.average), currentMovie.getAverage());
                detailOpen.putExtra(getString(R.string.backdrop), currentMovie.getBackdrop());
                detailOpen.putExtra(getString(R.string.synopsis), currentMovie.getSynopsis());
                detailOpen.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                //Start the detail activity
                startActivity(detailOpen);
            }
        });
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
        //Clear the adapter of previous Movie data
        movieAdapter.clear();
        // Set empty state text to display "No Movie found."
        emptyView.setText(R.string.no_movies_found);
        // Sets the loading bar to gone when loading is finished
        loadingView.setVisibility(View.GONE);

        // If there is a valid list of {@link Movies}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (movies != null && !movies.isEmpty()) {
            movieAdapter.addAll(movies);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        //Clear the adapter of previous data
        movieAdapter.clear();
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
        // Get the loader manager
        LoaderManager loaderManager = getLoaderManager();
        //Restart the loader when SharedPreferences are changed
        loaderManager.restartLoader(MOVIE_LOADER_ID, null, this);

        //Set the progress bar to visible
        loadingView.setVisibility(View.VISIBLE);

        //Set the emptyView text to searching
        emptyView.setText(R.string.searching_movies);

        //Set the title of the activity to the new Sort Order (I.e. Most Popular/Top Rated)
        this.setTitle(MovieHelper.getSortOrder());

        //Create a new adapter
        movieAdapter = new MovieAdapter(this, new ArrayList<Movie>());

        //Set the new adapter
        movieGridView.setAdapter(movieAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Unregister the OnSharedPreferenceChangeListener
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
