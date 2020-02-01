package com.muklas.mymoviecatalogue.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.muklas.mymoviecatalogue.R
import com.muklas.mymoviecatalogue.adapter.MovieAdapter
import com.muklas.mymoviecatalogue.model.Movie
import com.muklas.mymoviecatalogue.ui.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie.view.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {

    private lateinit var adapter: MovieAdapter
    private lateinit var movieViewModel: MovieViewModel

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie, container, false)

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

        movieViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MovieViewModel::class.java)
        movieViewModel.setMovie()
        showLoading(view, true)

        movieViewModel.getMovie().observe(this, Observer { mov ->
            if (mov != null) {
                adapter.setData(mov)
                showLoading(view, false)
            }
        })


        return view
    }

    private fun showLoading(view: View, state: Boolean) {
        if (state) {
            view.progressBar.visibility = View.VISIBLE
        } else {
            view.progressBar.visibility = View.GONE
        }
    }
}
