package com.muklas.mymoviecatalogue.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muklas.mymoviecatalogue.R
import com.muklas.mymoviecatalogue.model.TvShow
import kotlinx.android.synthetic.main.row_item.view.*

class TvShowAdapter(val mContext: Context, val list: ArrayList<TvShow>) :
    RecyclerView.Adapter<TvShowAdapter.CardViewViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false)
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tvShow: TvShow) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(tvShow.image)
                    .apply(RequestOptions().override(300, 300))
                    .into(itemView.civImage)
                tvTitle.text = tvShow.name
                tvGenre.text = tvShow.genre

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(tvShow) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TvShow)
    }

}