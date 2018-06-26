package com.example.brandonfadairo.popularmovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favorites")
public class Movie implements Parcelable {

    //int used to store the id of the movie
    @NonNull
    @PrimaryKey
    @SerializedName("id")
    private final int mId;

    //String used to store the title of the movie
    @ColumnInfo(name = "title")
    @SerializedName("title")
    private final String mTitle;

    //String used to store the release date of the movie
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private final String mDate;

    //String used to store the link of the movie poster
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private final String mPoster;

    //String used to store the link of the backdrop path
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    private final String mBackdrop;

    //String used to store the vote average
    @ColumnInfo(name = "voter_average")
    @SerializedName("vote_average")
    private final String mAverage;

    //String used to store synopsis of the movie
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private final String mSynopsis;


    public Movie(@NonNull int mId, String mTitle, String mDate, String mPoster, String mBackdrop, String mAverage, String mSynopsis) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mPoster = mPoster;
        this.mBackdrop = mBackdrop;
        this.mAverage = mAverage;
        this.mSynopsis = mSynopsis;
    }

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