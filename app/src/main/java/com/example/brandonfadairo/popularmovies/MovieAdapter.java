package com.example.brandonfadairo.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.brandonfadairo.popularmovies.Utils.MovieHelper;
import com.example.brandonfadairo.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    public String LOG_TAG = MovieAdapter.class.getName();

    private ArrayList<Movie> mMovies;
    private final Context mContext;
    private final AdapterOnClick clickHandler;

    public MovieAdapter(Context context, ArrayList<Movie> movies, AdapterOnClick onClickHandler) {
        mMovies = movies;
        mContext = context;
        clickHandler = onClickHandler;
    }

    public interface AdapterOnClick {

        void onClick(Movie movie);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        // create a new view
        LayoutInflater inflater = LayoutInflater.from(context);
        View movieView =
                inflater.inflate(R.layout.movie_list, parent, false);
        // set the view's size, margins, padding and layout parameters
        ViewHolder vh = new ViewHolder(movieView, context);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Movie currentMovie = mMovies.get(position);

        ImageView movieImage = holder.movieImage;

        Picasso.with(mContext)
                .load(MovieHelper.buildImage(currentMovie.getPoster(), mContext))
                .error(R.drawable.ic_launcher_background)
                .into(movieImage);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView movieImage;
        public View layout;
        public RelativeLayout relativeLayout;

        ViewHolder(View itemView, final Context context) {
            super(itemView);
            layout = itemView;
            movieImage = itemView.findViewById(R.id.movie_poster);
            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovies.get(adapterPosition);
            clickHandler.onClick(movie);
        }
    }
}
