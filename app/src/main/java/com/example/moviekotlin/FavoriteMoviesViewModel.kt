package com.example.moviekotlin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class FavoriteMoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDAO = MovieDatabase.getInstance(application).MovieDAO;

    fun loadFavoriteMovies():LiveData<List<Movie>>{
        return movieDAO.loadFavoriteMovies()
    }
}