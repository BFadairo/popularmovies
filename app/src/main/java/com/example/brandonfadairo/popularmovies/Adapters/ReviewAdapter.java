package com.example.brandonfadairo.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brandonfadairo.popularmovies.R;
import com.example.brandonfadairo.popularmovies.model.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private final Context mContext;
    public String LOG_TAG = this.getClass().getName();
    private ArrayList<Review> mReviews;

    public ReviewAdapter(Context context, ArrayList<Review> reviews) {
        mReviews = reviews;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        // create a new view
        LayoutInflater inflater = LayoutInflater.from(context);
        View reviewView =
                inflater.inflate(R.layout.review_list, parent, false);
        // set the view's size, margins, padding and layout parameters
        ViewHolder vh = new ViewHolder(reviewView, context);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Review currentReview = mReviews.get(position);

        //Review currentReview = mReviews.get(position);

        TextView authorText = holder.authorReview;

        TextView contentText = holder.contentReview;

        //Set the Author TextView to the current Author
        authorText.setText(currentReview.getAuthor());
        Log.v(LOG_TAG, "Author: " + currentReview.getAuthor());

        //Set the Content TextView to the current Review content
        contentText.setText(currentReview.getReview());

    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView authorReview;
        public TextView contentReview;
        public View layout;

        ViewHolder(View itemView, final Context context) {
            super(itemView);
            layout = itemView;
            authorReview = itemView.findViewById(R.id.author_text);
            contentReview = itemView.findViewById(R.id.content_text);

        }
    }
}
