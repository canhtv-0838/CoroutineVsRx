package com.canh.coroutinevsrx.ui.genres

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.canh.coroutinevsrx.BR
import com.canh.coroutinevsrx.R
import com.canh.coroutinevsrx.databinding.FragmentGenresBinding
import com.canh.coroutinevsrx.ui.base.BaseFragment
import com.canh.coroutinevsrx.ui.movies.MoviesViewModel
import com.canh.coroutinevsrx.util.decorationView
import kotlinx.android.synthetic.main.fragment_genres.*
import org.koin.android.viewmodel.ext.android.viewModel

class GenresFragment : BaseFragment<FragmentGenresBinding, GenresViewModel>() {
    override val viewModel: GenresViewModel by viewModel()
    override val bindingVariable = BR.viewModel
    override val layoutId = R.layout.fragment_genres

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getGenreMovieList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
    }

    private fun setUpAdapter() {
        val genresAdapter = GenresAdapter {
            findNavController().navigate(R.id.movies, MoviesViewModel.setArguments(it))
        }

        rvGenres?.apply {
            adapter = genresAdapter
            addItemDecoration(decorationView())
        }

        viewModel.genres.observe(viewLifecycleOwner, Observer {
            it?.let {
                genresAdapter.submitList(it)
            }
        })
    }
}
