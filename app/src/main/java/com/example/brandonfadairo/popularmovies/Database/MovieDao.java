package com.example.brandonfadairo.popularmovies.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.brandonfadairo.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insertOnlySingleMovie(Movie movies);

    @Insert
    void insertMultipleMovies(List<Movie> moviesList);

    @Query("SELECT * FROM favorites")
    List<Movie> getAllFavorites();

    @Query("SELECT * FROM favorites WHERE mId = :mId")
    Movie fetchOneMoviesByMovieId(int mId);

    @Delete
    void deleteMovie(Movie movies);

}
