package com.muklas.favoriteapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.muklas.favoriteapp.R
import com.muklas.favoriteapp.model.TvShow
import kotlinx.android.synthetic.main.row_item.view.*

class TvShowAdapter :
    RecyclerView.Adapter<TvShowAdapter.CardViewViewHolder>() {

    val list = ArrayList<TvShow>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(items: ArrayList<TvShow>) {
        list.clear()
        list.addAll(items)
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
//                    .error(Placeholder())
                    .into(itemView.civImage)
                tvTitle.text = tvShow.name

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(tvShow) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TvShow)
    }

}