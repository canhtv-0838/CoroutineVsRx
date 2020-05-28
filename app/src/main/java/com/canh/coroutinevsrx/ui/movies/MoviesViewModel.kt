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
//        runSupervisorScope()
//        runScopeHierarchy()
        cancelCoroutine()
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
        GlobalScope.launch {
            log("Run coroutine in GlobalScope")
        }

        viewModelScope.launch {
            log("Run coroutine in viewModelScope")
        }

    }

    /**
     * if a coroutines is canncel, other continue running
     */
    fun runSupervisorScope() = runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
        supervisorScope {
            val first = launch(handler) {
                log("Child throws an exception")
                throw AssertionError()
            }
            val second = launch {
                delay(100)
                log("Scope is completing")
            }
        }
        log("Scope is completed")
    }


    /**
     * Child coroutine use parent properties
     * parent wait until all child complete
     */
    fun runScopeHierarchy() = runBlocking {
        // scope 1
        launch {
            // coroutine 1
            delay(200L)
            log("Task from runBlocking")
        }

        coroutineScope {
            // coroutine 2   // scope 2
            launch {
                // coroutine 3
                delay(500L)
                log("Task from nested launch")
            }

            delay(100L)
            log("Task from coroutine scope")
        }

        log("Coroutine scope is over")
    }

    fun cancelCoroutine() = runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    log("I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                // Tranh thủ close resource trong này đi nha :D
                log("I'm running finally")
            }
        }
        delay(1300L) // delay a bit
        log("main: I'm tired of waiting!")
        job.cancel() // cancels the job
        log("main: Now I can quit.")
    }

    override fun onCleared() {
        super.onCleared()
        log("globalScope: ${GlobalScope.isActive} / viewModelScope: ${viewModelScope.isActive}")
    }
}
