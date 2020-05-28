package com.canh.coroutinevsrx.ui.genres

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.canh.coroutinevsrx.data.model.Genre
import com.canh.coroutinevsrx.data.repository.MovieRepository
import com.canh.coroutinevsrx.ui.base.BaseViewModel
import com.canh.coroutinevsrx.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GenresViewModel(private val repository: MovieRepository) : BaseViewModel() {
    var genres = MutableLiveData<List<Genre>>()

    fun getGenreMovieList() {
        viewModelScope.launch(Dispatchers.IO) {
            val genresResponse = repository.getGenreMovieList()
            withContext(Dispatchers.Main) {
                genres.value = genresResponse.genres.asFlow().filter {
                    it.id%2==0
                }.toList()
            }
        }
    }
}
