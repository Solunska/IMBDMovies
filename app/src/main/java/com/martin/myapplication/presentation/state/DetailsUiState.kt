package com.martin.myapplication.presentation.state

import com.martin.myapplication.domain.model.MovieDetailsModel

data class DetailsUiState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetailsModel? = null,
    var error: String = "",
)