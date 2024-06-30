package com.example.moviekotlin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "MovieDetailViewModel"
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val trailersLD: MutableLiveData<List<Trailer>> = MutableLiveData()
    val reviewsLD: MutableLiveData<List<Review>> = MutableLiveData()
    private val movieDAO = MovieDatabase.getInstance(application).MovieDAO;
    private var page = 1

    fun loadFavoriteMovie(movieId: Int): LiveData<Movie> {
        return movieDAO.loadFavoriteMovie(movieId)
    }

    fun insertFavoriteMovie(movie: Movie) {
        val disposable = movieDAO.insertFavoriteMovie(movie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "insertFavoriteMovie: Успешная вставка movie $movie")
            }, {
                Log.d(TAG, "insertFavoriteMovie: Ошибка вставки movie: $movie = ${it.message}")
            })
        compositeDisposable.add(disposable)
    }

    fun removeFavoriteMovie(movieId: Int){
        val disposable = movieDAO.removeFavoriteMovie(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "removeFavoriteMovie: Успешное удаление $movieId из БД")
            },{
                Log.d(TAG, "removeFavoriteMovie: Ошибка удаления $movieId из БД")
            })
        compositeDisposable.add(disposable)
    }

    fun loadTrailers(id: Int) {
        val disposable = ApiFactory.getInstance()?.loadTrailers(id)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.map { it.trailerList.trailers }
            ?.subscribe({
                if (!it.isEmpty()) {
                    Log.d(TAG, "onCreate: ${it}")
                    trailersLD.value = it
                } else {
                    Log.d(TAG, "loadTrailer: Ошибочка - трейлеров нет")
                }
            },
                { Log.d(TAG, "onCreate: Ошибка: ${it.message}") })

        if (disposable != null) {
            compositeDisposable.add(disposable)
        }
    }

    fun loadReviews(movieId: Int) {
        val disposable = ApiFactory.getInstance()?.loadReviews(movieId, page)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.map { it.reviewList }
            ?.subscribe({
                reviewsLD.value = it
                page++
                Log.d(TAG, "loadReview: review = $it")
                Log.d(TAG, "loadReview: reviewsize = ${it.size}")
                Log.d(TAG, "loadReview: page = $page")
            }, { Log.d(TAG, "loadReview: Ошибка: ${it.message}") })
        if (disposable != null) {
            compositeDisposable.add(disposable)
        }
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}