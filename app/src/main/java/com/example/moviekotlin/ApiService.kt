package com.example.moviekotlin

import androidx.room.Query
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
//
//    @Headers("X-API-KEY:0D3X4X9-XSFMX8F-H0J011Y-VWJHVGQ")
//    @GET("movie?rating.kp=4-8&year=2000-2010&isSerises=false&sortfield=votes.kp&votes.kp=1000-99999&&limit=100&sorttype=-1")
//    fun loadMovies():Single<MovieDocs>

    @Headers("X-API-KEY:0D3X4X9-XSFMX8F-H0J011Y-VWJHVGQ")
    @GET("movie?&rating.imdb=8-10&votes.filmCritics=150-666666&limit=40")
    fun loadMovies(@retrofit2.http.Query("page") page: Int): Single<MovieDocs>

    @Headers("X-API-KEY:0D3X4X9-XSFMX8F-H0J011Y-VWJHVGQ")
    @GET("movie/{id}")
    fun loadTrailers(@Path("id") id: Int): Single<Videos>

    @Headers("X-API-KEY:0D3X4X9-XSFMX8F-H0J011Y-VWJHVGQ")
    @GET("review")
    fun loadReviews(
        @retrofit2.http.Query("movieId") movieId: Int,
        @retrofit2.http.Query("page") page: Int
    ): Single<ReviewDocs>
}