package com.muklas.favoriteapp.ui

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.muklas.favoriteapp.R
import com.muklas.favoriteapp.db.DatabaseContract.MovieColumns.Companion.CONTENT_URI
import com.muklas.favoriteapp.model.Movie
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var uriWithId: Uri
    private var cursor: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.hide()

        showLoading(true)

        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as Movie

        tvTitle.text = movie.title
        tvRelease.text = movie.release
        tvDesc.text = movie.description
        Glide.with(this).load(movie.image).into(imgMovie)
        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + movie.id)

        showLoading(false)

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
