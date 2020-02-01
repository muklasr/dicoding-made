package com.muklas.mymoviecatalogue.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.muklas.mymoviecatalogue.BuildConfig
import com.muklas.mymoviecatalogue.model.TvShow
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class TvShowViewModel : ViewModel() {
    companion object {
        private val API_KEY = BuildConfig.API_KEY
    }

    val listTvShow = MutableLiveData<ArrayList<TvShow>>()

    internal fun setTvShow() {
        var language = "en-US"
        if (Locale.getDefault().displayLanguage == "Indonesia")
            language = "id"
        val client = AsyncHttpClient()
        val listItems = ArrayList<TvShow>()
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=$API_KEY&language=$language"
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
                        val tvShow = list.getJSONObject(i)
                        val tvShowItems = TvShow(
                            tvShow.getString("id"),
                            tvShow.getString("name"),
                            tvShow.getString("overview"),
                            tvShow.getString("first_air_date"),
                            "https://image.tmdb.org/t/p/w185" + tvShow.getString("poster_path")
                        )
                        listItems.add(tvShowItems)
                    }
                    listTvShow.postValue(listItems)
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

    internal fun getTvShow(): LiveData<ArrayList<TvShow>> {
        return listTvShow
    }
}