package com.muklas.mymoviecatalogue.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.muklas.mymoviecatalogue.db.DatabaseContract
import com.muklas.mymoviecatalogue.db.DatabaseContract.AUTHORITY
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns.Companion.CONTENT_URI
import com.muklas.mymoviecatalogue.db.MovieHelper
import com.muklas.mymoviecatalogue.db.TvShowHelper

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val MOVIE = 1
        private const val MOVIE_ID = 2
        private const val TV_SHOW = 3
        private const val TV_SHOW_ID = 4
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var movieHelper: MovieHelper
        private lateinit var tvShowHelper: TvShowHelper

        init {
            uriMatcher.addURI(AUTHORITY, DatabaseContract.MovieColumns.TABLE_NAME, MOVIE)
            uriMatcher.addURI(AUTHORITY, "${DatabaseContract.MovieColumns.TABLE_NAME}/#", MOVIE_ID)
            uriMatcher.addURI(AUTHORITY, DatabaseContract.TvShowColumns.TABLE_NAME, TV_SHOW)
            uriMatcher.addURI(
                AUTHORITY,
                "${DatabaseContract.TvShowColumns.TABLE_NAME}/#",
                TV_SHOW_ID
            )
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when (uriMatcher.match(uri)) {
            MOVIE -> movieHelper.insertProvider(values)
            TV_SHOW -> tvShowHelper.insertProvider(values)
            else -> 0
        }

        context!!.contentResolver.notifyChange(uri, null)

        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor: Cursor? = when (uriMatcher.match(uri)) {
            MOVIE -> movieHelper.queryProvider()
            MOVIE_ID -> movieHelper.queryByIdProvider(uri.lastPathSegment.toString())
            TV_SHOW -> tvShowHelper.queryProvider()
            TV_SHOW_ID -> tvShowHelper.queryByIdProvider(uri.lastPathSegment.toString())
            else -> null
        }

        return cursor
    }

    override fun onCreate(): Boolean {
        movieHelper = MovieHelper(context as Context)
        movieHelper.open()
        tvShowHelper = TvShowHelper(context as Context)
        tvShowHelper.open()
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val updated: Int = when (uriMatcher.match(uri)) {
            MOVIE_ID -> movieHelper.updateProvider(uri.lastPathSegment.toString(), values)
            TV_SHOW_ID -> tvShowHelper.updateProvider(uri.lastPathSegment.toString(), values)
            else -> 0
        }

        context!!.contentResolver.notifyChange(uri, null)

        return updated
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val deleted: Int = when (uriMatcher.match(uri)) {
            MOVIE_ID -> movieHelper.deleteProvider(uri.lastPathSegment.toString())
            TV_SHOW_ID -> tvShowHelper.deleteProvider(uri.lastPathSegment.toString())
            else -> 0
        }

        context!!.contentResolver.notifyChange(uri, null)

        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }

}