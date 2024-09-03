package com.martin.myapplication.presentation.state

import com.martin.myapplication.data.remote.dto.Genre
import com.martin.myapplication.domain.model.MovieDetailsModel
import com.martin.myapplication.domain.model.MovieModel
import com.martin.myapplication.domain.model.MovieReviewsModel
import com.martin.myapplication.domain.model.SearchMovieModel

data class SearchUiState(
    val isLoading: Boolean = false,
    val movies: List<SearchMovieModel.Result> = emptyList(),
    val genres: List<Genre> = emptyList(),
    var error: String = "",
)