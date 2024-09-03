package com.martin.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.myapplication.data.remote.dto.Genre
import com.martin.myapplication.data.remote.dto.GenresResponse
import com.martin.myapplication.domain.model.SearchMovieModel
import com.martin.myapplication.domain.usecase.GetGenresUseCase
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
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("OPT_IN_USAGE")
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchResults: GetSearchResultsUseCase,
    private val getGenres: GetGenresUseCase
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state

    init {
        searchText
            .debounce(1000)
            .onEach { _isSearching.update { true } }
            .filter { it.isNotEmpty() }
            .onEach { query ->
                getMovieResults(query)
            }
            .onEach { _isSearching.update { false } }
            .launchIn(viewModelScope)
        getMovieGenres()
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onEmptyInput() {
        _state.update { it.copy(movies = emptyList()) }
    }

    fun getGenreNameById(genreIds: List<Int>, genres: List<Genre>): String {
        val genre = genres.find { it.id in genreIds }
        return genre?.name ?: "Unknown Genre"
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

    private fun getMovieGenres(){
        getGenres().onEach { result ->
            _state.update { uiState ->
                when(result) {
                    is ApiResult.Success -> {
                        uiState.copy(genres = result.value.genres)
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
