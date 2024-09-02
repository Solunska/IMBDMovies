package com.martin.myapplication.presentation.state

import com.martin.myapplication.domain.model.MovieDetailsModel
import com.martin.myapplication.domain.model.MovieModel
import com.martin.myapplication.domain.model.MovieReviewsModel
import com.martin.myapplication.domain.model.SearchMovieModel
import com.martin.myapplication.domain.model.WatchListMoviesModel

data class WatchListUiState(
    val isLoading: Boolean = false,
    val movies: List<WatchListMoviesModel.Result> = emptyList(),
    var error: String = "",
)