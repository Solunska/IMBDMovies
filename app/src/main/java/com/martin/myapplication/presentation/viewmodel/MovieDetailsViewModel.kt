package com.martin.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.myapplication.domain.usecase.GetMovieDetailsUseCase
import com.martin.myapplication.domain.usecase.GetMovieReviewsUseCase
import com.martin.myapplication.presentation.state.DetailsUiState
import com.slack.eithernet.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetailsUseCase,
    private val getMovieReviews: GetMovieReviewsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsUiState())
    val state: StateFlow<DetailsUiState> = _state

    fun fetchMovieDetails(id: Int) {
        viewModelScope.launch {
            _state.update { uiState ->
                uiState.copy(isLoading = true)
            }

            getMovieDetails(id).collect { result ->
                _state.update { uiState ->
                    when(result){
                        is ApiResult.Success -> uiState.copy(
                            movieDetails = result.value,
                            error = ""
                        )
                        is ApiResult.Failure -> uiState.copy(
                            movieDetails = null,
                            error = ""
                        )
                    }
                }
            }

            getMovieReviews(id).collect { result ->
                _state.update { uiState ->
                    when(result){
                        is ApiResult.Success -> uiState.copy(
                            movieReviews = result.value
                        )
                        is ApiResult.Failure -> uiState.copy(
                            movieReviews = null,
                        )
                    }
                }

            }

            _state.update { uiState ->
                uiState.copy(isLoading = false)
            }
        }
    }
}
