package com.martin.myapplication.domain.model
import com.martin.myapplication.data.remote.dto.Genre

data class MovieDetailsModel (
    val genres: List<Genre>,
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
)