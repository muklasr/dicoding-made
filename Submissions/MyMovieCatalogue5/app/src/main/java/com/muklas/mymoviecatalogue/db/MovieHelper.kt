package com.muklas.mymoviecatalogue.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns.Companion.MOVIE_ID
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns.Companion.TABLE_NAME
import com.muklas.mymoviecatalogue.db.DatabaseContract.MovieColumns.Companion._ID

class MovieHelper(context: Context) {

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var databaseHelper: DatabaseHelper
        private var INSTANCE: MovieHelper? = null
        private lateinit var database: SQLiteDatabase

        fun getInstance(context: Context): MovieHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = MovieHelper(context)
                    }
                }
            }
            return INSTANCE as MovieHelper
        }
    }

    init {
        databaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    fun queryByIdProvider(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$MOVIE_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    fun queryProvider(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID DESC"
        )
    }

    fun insertProvider(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun updateProvider(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$MOVIE_ID = ?", arrayOf(id))
    }

    fun deleteProvider(id: String): Int {
        return database.delete(DATABASE_TABLE, "$MOVIE_ID = ?", arrayOf(id))
    }
}