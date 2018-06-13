package com.example.brandonfadairo.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Trailer implements Parcelable {

    /* String used to store the key of the trailer */
    @SerializedName("key")
    private String trailerKey;

    /* String used to store the size of the trailer */
    @SerializedName("size")
    private String trailerSize;

    /* String used to store the site of the trailer */
    @SerializedName("site")
    private String trailerSite;

    /* String used to store the id of the trailer */
    @SerializedName("id")
    private String trailerId;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.trailerKey);
        dest.writeString(this.trailerSize);
        dest.writeString(this.trailerSite);
        dest.writeString(this.trailerId);
    }

    public Trailer() {
    }

    protected Trailer(Parcel in) {
        this.trailerKey = in.readString();
        this.trailerSize = in.readString();
        this.trailerSite = in.readString();
        this.trailerId = in.readString();
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel source) {
            return new Trailer(source);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

//TODO GETTER METHODS
}
