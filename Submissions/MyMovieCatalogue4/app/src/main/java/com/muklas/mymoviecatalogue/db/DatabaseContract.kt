package com.muklas.mymoviecatalogue.db

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class MovieColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "movie"
            const val _ID = "_id"
            const val MOVIE_ID = "movie_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val RELEASE = "release"
            const val IMAGE = "image"
        }
    }

    internal class TvShowColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "tv_show"
            const val _ID = "_id"
            const val TVSHOW_ID = "tv_show_id"
            const val NAME = "name"
            const val DESCRIPTION = "description"
            const val RELEASE = "release"
            const val IMAGE = "image"
        }
    }

}