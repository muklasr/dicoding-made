package com.muklas.favoriteapp.helper

import android.database.Cursor
import com.muklas.favoriteapp.db.DatabaseContract
import com.muklas.favoriteapp.model.TvShow

object TvShowMappingHelper {
    fun mapCursorToArrayList(tvShowCursor: Cursor): ArrayList<TvShow> {
        val tvShowList = ArrayList<TvShow>()
//        tvShowCursor.moveToFirst()
        while (tvShowCursor.moveToNext()) {
            val tv_show_id = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.TVSHOW_ID))
            val name = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.NAME))
            val description = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.DESCRIPTION))
            val release = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.RELEASE))
            val image = tvShowCursor.getString(tvShowCursor.getColumnIndexOrThrow(DatabaseContract.TvShowColumns.IMAGE))
            tvShowList.add(TvShow(tv_show_id, name, description, release, image))
        }
        return tvShowList
    }
}