package com.martin.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.myapplication.domain.usecase.GetNowPlayingMoviesUseCase
import com.martin.myapplication.domain.usecase.GetPopularMoviesUseCase
import com.martin.myapplication.domain.usecase.GetSearchResultsUseCase
import com.martin.myapplication.domain.usecase.GetTopRatedMoviesUseCase
import com.martin.myapplication.domain.usecase.GetUpcomingMoviesUseCase
import com.martin.myapplication.presentation.state.HomeUiState
import com.martin.myapplication.presentation.state.SearchUiState
import com.slack.eithernet.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchResults: GetSearchResultsUseCase,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun getMovieResults(input: String) {
        getSearchResults(input).onEach { result ->
            _state.update { uiState ->
                when (result) {
                    is ApiResult.Success -> {
                        uiState.copy(movies = result.value.results)
                    }

                    is ApiResult.Failure -> {
                        uiState.copy(error = "An unexpected error occurred")
                    }

                    else -> uiState
                }
            }
        }.launchIn(viewModelScope)
    }
}
