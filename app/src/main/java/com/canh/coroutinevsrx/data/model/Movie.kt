package com.canh.coroutinevsrx.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id")
    @Expose
    val id: Int,
//    @SerializedName("adult")
//    @Expose
//    val adult: Boolean,
//    @SerializedName("backdrop_path")
//    @Expose
//    val backdropPath: String? = null,
//    @SerializedName("budget")
//    @Expose
//    val budget: Int,
//    @SerializedName("genres")
//    @Expose
//    val genres: List<Genre>,
//    @SerializedName("homepage")
//    @Expose
//    val homePage: String? = null,
//    @SerializedName("original_language")
//    @Expose
//    val originalLanguage: String,
//    @SerializedName("original_title")
//    @Expose
//    val originalTitle: String,
    @SerializedName("overview")
    @Expose
    val overview: String? = null,
    @SerializedName("title")
    @Expose
    val title: String,
//    @SerializedName("popularity")
//    @Expose
//    val popularity: Double,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String? = null,
    @SerializedName("release_date")
    @Expose
    val releaseDate: String,
//    @SerializedName("revenue")
//    @Expose
//    val revenue: Int? = null,
//    @SerializedName("runtime")
//    @Expose
//    val runtime: Int? = null,
//    @SerializedName("video")
//    @Expose
//    val video: Boolean,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double
//    @SerializedName("vote_count")
//    @Expose
//    val voteCount: Int,
//    @SerializedName("tagline")
//    @Expose
//    val tagLine: String? = null,
//    @SerializedName("status")
//    @Expose
//    val status: String
) : Parcelable