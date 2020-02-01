package com.muklas.mymoviecatalogue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.muklas.mymoviecatalogue.model.TvShow
import kotlinx.android.synthetic.main.activity_detail.btnBack
import kotlinx.android.synthetic.main.activity_detail.tvDesc
import kotlinx.android.synthetic.main.activity_detail.tvTitle
import kotlinx.android.synthetic.main.activity_tv_detail.*

class TvDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_detail)
        supportActionBar?.hide()

        val tvShow = intent.getParcelableExtra(TvShowFragment.EXTRA_TV) as TvShow

        tvTitle.text = tvShow.name
        tvRelease.text = tvShow.release
        tvDesc.text = tvShow.description

        Glide.with(this).load(tvShow.image).into(img)

        btnBack.setOnClickListener {
            finish()
        }
    }
}
