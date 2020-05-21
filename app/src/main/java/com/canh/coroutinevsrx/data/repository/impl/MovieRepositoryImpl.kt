package com.canh.coroutinevsrx.data.repository.impl

import com.canh.coroutinevsrx.data.remote.ApiServiceInterface
import com.canh.coroutinevsrx.data.remote.response.GenresResponse
import com.canh.coroutinevsrx.data.remote.response.MovieResponse
import com.canh.coroutinevsrx.data.repository.MovieRepository

class MovieRepositoryImpl(private val api: ApiServiceInterface) : MovieRepository {
    override suspend fun getGenreMovieList(): GenresResponse =
        api.getGenreMovieList()

    override suspend fun getMovieByGenres(withGenres: Int): MovieResponse =
        api.discoverMovie(withGenres)
}
