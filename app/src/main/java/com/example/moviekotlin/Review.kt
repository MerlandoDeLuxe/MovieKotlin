package com.example.moviekotlin

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Review(
    @SerializedName("id")
    val id: Int,
    @SerializedName("movieId")
    val movieId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("review")
    val textReview: String,
    @SerializedName("date")
    val date: Date,
    @SerializedName("author")
    val author: String,
    @SerializedName("createdAt")
    val createdAt: Date,
    @SerializedName("updatedAt")
    val updatedAt: Date
)