package com.martin.myapplication.domain.model

data class MovieModel(
    val results: List<Result>,
) {
    data class Result(
        val id: Int,
        val genreIds: List<Int>,
        val title: String,
        val overview: String,
        val popularity: Double,
        val posterPath: String,
        val releaseDate: String,
    )
}