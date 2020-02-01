package com.muklas.mymoviecatalogue.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.muklas.mymoviecatalogue.R
import com.muklas.mymoviecatalogue.adapter.TvShowAdapter
import com.muklas.mymoviecatalogue.db.TvShowHelper
import com.muklas.mymoviecatalogue.helper.TvShowMappingHelper
import com.muklas.mymoviecatalogue.model.TvShow
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
    private lateinit var tvShowHelper: TvShowHelper
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
        view.rvTvShow.adapter = adapter

        tvShowHelper = TvShowHelper.getInstance(context as Context)

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
                val cursor = tvShowHelper.queryAll()
                TvShowMappingHelper.mapCursorToArrayList(cursor)
            }
            showLoading(false)
            val tvShow = deferredTvShows.await()
            if (tvShow.isNotEmpty()) {
                adapter.setData(tvShow)
            } else {
                tvFeedback.visibility = View.VISIBLE
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