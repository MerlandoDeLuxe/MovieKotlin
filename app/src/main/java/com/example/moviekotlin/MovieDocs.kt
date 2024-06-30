package com.example.moviekotlin

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieDocs(@SerializedName("docs") var listMovie: MutableList<Movie>) : Serializable{
}