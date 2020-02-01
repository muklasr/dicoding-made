package com.muklas.mymoviecatalogue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.muklas.mymoviecatalogue.R
import com.muklas.mymoviecatalogue.TvShowFragment
import com.muklas.mymoviecatalogue.model.TvShow
import kotlinx.android.synthetic.main.activity_detail.btnBack
import kotlinx.android.synthetic.main.activity_detail.tvDesc
import kotlinx.android.synthetic.main.activity_detail.tvGenre
import kotlinx.android.synthetic.main.activity_detail.tvTitle
import kotlinx.android.synthetic.main.activity_tv_detail.*

class TvDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_detail)
        supportActionBar?.hide()

        val tvShow = intent.getParcelableExtra(TvShowFragment.EXTRA_TV) as TvShow


        tvTitle.text = tvShow.name
        tvType.text = ": "+tvShow.type
        tvLanguage.text = ": "+tvShow.language
        tvRuntime.text = ": "+tvShow.runtime
        tvGenre.text = ": "+tvShow.genre
        tvDesc.text = tvShow.description

        Glide.with(this).load(tvShow.image).into(img)

        btnBack.setOnClickListener {
            finish()
        }

    }
}
