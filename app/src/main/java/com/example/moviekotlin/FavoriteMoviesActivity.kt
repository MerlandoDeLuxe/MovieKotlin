package com.example.moviekotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteMoviesActivity : AppCompatActivity() {
    lateinit var recyclerViewMovies: RecyclerView
    lateinit var adapter: MoviesAdapter
    lateinit var viewModel: FavoriteMoviesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorite_movies)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recyclerViewMovies)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeAllElements()
//===============================================================================================
        viewModel.loadFavoriteMovies().observe(this) {
            adapter.listOfMovies = it
        }
        adapter.setOnReachEndListener({
            viewModel.loadFavoriteMovies()
        })
        adapter.setOnTouchElement({
            val intent = MovieDetailActivity().newIntent(this, it)
            startActivity(intent)
        })
    }

    fun newIntent(context: Context): Intent {
        val intent = Intent(context, FavoriteMoviesActivity::class.java)
        return intent
    }

    fun initializeAllElements() {
        adapter = MoviesAdapter()
        viewModel = ViewModelProvider(this).get(FavoriteMoviesViewModel::class.java)
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies)
        recyclerViewMovies.adapter = adapter
        recyclerViewMovies.layoutManager = GridLayoutManager(this, 2)
    }
}