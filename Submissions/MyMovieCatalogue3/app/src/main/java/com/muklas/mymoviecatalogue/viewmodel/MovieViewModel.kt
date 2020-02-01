package com.muklas.mymoviecatalogue.viewmodel

import android.util.Log
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.muklas.mymoviecatalogue.BuildConfig
import com.muklas.mymoviecatalogue.model.Movie
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class MovieViewModel : ViewModel() {
    companion object {
        private val API_KEY = BuildConfig.API_KEY
    }

    val listMovies = MutableLiveData<ArrayList<Movie>>()

    internal fun setMovie() {
        var language = "en-US"
        if (Locale.getDefault().displayLanguage == "Indonesia")
            language = "id"
        val client = AsyncHttpClient()
        val listItems = ArrayList<Movie>()
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&language=$language"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    for (i in 0 until list.length()) {
                        val movie = list.getJSONObject(i)
                        val movieItems = Movie(
                            movie.getString("title"),
                            movie.getString("overview"),
                            movie.getString("release_date"),
                            "https://image.tmdb.org/t/p/w185" + movie.getString("poster_path")
                        )
                        listItems.add(movieItems)
                    }
                    listMovies.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    internal fun getMovie(): LiveData<ArrayList<Movie>> {
        return listMovies
    }
}