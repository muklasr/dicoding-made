package com.muklas.mymoviecatalogue.db

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.muklas.mymoviecatalogue"
    const val SCHEME = "content"

    internal class MovieColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "movie"
            const val _ID = "_id"
            const val MOVIE_ID = "movie_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val RELEASE = "release"
            const val IMAGE = "image"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }

    internal class TvShowColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "tv_show"
            const val _ID = "_id"
            const val TVSHOW_ID = "tv_show_id"
            const val NAME = "name"
            const val DESCRIPTION = "description"
            const val RELEASE = "release"
            const val IMAGE = "image"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }

}