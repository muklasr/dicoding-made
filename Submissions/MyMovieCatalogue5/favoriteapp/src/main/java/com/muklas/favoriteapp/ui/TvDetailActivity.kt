package com.muklas.favoriteapp.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.muklas.favoriteapp.R
import com.muklas.favoriteapp.db.DatabaseContract.TvShowColumns.Companion.CONTENT_URI
import com.muklas.favoriteapp.model.TvShow
import kotlinx.android.synthetic.main.activity_tv_detail.*

class TvDetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_TV = "extra_tv"
    }

    private lateinit var uriWithId: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_detail)
        supportActionBar?.hide()

        showLoading(true)

        val tvShow = intent.getParcelableExtra(EXTRA_TV) as TvShow

        tvTitle.text = tvShow.name
        tvRelease.text = tvShow.release
        tvDesc.text = tvShow.description
        Glide.with(this).load(tvShow.image).into(img)
        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + tvShow.id)

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
