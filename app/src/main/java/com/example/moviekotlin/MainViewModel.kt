package com.example.moviekotlin

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "MainViewModel"
    val listOfMoviesMLD: MutableLiveData<MutableList<Movie>>
    val isStillLoading: MutableLiveData<Boolean>
    var isAllMoviesDownloaded = false
    var page = 1
    private val compositeDisposable: CompositeDisposable

    fun loadMovies() {
        val isLoading = isStillLoading.value
        if (isLoading == true) {
            return;
        }
        if (!isAllMoviesDownloaded) {
            val disposable = ApiFactory.getInstance()?.loadMovies(page)
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeOn(Schedulers.io())
                ?.doOnSubscribe { isStillLoading.value = true }
                ?.doAfterTerminate { isStillLoading.value = false }
                ?.subscribe({
                    val previoslyLoadedMovies: MutableList<Movie>? = listOfMoviesMLD.value
                    if (previoslyLoadedMovies != null) {
                        previoslyLoadedMovies.addAll(it.listMovie)
                        listOfMoviesMLD.value = previoslyLoadedMovies
                    } else {
                        listOfMoviesMLD.value = it.listMovie
                    }
                    if (it.listMovie.size == 0) {
                        Log.d(TAG, "accept: Объекты закончились")
                        isAllMoviesDownloaded = true
                        isStillLoading.value = false
                    }
                    page++
                }, {
                    Log.d(TAG, "loadMovies: ${it.message}")
                })
            if (disposable != null) {
                compositeDisposable.add(disposable)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    init {
        this.listOfMoviesMLD = MutableLiveData()
        this.isStillLoading = MutableLiveData()
        this.compositeDisposable = CompositeDisposable()
        loadMovies()
    }
}