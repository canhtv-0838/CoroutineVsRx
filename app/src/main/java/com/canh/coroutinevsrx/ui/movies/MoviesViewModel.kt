package com.canh.coroutinevsrx.ui.movies

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.canh.coroutinevsrx.data.model.Genre
import com.canh.coroutinevsrx.data.model.Movie
import com.canh.coroutinevsrx.data.repository.MovieRepository
import com.canh.coroutinevsrx.ui.base.BaseViewModel
import com.canh.coroutinevsrx.util.log
import kotlinx.coroutines.*

class MoviesViewModel(private val repository: MovieRepository) : BaseViewModel() {

    companion object {
        private const val ARGUMENT_GENRE = "argument_genre"

        fun setArguments(genre: Genre) =
            Bundle().apply {
                putParcelable(ARGUMENT_GENRE, genre)
            }
    }
    init {
//        runCoroutineInScopes()
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
        viewModelScope.launch(Dispatchers.IO) {
            val moviesResponse = repository.getMovieByGenres(genre.id)
            withContext(Dispatchers.Main) {
                movies.value = moviesResponse.results
            }
        }
    }

    fun runCoroutineInScopes() {
//        GlobalScope.launch {
//            log("Run coroutine in GlobalScope")
//        }

        viewModelScope.launch() {
            val job = launch {
                repeat(100){
                    delay(1000L)
                    log("Run coroutine in viewModelScope")
                }
            }
            delay(2000)
            job.cancel()
        }

    }

    override fun onCleared() {
        super.onCleared()
        log("globalScope: ${GlobalScope.isActive} / viewModelScope: ${viewModelScope.isActive}")
    }
}
