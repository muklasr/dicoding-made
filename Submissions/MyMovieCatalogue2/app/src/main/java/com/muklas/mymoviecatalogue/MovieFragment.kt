package com.muklas.mymoviecatalogue


import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.muklas.mymoviecatalogue.adapter.MovieAdapter
import com.muklas.mymoviecatalogue.model.Movie
import kotlinx.android.synthetic.main.fragment_movie.view.*

/**
 * A simple [Fragment] subclass.
 */
class MovieFragment : Fragment() {

    private lateinit var dataTitle: Array<String>
    private lateinit var dataGenre: Array<String>
    private lateinit var dataRelease: Array<String>
    private lateinit var dataDescription: Array<String>
    private lateinit var dataImage: TypedArray
    private var movies = arrayListOf<Movie>()

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie, container, false)

        prepare()
        addItem()

        view.rvMovie.layoutManager = LinearLayoutManager(view.context)
        val adapter = MovieAdapter(movies)
        adapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Movie) {
                val i = Intent(view.context, DetailActivity::class.java)
                i.putExtra(EXTRA_MOVIE, data)
                startActivity(i)
            }
        })
        view.rvMovie.adapter = adapter


        return view
    }

    private fun prepare() {
        dataTitle = resources.getStringArray(R.array.data_movie_title)
        dataGenre = resources.getStringArray(R.array.data_movie_genre)
        dataRelease = resources.getStringArray(R.array.data_movie_release)
        dataDescription = resources.getStringArray(R.array.data_movie_description)
        dataImage = resources.obtainTypedArray(R.array.data_movie_image)
    }

    private fun addItem() {
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
    }

}
