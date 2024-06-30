package com.example.moviekotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var viewModel: MainViewModel
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initializeAllElements()
//===============================================================================================
        viewModel.listOfMoviesMLD.observe(this) {
            moviesAdapter.listOfMovies = it
        }
        viewModel.isStillLoading.observe(this) {
            if (it) {
                progressBar.visibility = ProgressBar.VISIBLE
            } else {
                progressBar.visibility = ProgressBar.INVISIBLE
            }
        }
//===============================================================================================
        moviesAdapter.setOnReachEndListener {
            viewModel.loadMovies()
        }
//===============================================================================================
        moviesAdapter.setOnTouchElement {
            val intent = MovieDetailActivity().newIntent(this, it)
            startActivity(intent)
        }
    }

    private fun initializeAllElements() {
        progressBar = findViewById(R.id.progressBar)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        moviesAdapter = MoviesAdapter()
        recyclerView = findViewById(R.id.recyclerViewMovies)
        recyclerView.adapter = moviesAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.itemFavorite) {
            val intent = FavoriteMoviesActivity().newIntent(this)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

}