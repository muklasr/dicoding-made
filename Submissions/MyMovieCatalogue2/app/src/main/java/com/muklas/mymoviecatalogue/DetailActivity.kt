package com.muklas.mymoviecatalogue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.muklas.mymoviecatalogue.model.Movie
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.hide()

        val movie = intent.getParcelableExtra(MovieFragment.EXTRA_MOVIE) as Movie

        tvTitle.text = movie.title
        tvRelease.text = ": "+movie.release
        tvGenre.text = ": "+movie.genre
        tvDesc.text = movie.description

        Glide.with(this).load(movie.image).into(imgMovie)

        btnBack.setOnClickListener {
            finish()
        }

    }
}
