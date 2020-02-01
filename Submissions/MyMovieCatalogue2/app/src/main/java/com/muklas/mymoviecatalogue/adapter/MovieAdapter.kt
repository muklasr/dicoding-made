package com.muklas.mymoviecatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muklas.mymoviecatalogue.R
import com.muklas.mymoviecatalogue.model.Movie
import kotlinx.android.synthetic.main.row_item.view.*

class MovieAdapter(private val listMovie: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.CardViewViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listMovie.size
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(movie.image)
                    .apply(RequestOptions().override(300, 300))
                    .into(itemView.civImage)
                tvTitle.text = movie.title
                tvGenre.text = movie.genre

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(movie) }
            }

        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
    }
}