package com.example.brandonfadairo.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    //int used to store the id of the movie
    @SerializedName("id")
    private int mId;

    //String used to store the title of the movie
    @SerializedName("title")
    private String mTitle;

    //String used to store the release date of the movie
    @SerializedName("release_date")
    private String mDate;

    //String used to store the link of the movie poster
    @SerializedName("poster_path")
    private String mPoster;

    //String used to store the link of the backdrop path
    @SerializedName("backdrop_path")
    private String mBackdrop;

    //String used to store the vote average
    @SerializedName("vote_average")
    private String mAverage;

    //String used to store synopsis of the movie
    @SerializedName("overview")
    private String mSynopsis;


    /**
     * Constructor
     **/

    protected Movie(Parcel in) {
        this.mId = in.readInt();
        this.mTitle = in.readString();
        this.mDate = in.readString();
        this.mPoster = in.readString();
        this.mBackdrop = in.readString();
        this.mAverage = in.readString();
        this.mSynopsis = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDate);
        dest.writeString(this.mPoster);
        dest.writeString(this.mBackdrop);
        dest.writeString(this.mAverage);
        dest.writeString(this.mSynopsis);
    }

    /**
     * Get the ID of the movie (For Future Use)
     */
    public int getId() {
        return mId;
    }

    /**
     * Get the link of the backdrop for the movie
     **/
    public String getBackdrop() {
        return mBackdrop;
    }


    /**
     * Get the Title of the movie
     **/
    public String getTitle() {
        return mTitle;
    }

    /**
     * Get the date the movie was release
     **/
    public String getDate() {
        return mDate;
    }

    /**
     * Get the link of the poster for the movie
     **/
    public String getPoster() {
        return mPoster;
    }

    /**
     * Get the voter average for the movie
     **/
    public String getAverage() {
        return mAverage;
    }

    /**
     * Get the plot synopsis for the movie
     **/
    public String getSynopsis() {
        return mSynopsis;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}