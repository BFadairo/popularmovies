package com.example.brandonfadairo.popularmovies.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.brandonfadairo.popularmovies.BuildConfig;
import com.example.brandonfadairo.popularmovies.R;
import com.example.brandonfadairo.popularmovies.model.Videos;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    public static final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> youtubeMap = new HashMap<>();
    private final Context mContext;
    private final String API_KEY = BuildConfig.MY_YOUTUBE_API_KEY;
    private final String LOG_TAG = this.getClass().getName();
    private final ArrayList<Videos> mVideos;

    public VideoAdapter(Context context, ArrayList<Videos> videos) {
        mVideos = videos;
        mContext = context;
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        // create a new view
        LayoutInflater inflater = LayoutInflater.from(context);
        View reviewView =
                inflater.inflate(R.layout.video_list, parent, false);
        // set the view's size, margins, padding and layout parameters

        return new ViewHolder(reviewView);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, int position) {

        final Videos currentVideo = mVideos.get(position);

        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                Log.v(LOG_TAG, "Error Loading Thumbnail");
            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                Log.v(LOG_TAG, "Thumbnail Loaded");
            }
        };


        holder.youTubeThumbnailView.initialize(API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youtubeMap.put(youTubeThumbnailView, youTubeThumbnailLoader);
                youTubeThumbnailLoader.setVideo(currentVideo.getTrailerKey());
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                Log.i(LOG_TAG, "For Testing: Successful Load");
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                Log.v(LOG_TAG, "Error Loading Youtube Thumbnail");

            }
        });
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final YouTubeThumbnailView youTubeThumbnailView;
        final View layout;
        final ImageView playButton;

        ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            youTubeThumbnailView = itemView.findViewById(R.id.youtube_view);
            playButton = itemView.findViewById(R.id.youtube_play_button);
            playButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) mContext, API_KEY, mVideos.get(adapterPosition).getTrailerKey(), 0, true, true);
            mContext.startActivity(intent);
        }
    }
}
