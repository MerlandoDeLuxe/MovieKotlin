package com.example.moviekotlin

import com.google.gson.annotations.SerializedName

data class ReviewDocs(@SerializedName("docs") val reviewList: List<Review>) {
}