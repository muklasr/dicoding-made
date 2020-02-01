package com.muklas.favoriteapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muklas.favoriteapp.R
import com.muklas.favoriteapp.model.Movie
import kotlinx.android.synthetic.main.row_item.view.*

class MovieAdapter :
    RecyclerView.Adapter<MovieAdapter.CardViewViewHolder>() {

    val listMovie = ArrayList<Movie>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(items: ArrayList<Movie>) {
        listMovie.clear()
        listMovie.addAll(items)
        notifyDataSetChanged()
    }

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

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(movie.image)
                    .apply(RequestOptions().override(300, 300))
//                    .error(Placeholder())
                    .into(itemView.civImage)
                tvTitle.text = movie.title

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(movie) }
            }

        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
    }
}