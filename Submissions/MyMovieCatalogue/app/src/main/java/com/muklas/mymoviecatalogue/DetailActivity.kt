package com.muklas.mymoviecatalogue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val movie = intent.getParcelableExtra("movie") as Movie

        tvTitle.text = movie.title
        tvRelease.text = movie.release
        tvDesc.text = movie.description

        Glide.with(this).load(movie.image).into(imgMovie)

        btnBack.setOnClickListener {
            finish()
        }

    }
}
