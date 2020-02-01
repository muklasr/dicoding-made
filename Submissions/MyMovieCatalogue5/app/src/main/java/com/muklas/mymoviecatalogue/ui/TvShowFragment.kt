package com.muklas.mymoviecatalogue.ui


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.muklas.mymoviecatalogue.R
import com.muklas.mymoviecatalogue.adapter.TvShowAdapter
import com.muklas.mymoviecatalogue.helper.InternetCheck
import com.muklas.mymoviecatalogue.model.TvShow
import com.muklas.mymoviecatalogue.ui.viewmodel.TvShowViewModel
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.android.synthetic.main.fragment_tv_show.view.*

/**
 * A simple [Fragment] subclass.
 */
class TvShowFragment : Fragment() {

    private lateinit var adapter: TvShowAdapter
    private lateinit var tvShowViewModel: TvShowViewModel

    companion object {
        const val EXTRA_TV = "extra_tv"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tv_show, container, false)

        adapter = TvShowAdapter()
        adapter.notifyDataSetChanged()

        view.rvTvShow.layoutManager = LinearLayoutManager(view.context)
        adapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TvShow) {
                val i = Intent(view.context, TvDetailActivity::class.java)
                i.putExtra(EXTRA_TV, data)
                startActivity(i)
            }
        })
        view.rvTvShow.adapter = adapter

        tvShowViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(TvShowViewModel::class.java)
        tvShowViewModel.setTvShow(null)
        showLoading(view, true)

        tvShowViewModel.getTvShow().observe(this, Observer {
            if (it != null) {
                adapter.setData(it)
                showLoading(view, false)
            }
        })

        checkConnection(view)

        return view
    }

    private fun showLoading(view: View, state: Boolean) {
        if (state) {
            view.progressBar.visibility = View.VISIBLE
        } else {
            view.progressBar.visibility = View.GONE
        }
    }

    private fun checkConnection(view: View) {
        InternetCheck(object : InternetCheck.Consumer {
            override fun accept(internet: Boolean?) {
                if (!internet!!) {
                    feedback.visibility = View.VISIBLE
                    showLoading(view, false)
                } else {
                    feedback.visibility = View.GONE
                    showLoading(view, true)
                }
            }
        })
    }
}
