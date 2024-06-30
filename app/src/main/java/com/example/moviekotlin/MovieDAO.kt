package com.example.moviekotlin

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable

@Dao
interface MovieDAO {

    @Query("select * from favorite_movies")
    fun loadFavoriteMovies(): LiveData<List<Movie>>

    @Query("select * from favorite_movies where id = :movieId")
    fun loadFavoriteMovie(movieId: Int): LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(movie: Movie): Completable

    @Query("delete from favorite_movies where id = :movieId")
    fun removeFavoriteMovie(movieId: Int):Completable
}