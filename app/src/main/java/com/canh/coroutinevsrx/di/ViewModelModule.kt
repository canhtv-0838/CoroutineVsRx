package com.canh.coroutinevsrx.di

import com.canh.coroutinevsrx.ui.genres.GenresViewModel
import com.canh.coroutinevsrx.ui.movies.MoviesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { GenresViewModel(get()) }
    viewModel { MoviesViewModel(get()) }
}
