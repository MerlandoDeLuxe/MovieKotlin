package com.example.moviekotlin

import com.google.gson.annotations.SerializedName

data class Trailer(@SerializedName("url") val url: String, @SerializedName("name") val name: String) {
}