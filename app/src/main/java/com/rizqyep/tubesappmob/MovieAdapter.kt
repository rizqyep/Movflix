package com.rizqyep.tubesappmob

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(private val list: ArrayList<MovieResponse>) :
        RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(movieResponse: MovieResponse){
            with(itemView){
                val title = "${movieResponse.name}"
                val year_released = "${movieResponse.year_released}"
                val rating = "Rating : ${movieResponse.rating}"

                tv_title.text = title
                tv_year_released.text = year_released
                tv_rating.text = rating
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size;
    }


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(list[position])
    }
}