package com.example.moviekotlin

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.ImageViewHolder>() {
    val TAG = "MoviesAdapter"

    private lateinit var onReachEndListenerInterface: OnReachEndListener

    fun setOnReachEndListener(onReachEndListener: OnReachEndListener) {
        onReachEndListenerInterface = onReachEndListener
    }

    private lateinit var onTochElementInterface: OnTochElement

    fun setOnTouchElement(onTochElement: OnTochElement) {
        onTochElementInterface = onTochElement
    }

    var listOfMovies: List<Movie> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class ImageViewHolder(itemView: View) : ViewHolder(itemView) {
        val textViewRating = itemView.findViewById<TextView>(R.id.textViewRating)
            get() = field
        val imageViewPoster = itemView.findViewById<ImageView>(R.id.imageViewPoster)
            get() = field
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: $position")
        val movie = listOfMovies.get(position)

        if (movie.poster.url.isNotEmpty()) {
            Glide.with(holder.itemView)
                .load(movie.poster.url)
                .into(holder.imageViewPoster)
        } else {
            Glide.with(holder.itemView)
                .load("https://st.kp.yandex.net/images/no-poster.gif")
                .into(holder.imageViewPoster)
        }

        val rating: Double
        if (movie.rating.ratingKP.length > 3) {
            rating = movie.rating.ratingKP.substring(0, 3).toDouble()
        } else {
            rating = movie.rating.ratingKP.toDouble()
        }
        val backgroundID: Int
        if (rating > 8) {
            backgroundID = R.drawable.cyrle_green
        } else if (rating > 6) {
            backgroundID = R.drawable.cyrcle_orange
        } else {
            backgroundID = R.drawable.cyrcle_red
        }
        val drawable = ContextCompat.getDrawable(holder.textViewRating.context, backgroundID)
        holder.textViewRating.background = drawable
        holder.textViewRating.text = rating.toString()

        if (position >= listOfMovies.size - 10 && onReachEndListenerInterface != null)
            onReachEndListenerInterface.onReachEnd()

        if (onTochElementInterface != null) {
            holder.itemView.setOnClickListener({
                onTochElementInterface.onTouchElement(movie)
            })
        }
    }

    fun interface OnReachEndListener {
        fun onReachEnd()
    }

    fun interface OnTochElement {
        fun onTouchElement(movie: Movie)
    }

    override fun getItemCount(): Int {
        return listOfMovies.size
    }
}