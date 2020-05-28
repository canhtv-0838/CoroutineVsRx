package com.canh.coroutinevsrx.data.remote.response

import com.canh.coroutinevsrx.data.model.Genre
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres")
    @Expose
    val genres: List<Genre>
)
