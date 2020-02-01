package com.muklas.mymoviecatalogue.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.muklas.mymoviecatalogue.db.DatabaseContract.TvShowColumns.Companion.TABLE_NAME
import com.muklas.mymoviecatalogue.db.DatabaseContract.TvShowColumns.Companion.TVSHOW_ID
import com.muklas.mymoviecatalogue.db.DatabaseContract.TvShowColumns.Companion._ID

class TvShowHelper(context: Context) {

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var databaseHelper: DatabaseHelper
        private var INSTANCE: TvShowHelper? = null
        private lateinit var database: SQLiteDatabase

        fun getInstance(context: Context): TvShowHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = TvShowHelper(context)
                    }
                }
            }
            return INSTANCE as TvShowHelper
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

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    fun queryByTvShowId(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$TVSHOW_ID = ?",
            arrayOf(id),
            null,
            null,
            null
        )
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$TVSHOW_ID = ?", arrayOf(id))
    }

    fun deleteByTvShowId(id: String): Int {
        return database.delete(DATABASE_TABLE, "$TVSHOW_ID = '$id'", null)
    }
}