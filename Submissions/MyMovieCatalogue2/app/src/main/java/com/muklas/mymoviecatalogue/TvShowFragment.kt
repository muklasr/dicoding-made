package com.muklas.mymoviecatalogue


import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.muklas.mymoviecatalogue.adapter.TvShowAdapter
import com.muklas.mymoviecatalogue.model.TvShow
import kotlinx.android.synthetic.main.fragment_tv_show.view.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {

    private lateinit var dataTitle: Array<String>
    private lateinit var dataDescription: Array<String>
    private lateinit var dataGenre: Array<String>
    private lateinit var dataType: Array<String>
    private lateinit var dataLanguage: Array<String>
    private lateinit var dataRuntime: Array<String>
    private lateinit var dataImage: TypedArray
    private var tvShows = arrayListOf<TvShow>()

    companion object {
        const val EXTRA_TV = "extra_tv"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tv_show, container, false)

        prepare()
        addItem()

        view.rvTvShow.layoutManager = LinearLayoutManager(view.context)
        val adapter = TvShowAdapter(view.context, tvShows)
        adapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TvShow) {
                val i = Intent(view.context, TvDetailActivity::class.java)
                i.putExtra(EXTRA_TV, data)
                startActivity(i)
            }
        })
        view.rvTvShow.adapter = adapter

        return view
    }

    private fun prepare() {
        dataTitle = resources.getStringArray(R.array.data_tv_title)
        dataDescription = resources.getStringArray(R.array.data_tv_description)
        dataGenre = resources.getStringArray(R.array.data_tv_genre)
        dataType = resources.getStringArray(R.array.data_tv_type)
        dataLanguage = resources.getStringArray(R.array.data_tv_language)
        dataRuntime = resources.getStringArray(R.array.data_tv_time)
        dataImage = resources.obtainTypedArray(R.array.data_tv_image)
    }

    private fun addItem() {
        for (position in dataTitle.indices) {
            val tvShow = TvShow(
                dataTitle[position],
                dataDescription[position],
                dataGenre[position],
                dataType[position],
                dataLanguage[position],
                dataRuntime[position],
                dataImage.getResourceId(position, -1)
            )
            tvShows.add(tvShow)
        }
    }

}
