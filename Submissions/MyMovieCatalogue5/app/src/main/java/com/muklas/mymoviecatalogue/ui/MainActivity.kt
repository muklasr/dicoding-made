package com.muklas.mymoviecatalogue.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.muklas.mymoviecatalogue.R
import com.muklas.mymoviecatalogue.adapter.MovieAdapter
import com.muklas.mymoviecatalogue.adapter.SectionsPagerAdapter
import com.muklas.mymoviecatalogue.adapter.TvShowAdapter
import com.muklas.mymoviecatalogue.db.MovieHelper
import com.muklas.mymoviecatalogue.db.TvShowHelper
import com.muklas.mymoviecatalogue.model.Movie
import com.muklas.mymoviecatalogue.model.TvShow
import com.muklas.mymoviecatalogue.ui.viewmodel.MovieViewModel
import com.muklas.mymoviecatalogue.ui.viewmodel.TvShowViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var movieHelper: MovieHelper
    private lateinit var tvShowHelper: TvShowHelper
    private var position = 0

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

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                position = p0?.position!!.toInt()
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    tabs.visibility = View.VISIBLE
                    viewPager.visibility = View.VISIBLE
                    rvSearch.visibility = View.GONE
                } else {
                    tabs.visibility = View.GONE
                    loadData(newText)
                    viewPager.visibility = View.GONE
                    rvSearch.visibility = View.VISIBLE
                }
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings) {
            val i = Intent(this, SettingsActivity::class.java)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        movieHelper.close()
        tvShowHelper.close()
    }

    private fun loadData(query: String?) {
        if (position == 0) {
            val adapter = MovieAdapter()
            rvSearch.layoutManager = LinearLayoutManager(this)
            adapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Movie) {
                    val i = Intent(applicationContext, DetailActivity::class.java)
                    i.putExtra(MovieFragment.EXTRA_MOVIE, data)
                    startActivity(i)
                }
            })
            rvSearch.adapter = adapter
            adapter.notifyDataSetChanged()

            val viewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(MovieViewModel::class.java)
            viewModel.setMovie(query)
            showLoading(true)

            viewModel.getMovie().observe(this, Observer { data ->
                if (data != null) {
                    adapter.setData(data)
                    showLoading(false)
                }
            })
        } else {
            val adapter = TvShowAdapter()
            rvSearch.layoutManager = LinearLayoutManager(this)
            adapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback {
                override fun onItemClicked(data: TvShow) {
                    val i = Intent(applicationContext, TvDetailActivity::class.java)
                    i.putExtra(TvShowFragment.EXTRA_TV, data)
                    startActivity(i)
                }
            })
            rvSearch.adapter = adapter
            adapter.notifyDataSetChanged()

            val viewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(TvShowViewModel::class.java)
            viewModel.setTvShow(query)
            showLoading(true)

            viewModel.getTvShow().observe(this, Observer { data ->
                if (data != null) {
                    adapter.setData(data)
                    showLoading(false)
                }
            })
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
