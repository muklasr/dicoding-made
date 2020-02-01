package com.muklas.mymoviecatalogue.ui

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.muklas.mymoviecatalogue.R
import com.muklas.mymoviecatalogue.db.DatabaseContract.TvShowColumns.Companion.CONTENT_URI
import com.muklas.mymoviecatalogue.db.DatabaseContract.TvShowColumns.Companion.DESCRIPTION
import com.muklas.mymoviecatalogue.db.DatabaseContract.TvShowColumns.Companion.IMAGE
import com.muklas.mymoviecatalogue.db.DatabaseContract.TvShowColumns.Companion.NAME
import com.muklas.mymoviecatalogue.db.DatabaseContract.TvShowColumns.Companion.RELEASE
import com.muklas.mymoviecatalogue.db.DatabaseContract.TvShowColumns.Companion.TVSHOW_ID
import com.muklas.mymoviecatalogue.model.TvShow
import com.muklas.mymoviecatalogue.widget.TvShowWidget
import kotlinx.android.synthetic.main.activity_tv_detail.*

class TvDetailActivity : AppCompatActivity() {

    private lateinit var uriWithId: Uri
    private val TAG = TvDetailActivity::class.java.simpleName

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
        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + tvShow.id)

        showLoading(false)
        setLove()

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

        Log.d(TAG, uriWithId.toString())
        val cursor = contentResolver.query(uriWithId, null, null, null, null) as Cursor
        if (cursor.count > 0) {
            val result = contentResolver.delete(uriWithId, null, null)
            if (result > 0)
                Toast.makeText(this, getString(R.string.success_delete), Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, getString(R.string.fail_delete), Toast.LENGTH_SHORT).show()
        } else {
            val result = contentResolver.insert(CONTENT_URI, values)
            if (result != null)
                Toast.makeText(this, getString(R.string.success_add), Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, getString(R.string.fail_add), Toast.LENGTH_SHORT).show()
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
        val view = RemoteViews(packageName, R.layout.tv_show_widget)
        val theWidget = ComponentName(this, TvShowWidget::class.java)
        val ids = manager.getAppWidgetIds(theWidget)

        manager.notifyAppWidgetViewDataChanged(ids, R.id.stack_view)
        manager.updateAppWidget(ids, view)
    }
}
