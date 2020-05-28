package com.canh.coroutinevsrx.data.remote

import com.canh.coroutinevsrx.data.remote.response.GenresResponse
import com.canh.coroutinevsrx.data.remote.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceInterface {
    @GET("genre/movie/list")
    suspend fun getGenreMovieList(): GenresResponse

    @GET("discover/movie")
    suspend fun discoverMovie(
        @Query("with_genres") with_genres: Int? = null
    ): MovieResponse
}
