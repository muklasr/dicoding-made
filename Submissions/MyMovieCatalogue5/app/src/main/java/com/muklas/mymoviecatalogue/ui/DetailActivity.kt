package com.muklas.mymoviecatalogue.ui

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.muklas.mymoviecatalogue.R
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns.Companion.CONTENT_URI
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns.Companion.DESCRIPTION
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns.Companion.IMAGE
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns.Companion.MOVIE_ID
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns.Companion.RELEASE
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns.Companion.TITLE
import com.muklas.mymoviecatalogue.model.Movie
import com.muklas.mymoviecatalogue.widget.MovieWidget
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var uriWithId: Uri

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
        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + movie.id)

        showLoading(false)
        setLove()


        btnBack.setOnClickListener {
            finish()
        }

        btnFavorite.setOnClickListener {
            addFavorite(movie)
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun addFavorite(movie: Movie) {
        val values = ContentValues()
        values.put(MOVIE_ID, movie.id)
        values.put(TITLE, movie.title)
        values.put(DESCRIPTION, movie.description)
        values.put(RELEASE, movie.release)
        values.put(IMAGE, movie.image)

        val cursor = contentResolver.query(uriWithId, null, null, null, null)
        cursor?.let {
            if (cursor.count > 0) {
                val result = contentResolver.delete(uriWithId, null, null)
                if (result > 0)
                    Toast.makeText(
                        this,
                        getString(R.string.success_delete),
                        Toast.LENGTH_SHORT
                    ).show()
                else
                    Toast.makeText(this, getString(R.string.fail_delete), Toast.LENGTH_SHORT).show()
            } else {
                val result = contentResolver.insert(CONTENT_URI, values)
                if (result != null)
                    Toast.makeText(this, getString(R.string.success_add), Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, getString(R.string.fail_add), Toast.LENGTH_SHORT).show()
            }
        }
        setLove()
        updateWidget()
    }

    private fun setLove() {
        val cursor = contentResolver.query(uriWithId, null, null, null, null)
        if (cursor != null) {
            if (cursor.count > 0) {
                btnFavorite.setBackgroundResource(R.drawable.ic_favorite_blue)
            } else {
                btnFavorite.setBackgroundResource(R.drawable.ic_favorite_border_blue)
            }
        }
    }

    private fun updateWidget() {
        val manager = AppWidgetManager.getInstance(this)
        val view = RemoteViews(packageName, R.layout.movie_widget)
        val theWidget = ComponentName(this, MovieWidget::class.java)
        val ids = manager.getAppWidgetIds(theWidget)

        manager.notifyAppWidgetViewDataChanged(ids, R.id.stack_view)
        manager.updateAppWidget(ids, view)
    }
}
