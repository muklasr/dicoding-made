package com.muklas.mymoviecatalogue

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : MovieAdapter
    private lateinit var dataTitle: Array<String>
    private lateinit var dataGenre: Array<String>
    private lateinit var dataRelease: Array<String>
    private lateinit var dataDescription: Array<String>
    private lateinit var dataImage: TypedArray
    private var movies = arrayListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MovieAdapter(this)
        lvMovie.adapter = adapter

        prepare()
        addItem()
        
        lvMovie.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val i = Intent(this, DetailActivity::class.java)
            i.putExtra("movie", movies[position])
            startActivity(i)
        }
    }

    private fun prepare(){
        dataTitle = resources.getStringArray(R.array.data_title)
        dataGenre = resources.getStringArray(R.array.data_genre)
        dataRelease = resources.getStringArray(R.array.data_release)
        dataDescription = resources.getStringArray(R.array.data_description)
        dataImage = resources.obtainTypedArray(R.array.data_image)
    }

    private fun addItem(){
        for (position in dataTitle.indices) {
            val movie = Movie(
                dataTitle[position],
                dataDescription[position],
                dataRelease[position],
                dataGenre[position],
                dataImage.getResourceId(position, -1)
            )
            movies.add(movie)
        }
        adapter.movies = movies
    }
}
