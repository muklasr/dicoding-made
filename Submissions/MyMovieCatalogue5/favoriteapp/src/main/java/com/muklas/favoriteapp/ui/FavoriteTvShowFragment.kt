package com.muklas.favoriteapp.ui

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.muklas.favoriteapp.R
import com.muklas.favoriteapp.adapter.TvShowAdapter
import com.muklas.favoriteapp.db.DatabaseContract.TvShowColumns.Companion.CONTENT_URI
import com.muklas.favoriteapp.helper.TvShowMappingHelper
import com.muklas.favoriteapp.model.TvShow
import com.muklas.favoriteapp.ui.TvDetailActivity.Companion.EXTRA_TV
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.android.synthetic.main.fragment_tv_show.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteTvShowFragment : Fragment() {

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    private lateinit var adapter: TvShowAdapter
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tv_show, container, false)

        rootView = view

        adapter = TvShowAdapter()
        adapter.notifyDataSetChanged()

        view.rvTvShow.layoutManager = LinearLayoutManager(view.context)

        adapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TvShow) {
                val i = Intent(view.context, TvDetailActivity::class.java)
                i.putExtra(EXTRA_TV, data)
                startActivity(i)
            }
        })
        view.rvTvShow.adapter = adapter


        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadTvShowsAsync()
            }
        }
        view.context.contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadTvShowsAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<TvShow>(EXTRA_STATE)
            if (list != null) {
                adapter.setData(list)
            }
        }
        return view
    }

    private fun loadTvShowsAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            showLoading(true)
            val deferredTvShows = async(Dispatchers.IO) {
                val cursor = context?.contentResolver?.query(CONTENT_URI, null, null, null, null)
                cursor?.let {
                    TvShowMappingHelper.mapCursorToArrayList(cursor)
                }
            }
            showLoading(false)
            val tvShow = deferredTvShows.await()
            tvShow?.let {
                if (tvShow.isNotEmpty()) {
                    adapter.setData(tvShow)
                } else {
                    tvFeedback.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            rootView.progressBar.visibility = View.VISIBLE
        } else {
            rootView.progressBar.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.list)
    }
}