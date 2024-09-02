package com.martin.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.myapplication.domain.usecase.GetWatchListMoviesUseCase
import com.martin.myapplication.presentation.state.WatchListUiState
import com.slack.eithernet.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("OPT_IN_USAGE")
@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val getWatchListMovies: GetWatchListMoviesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(WatchListUiState())
    val state: StateFlow<WatchListUiState> = _state

    fun fetchMoviesFromWatchList(id: Int) {
        viewModelScope.launch {
            _state.update { uiState ->
                uiState.copy(isLoading = true)
            }

            getWatchListMovies(id).collect { result ->
                _state.update { uiState ->
                    when (result) {
                        is ApiResult.Success -> uiState.copy(
                            movies = result.value.results,
                            error = ""
                        )

                        is ApiResult.Failure -> uiState.copy(
                            movies = emptyList(),
                            error = "An error has occurred"
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
