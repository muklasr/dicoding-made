package com.muklas.mymoviecatalogue

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class MovieAdapter internal constructor(private val context: Context) : BaseAdapter() {
    internal var movies = arrayListOf<Movie>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false)
        }

        val viewHolder = ViewHolder(itemView as View)

        val movie = getItem(position) as Movie
        viewHolder.bind(movie)
        return itemView!!
    }

    override fun getItem(position: Int): Any = movies[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = movies.size

    private inner class ViewHolder internal constructor(view: View) {
        private val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        private val tvGenre: TextView = view.findViewById(R.id.tvGenre)
        private val civ: CircleImageView = view.findViewById(R.id.civImage)

        internal fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvGenre.text = movie.genre
            Glide.with(context).load(movie.image).into(civ)
        }
    }
}