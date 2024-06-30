package com.example.moviekotlin

import com.google.gson.annotations.SerializedName

data class Videos(@SerializedName("videos") val trailerList: TrailerList) {
}