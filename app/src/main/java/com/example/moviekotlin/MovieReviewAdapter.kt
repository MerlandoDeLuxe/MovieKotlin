package com.example.moviekotlin

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MovieReviewAdapter :
    RecyclerView.Adapter<MovieReviewAdapter.TextViewReviewElementsHolder>() {
    val TAG = "MovieReviewAdapter"
    var reviewList: List<Review> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private lateinit var onReachEndListenerInterface: OnReachEndListener

    fun setOnReachEndListener(onReachEndListener: OnReachEndListener) {
        onReachEndListenerInterface = onReachEndListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TextViewReviewElementsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.review_cards_template,
                parent,
                false
            )
        return TextViewReviewElementsHolder(view)
    }

    override fun onBindViewHolder(holder: TextViewReviewElementsHolder, position: Int) {
        val review = reviewList.get(position)
        Log.d(TAG, "onBindViewHolder: reviewList = $reviewList")
        var colorRes = 0
        if (review.type.equals("Позитивный")) {
            colorRes = android.R.color.holo_green_light
        } else if (review.type.equals("Нейтральный")) {
            colorRes = android.R.color.holo_orange_light
        } else if (review.type.equals("Негативный")) {
            colorRes = android.R.color.holo_red_light
        } else if (review.type == null) {
            colorRes = android.R.color.holo_orange_light
        }
        val color = ContextCompat.getColor(holder.itemView.context, colorRes)
        holder.constraintLayout.setBackgroundColor(color)
        holder.textViewAuthorName.setBackgroundColor(color)
        holder.textViewReview.setBackgroundColor(color)

        holder.textViewAuthorName.text = review.author

        val resultReview = review.textReview
            .replace("\\r", "")
            .replace("\\n", "")
            .replace("<b>", "")
            .replace("</b>", "")
            .replace("<i>", "")
            .replace("</i>", "");
        holder.textViewReview.text = resultReview

        if (position > reviewList.size && onReachEndListenerInterface != null) {
            onReachEndListenerInterface.onReachEnd()
        }
    }

    fun interface OnReachEndListener {
        fun onReachEnd()
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    class TextViewReviewElementsHolder(itemView: View) : ViewHolder(itemView) {
        val constraintLayout = itemView.findViewById<ConstraintLayout>(R.id.constraintLayout)
        var textViewAuthorName = itemView.findViewById<TextView>(R.id.textViewAuthorName)
        var textViewReview = itemView.findViewById<TextView>(R.id.textViewReview)

    }
}