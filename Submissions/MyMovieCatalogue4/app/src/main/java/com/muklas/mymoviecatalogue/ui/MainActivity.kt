package com.muklas.mymoviecatalogue.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.muklas.mymoviecatalogue.R
import com.muklas.mymoviecatalogue.adapter.SectionsPagerAdapter
import com.muklas.mymoviecatalogue.db.MovieHelper
import com.muklas.mymoviecatalogue.db.TvShowHelper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var movieHelper: MovieHelper
    private lateinit var tvShowHelper: TvShowHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)

        supportActionBar?.elevation = 0f

        movieHelper = MovieHelper.getInstance(applicationContext)
        movieHelper.open()
        tvShowHelper = TvShowHelper.getInstance(applicationContext)
        tvShowHelper.open()

        fabFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings) {
            val i = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        movieHelper.close()
        tvShowHelper.close()
    }
}
