package com.example.moviekotlin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MovieDetailActivity : AppCompatActivity() {
    val TAG = "MovieDetailActivity"
    val EXTRA_MOVIE = "movie"
    lateinit var imageViewPoster: ImageView
    lateinit var textViewMovieName: TextView
    lateinit var textViewMovieYear: TextView
    lateinit var textViewMovieDescription: TextView
    lateinit var movie: Movie
    lateinit var viewModel: MovieDetailViewModel
    lateinit var trailerAdapter: MovieTrailerAdapter
    lateinit var recyclerViewTrailerButtons: RecyclerView
    lateinit var reviewAdapter: MovieReviewAdapter
    lateinit var recyclerViewReview: RecyclerView
    lateinit var imageViewStar: ImageView
    var page = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recyclerViewMovies)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (savedInstanceState != null){
            page = savedInstanceState.getInt("page")
        }
//===============================================================================================
        initializeAllElements()
//===============================================================================================
        Glide.with(this)
            .load(movie.poster.url)
            .into(imageViewPoster)
        textViewMovieName.text = movie.name
        textViewMovieYear.text = movie.year.toString()
        textViewMovieDescription.text = movie.description
//===============================================================================================
        viewModel.loadTrailers(movie.id)
        viewModel.trailersLD.observe(this) {
            Log.d(TAG, "onCreate: ${it}")
            trailerAdapter.trailers = it
        }
        trailerAdapter.setOnTrailerClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(it.url)
            startActivity(intent)
        }
//===============================================================================================
        viewModel.loadReviews(movie.id, page)
        viewModel.reviewsLD.observe(this) {
            reviewAdapter.reviewList = it
        }
        reviewAdapter.setOnReachEndListener {
            viewModel.loadReviews(movie.id, page)
        }
//===============================================================================================
        val starOff = ContextCompat.getDrawable(this, android.R.drawable.star_big_off)
        val starOn = ContextCompat.getDrawable(this, android.R.drawable.btn_star_big_on)

        viewModel.loadFavoriteMovie(movie.id).observe(this) {
            if (it == null) {
                imageViewStar.setImageDrawable(starOff)
                imageViewStar.setOnClickListener {
                    viewModel.insertFavoriteMovie(movie)
                }
            } else {
                imageViewStar.setImageDrawable(starOn)
                imageViewStar.setOnClickListener {
                    viewModel.removeFavoriteMovie(movie.id)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("page", page)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun newIntent(context: Context, movie: Movie): Intent {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        return intent
    }

    private fun initializeAllElements() {
        movie = intent.getSerializableExtra(EXTRA_MOVIE) as Movie
        imageViewPoster = findViewById(R.id.imageViewPoster)
        textViewMovieName = findViewById(R.id.textViewMovieName)
        textViewMovieYear = findViewById(R.id.textViewMovieYear)
        textViewMovieDescription = findViewById(R.id.textViewMovieDescription)
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)

        trailerAdapter = MovieTrailerAdapter()
        recyclerViewTrailerButtons = findViewById(R.id.recyclerViewTrailerButtons)
        recyclerViewTrailerButtons.layoutManager = GridLayoutManager(this, 1)
        recyclerViewTrailerButtons.adapter = trailerAdapter

        reviewAdapter = MovieReviewAdapter()
        recyclerViewReview = findViewById(R.id.recyclerViewReview)
        recyclerViewReview.adapter = reviewAdapter
        recyclerViewReview.layoutManager = GridLayoutManager(this, 1)

        imageViewStar = findViewById(R.id.imageViewStar);
    }
}