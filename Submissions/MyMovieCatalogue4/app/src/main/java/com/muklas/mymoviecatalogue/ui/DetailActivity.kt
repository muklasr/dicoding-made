package com.muklas.mymoviecatalogue.ui

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.muklas.mymoviecatalogue.R
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns.Companion.DESCRIPTION
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns.Companion.IMAGE
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns.Companion.MOVIE_ID
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns.Companion.RELEASE
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns.Companion.TITLE
import com.muklas.mymoviecatalogue.db.MovieHelper
import com.muklas.mymoviecatalogue.model.Movie
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var movieHelper: MovieHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.hide()

        showLoading(true)

        val movie = intent.getParcelableExtra(MovieFragment.EXTRA_MOVIE) as Movie

        tvTitle.text = movie.title
        tvRelease.text = movie.release
        tvDesc.text = movie.description
        Glide.with(this).load(movie.image).into(imgMovie)

        showLoading(false)

        movieHelper = MovieHelper.getInstance(applicationContext)
        if (movieHelper.queryByMovieId(movie.id).count > 0)
            btnFavorite.setBackgroundResource(R.drawable.ic_favorite_blue)
        else
            btnFavorite.setBackgroundResource(R.drawable.ic_favorite_border_blue)

        btnBack.setOnClickListener {
            finish()
        }

        btnFavorite.setOnClickListener {
            addFavorite(movie)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun addFavorite(movie: Movie) {
        val values = ContentValues()
        values.put(MOVIE_ID, movie.id)
        values.put(TITLE, movie.title)
        values.put(DESCRIPTION, movie.description)
        values.put(RELEASE, movie.release)
        values.put(IMAGE, movie.image)

        val theMovie = movieHelper.queryByMovieId(movie.id).count
        if (theMovie > 0) {
            val result = movieHelper.deleteByMovieId(movie.id).toLong()
            if (result > 0) {
                btnFavorite.setBackgroundResource(R.drawable.ic_favorite_border_blue)
                Toast.makeText(this, getString(R.string.success_delete), Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(this, getString(R.string.fail_delete), Toast.LENGTH_SHORT).show()
        } else {
            val result = movieHelper.insert(values)
            if (result > 0) {
                btnFavorite.setBackgroundResource(R.drawable.ic_favorite_blue)
                Toast.makeText(this, getString(R.string.success_add), Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(this, getString(R.string.fail_add), Toast.LENGTH_SHORT).show()
        }
    }
}
