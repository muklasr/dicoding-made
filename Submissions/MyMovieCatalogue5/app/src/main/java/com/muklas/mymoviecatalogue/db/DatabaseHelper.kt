package com.muklas.mymoviecatalogue.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns
import com.muklas.mymoviecatalogue.db.DatabaseContract.TvShowColumns

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "favoritedb"
        private const val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_MOVIE = "CREATE TABLE ${MovieColumns.TABLE_NAME}" +
                "(${MovieColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${MovieColumns.MOVIE_ID} INTEGER NOT NULL," +
                "${MovieColumns.TITLE} TEXT NOT NULL," +
                "${MovieColumns.DESCRIPTION} TEXT NOT NULL," +
                "${MovieColumns.RELEASE} TEXT NOT NULL," +
                "${MovieColumns.IMAGE} TEXT NOT NULL)"
        private val SQL_CREATE_TABLE_TVSHOW = "CREATE TABLE ${TvShowColumns.TABLE_NAME}" +
                "(${TvShowColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${TvShowColumns.TVSHOW_ID} INTEGER NOT NULL," +
                "${TvShowColumns.NAME} TEXT NOT NULL," +
                "${TvShowColumns.DESCRIPTION} TEXT NOT NULL," +
                "${TvShowColumns.RELEASE} TEXT NOT NULL," +
                "${TvShowColumns.IMAGE} TEXT NOT NULL)"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_MOVIE)
        db?.execSQL(SQL_CREATE_TABLE_TVSHOW)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${MovieColumns.TABLE_NAME}")
        db?.execSQL("DROP TABLE IF EXISTS ${TvShowColumns.TABLE_NAME}")
        onCreate(db)
    }

}