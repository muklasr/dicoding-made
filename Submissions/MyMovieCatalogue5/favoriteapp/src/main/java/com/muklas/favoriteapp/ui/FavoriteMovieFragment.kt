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
import com.muklas.favoriteapp.adapter.MovieAdapter
import com.muklas.favoriteapp.db.DatabaseContract.MovieColumns.Companion.CONTENT_URI
import com.muklas.favoriteapp.helper.MovieMappingHelper
import com.muklas.favoriteapp.model.Movie
import com.muklas.favoriteapp.ui.DetailActivity.Companion.EXTRA_MOVIE
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMovieFragment : Fragment() {

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    private lateinit var adapter: MovieAdapter
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        rootView = view

        adapter = MovieAdapter()
        adapter.notifyDataSetChanged()

        view.rvMovie.layoutManager = LinearLayoutManager(view.context)

        adapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                val i = Intent(view.context, DetailActivity::class.java)
                i.putExtra(EXTRA_MOVIE, data)
                startActivity(i)
            }
        })
        view.rvMovie.adapter = adapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadMoviesAsync()
            }
        }
        view.context.contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadMoviesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Movie>(EXTRA_STATE)
            if (list != null) {
                adapter.setData(list)
            }
        }
        return view
    }

    private fun loadMoviesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            showLoading(true)
            val deferredMovies = async(Dispatchers.IO) {
                val cursor = context?.contentResolver?.query(CONTENT_URI, null, null, null, null)
                cursor?.let {
                    MovieMappingHelper.mapCursorToArrayList(cursor)
                }
            }
            showLoading(false)
            val movies = deferredMovies.await()
            movies?.let {
                if (movies.isNotEmpty()) {
                    adapter.setData(movies)
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
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listMovie)
    }
}
