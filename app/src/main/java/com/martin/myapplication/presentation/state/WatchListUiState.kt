package com.martin.myapplication.presentation.state

import com.martin.myapplication.data.remote.dto.Genre
import com.martin.myapplication.domain.model.MovieDetailsModel
import com.martin.myapplication.domain.model.MovieModel
import com.martin.myapplication.domain.model.MovieReviewsModel
import com.martin.myapplication.domain.model.SearchMovieModel
import com.martin.myapplication.domain.model.WatchListMoviesModel

data class WatchListUiState(
    val isLoading: Boolean = false,
    val movies: List<WatchListMoviesModel.Result> = emptyList(),
    val genres: List<Genre> = emptyList(),
    var error: String = "",
)