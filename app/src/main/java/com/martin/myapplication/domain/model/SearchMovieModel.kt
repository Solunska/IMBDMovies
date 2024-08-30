package com.martin.myapplication.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class SearchMovieModel(
    val results: List<Result>,
) {
    data class Result(
        val genreIds: List<Int>,
        val originalTitle: String,
        val posterPath: String?,
        val releaseDate: String,
    )
}