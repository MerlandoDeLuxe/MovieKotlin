package com.example.moviekotlin

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Rating(@SerializedName("kp") val ratingKP: String) :Serializable{
}