package com.martin.myapplication.domain.model

import com.martin.myapplication.data.remote.dto.WatchListMoviesDTO.Result
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class WatchListMoviesModel (
    val results: List<Result>,
){
    data class Result(
        val genreIds: List<Int>,
        val posterPath: String,
        val releaseDate: String,
        val title: String,
    )
}