package com.muklas.mymoviecatalogue.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.muklas.mymoviecatalogue.R
import com.muklas.mymoviecatalogue.db.DatabaseContract
import com.muklas.mymoviecatalogue.db.MovieHelper
import com.muklas.mymoviecatalogue.helper.MovieMappingHelper
import com.muklas.mymoviecatalogue.model.Movie

class MovieRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    var mWidgetItems = ArrayList<Movie>()

    override fun onCreate() {

    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = 0

    override fun onDataSetChanged() {
        val movieHelper = MovieHelper.getInstance(mContext)
        movieHelper.open()
        val cursor = mContext.contentResolver.query(
            DatabaseContract.MovieColumns.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val movies = cursor?.let { MovieMappingHelper.mapCursorToArrayList(cursor) }
        movies?.let {
            if (movies.isNotEmpty()) {
                mWidgetItems = movies
            }
        }
    }


    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName,
            R.layout.widget_item
        )
        val bitmap = Glide.with(mContext)
            .asBitmap()
            .load(mWidgetItems[position].image)
            .submit()
            .get()
        rv.setImageViewBitmap(R.id.imageView, bitmap)

        val extras = bundleOf(MovieWidget.EXTRA_ITEM to mWidgetItems[position].title)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {

    }

}