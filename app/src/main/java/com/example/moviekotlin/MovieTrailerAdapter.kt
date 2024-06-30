package com.example.moviekotlin

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MovieTrailerAdapter : RecyclerView.Adapter<MovieTrailerAdapter.TextViewHolder>() {
    private val TAG = "MovieTrailerAdapter"
    var trailers: List<Trailer> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private lateinit var onTrailerClickListenerInterface: OnTrailerClickListener

    fun setOnTrailerClickListener(onTrailerClickListener: OnTrailerClickListener) {
        onTrailerClickListenerInterface = onTrailerClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.trailer_button_card_view_template, parent, false)
        return TextViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: trailers = ${trailers.get(position).url}")
        holder.textViewTrailerName.text = trailers.get(position).name

        holder.itemView.setOnClickListener({
            if (onTrailerClickListenerInterface != null) {
                onTrailerClickListenerInterface.onClickListener(trailers.get(position))
            }
        })
    }

    fun interface OnTrailerClickListener {
        fun onClickListener(trailer: Trailer)
    }

    override fun getItemCount(): Int {
        return trailers.size
    }

    class TextViewHolder(itemView: View) : ViewHolder(itemView) {
        val textViewTrailerName = itemView.findViewById<TextView>(R.id.textViewTrailerName);
    }
}