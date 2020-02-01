package com.muklas.favoriteapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.muklas.favoriteapp.R
import com.muklas.favoriteapp.adapter.FavoritePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagerAdapter =
            FavoritePagerAdapter(this, supportFragmentManager)
        viewPager.adapter = pagerAdapter
        tabs.setupWithViewPager(viewPager)

        supportActionBar?.elevation = 0f

    }
}
