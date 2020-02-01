package com.muklas.mymoviecatalogue.ui

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.muklas.mymoviecatalogue.R
import com.muklas.mymoviecatalogue.db.DatabaseContract.TvShowColumns.Companion.DESCRIPTION
import com.muklas.mymoviecatalogue.db.DatabaseContract.TvShowColumns.Companion.IMAGE
import com.muklas.mymoviecatalogue.db.DatabaseContract.TvShowColumns.Companion.NAME
import com.muklas.mymoviecatalogue.db.DatabaseContract.TvShowColumns.Companion.RELEASE
import com.muklas.mymoviecatalogue.db.DatabaseContract.TvShowColumns.Companion.TVSHOW_ID
import com.muklas.mymoviecatalogue.db.TvShowHelper
import com.muklas.mymoviecatalogue.model.TvShow
import kotlinx.android.synthetic.main.activity_tv_detail.*

class TvDetailActivity : AppCompatActivity() {

    private lateinit var tvShowHelper: TvShowHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_detail)
        supportActionBar?.hide()

        showLoading(true)

        val tvShow = intent.getParcelableExtra(TvShowFragment.EXTRA_TV) as TvShow

        tvTitle.text = tvShow.name
        tvRelease.text = tvShow.release
        tvDesc.text = tvShow.description
        Glide.with(this).load(tvShow.image).into(img)

        showLoading(false)

        tvShowHelper = TvShowHelper.getInstance(applicationContext)
        if (tvShowHelper.queryByTvShowId(tvShow.id).count > 0)
            btnFavorite.setBackgroundResource(R.drawable.ic_favorite_blue)
        else
            btnFavorite.setBackgroundResource(R.drawable.ic_favorite_border_blue)

        btnBack.setOnClickListener {
            finish()
        }

        btnFavorite.setOnClickListener {
            addFavorite(tvShow)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun addFavorite(tvShow: TvShow) {
        val values = ContentValues()
        values.put(TVSHOW_ID, tvShow.id)
        values.put(NAME, tvShow.name)
        values.put(DESCRIPTION, tvShow.description)
        values.put(RELEASE, tvShow.release)
        values.put(IMAGE, tvShow.image)

        val theTvShow = tvShowHelper.queryByTvShowId(tvShow.id).count
        if (theTvShow > 0) {
            val result = tvShowHelper.deleteByTvShowId(tvShow.id).toLong()
            if (result > 0) {
                btnFavorite.setBackgroundResource(R.drawable.ic_favorite_border_blue)
                Toast.makeText(this, getString(R.string.success_delete), Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(this, getString(R.string.fail_delete), Toast.LENGTH_SHORT).show()
        } else {
            val result = tvShowHelper.insert(values)
            if (result > 0) {
                btnFavorite.setBackgroundResource(R.drawable.ic_favorite_blue)
                Toast.makeText(this, getString(R.string.success_add), Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(this, getString(R.string.fail_add), Toast.LENGTH_SHORT).show()
        }
    }
}
