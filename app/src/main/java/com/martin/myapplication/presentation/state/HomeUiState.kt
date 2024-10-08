package com.martin.myapplication.presentation.state

import com.martin.myapplication.domain.model.MovieModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val topRatedMovies: List<MovieModel.Result> = emptyList(),
    val nowPlayingMovies: List<MovieModel.Result> = emptyList(),
    val upcomingMovies: List<MovieModel.Result> = emptyList(),
    val popularMovies: List<MovieModel.Result> = emptyList(),
    val error: String = "",
)