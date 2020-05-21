package com.canh.coroutinevsrx.ui.movies

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.canh.coroutinevsrx.data.model.Genre
import com.canh.coroutinevsrx.data.model.Movie
import com.canh.coroutinevsrx.data.repository.MovieRepository
import com.canh.coroutinevsrx.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel(private val repository: MovieRepository) : BaseViewModel() {

    companion object {
        private const val ARGUMENT_GENRE = "argument_genre"

        fun setArguments(genre: Genre) =
            Bundle().apply {
                putParcelable(ARGUMENT_GENRE, genre)
            }
    }

    val genre = MutableLiveData<Genre>()
    val movies = MutableLiveData<List<Movie>>()

    fun getArguments(arguments: Bundle?) {
        arguments?.let {
            val genre = arguments.get(ARGUMENT_GENRE) as Genre?
            genre?.let {
                this.genre.postValue(genre)
            }
        }
    }

    fun getMovies(genre: Genre) {
        viewModelScope.launch(Dispatchers.Default) {
            val moviesResponse = repository.getMovieByGenres(genre.id)
            withContext(Dispatchers.Main) {
                movies.value = moviesResponse.results
            }
        }
    }
}
