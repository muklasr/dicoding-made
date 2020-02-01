package com.muklas.mymoviecatalogue.helper

import android.database.Cursor
import com.muklas.mymoviecatalogue.db.DatabaseContract
import com.muklas.mymoviecatalogue.model.Movie

object MovieMappingHelper {
    fun mapCursorToArrayList(moviesCursor: Cursor): ArrayList<Movie> {
        val moviesList = ArrayList<Movie>()
//        moviesCursor.moveToFirst()
        while (moviesCursor.moveToNext()) {
            val movie_id = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.MOVIE_ID))
            val title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE))
            val description = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.DESCRIPTION))
            val release = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASE))
            val image = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.IMAGE))
            moviesList.add(Movie(movie_id, title, description, release, image))
        }
        return moviesList
    }
}