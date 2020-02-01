package com.muklas.mymoviecatalogue.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.muklas.mymoviecatalogue.R
import com.muklas.mymoviecatalogue.adapter.MovieAdapter
import com.muklas.mymoviecatalogue.db.MovieHelper
import com.muklas.mymoviecatalogue.helper.MovieMappingHelper
import com.muklas.mymoviecatalogue.model.Movie
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
    private lateinit var movieHelper: MovieHelper
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
        view.rvMovie.adapter = adapter

        movieHelper = MovieHelper.getInstance(context as Context)

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
                val cursor = movieHelper.queryAll()
                MovieMappingHelper.mapCursorToArrayList(cursor)
            }
            showLoading(false)
            val movies = deferredMovies.await()
            if (movies.isNotEmpty()) {
                adapter.setData(movies)
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
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listMovie)
    }
}
