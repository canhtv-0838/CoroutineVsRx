package com.canh.coroutinevsrx.ui.movies

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.canh.coroutinevsrx.BR
import com.canh.coroutinevsrx.R
import com.canh.coroutinevsrx.databinding.FragmentMoviesBinding
import com.canh.coroutinevsrx.ui.base.BaseFragment
import com.canh.coroutinevsrx.util.log
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : BaseFragment<FragmentMoviesBinding, MoviesViewModel>() {
    override val viewModel: MoviesViewModel by viewModel()
    override val bindingVariable = BR.viewModel
    override val layoutId = R.layout.fragment_movies

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getArguments(arguments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }

    private fun setUpAdapter() {
        val movieAdapter = MoviesAdapter {
            log("$it")
        }

        rvMovies?.apply {
            adapter = movieAdapter
        }

        with(viewModel){
            genre.observe(viewLifecycleOwner, Observer {
                it?.let {
                    getMovies(it)
                }
            })

            movies.observe(viewLifecycleOwner, Observer {
                it?.let {
                    log("$it")
                    movieAdapter.submitList(it)
                }
            })
        }
    }
}
