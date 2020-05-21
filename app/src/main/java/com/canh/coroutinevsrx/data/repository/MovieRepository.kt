package com.canh.coroutinevsrx.data.repository

import com.canh.coroutinevsrx.data.remote.response.GenresResponse
import com.canh.coroutinevsrx.data.remote.response.MovieResponse

interface MovieRepository {
    suspend fun getGenreMovieList(): GenresResponse

    suspend fun getMovieByGenres(withGenres: Int): MovieResponse
}
