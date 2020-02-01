package com.muklas.mymoviecatalogue

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.muklas.mymoviecatalogue.model.Movie
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

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
