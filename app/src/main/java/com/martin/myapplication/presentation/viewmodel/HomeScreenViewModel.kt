package com.martin.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.myapplication.domain.usecase.GetNowPlayingMoviesUseCase
import com.martin.myapplication.domain.usecase.GetPopularMoviesUseCase
import com.martin.myapplication.domain.usecase.GetTopRatedMoviesUseCase
import com.martin.myapplication.domain.usecase.GetUpcomingMoviesUseCase
import com.martin.myapplication.presentation.state.UIState
import com.slack.eithernet.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getTopRatedMovies: GetTopRatedMoviesUseCase,
    private val getNowPlayingMovies: GetNowPlayingMoviesUseCase,
    private val getPopularMovies: GetPopularMoviesUseCase,
    private val getUpcomingMovies: GetUpcomingMoviesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(UIState())
    val state: StateFlow<UIState> = _state

    init {
        getMovies()
    }

    private fun getMovies() {
        val combinedFlow = combine(
            getTopRatedMovies.invoke(),
            getNowPlayingMovies.invoke(),
            getPopularMovies.invoke(),
            getUpcomingMovies.invoke()
        ) { topRated, nowPlaying, popular, upcoming ->

            val topRatedMovies = if (topRated is ApiResult.Success) {
                topRated.value.results
            } else {
                emptyList()
            }

            val nowPlayingMovies = if (nowPlaying is ApiResult.Success) {
                nowPlaying.value.results
            } else {
                emptyList()
            }

            val popularMovies = if (popular is ApiResult.Success) {
                popular.value.results
            } else {
                emptyList()
            }

            val upcomingMovies = if (upcoming is ApiResult.Success) {
                upcoming.value.results
            } else {
                emptyList()
            }

            _state.update { uiState ->
                uiState.copy(
                    topRatedMovies = topRatedMovies,
                    nowPlayingMovies = nowPlayingMovies,
                    popularMovies = popularMovies,
                    upcomingMovies = upcomingMovies
                )
            }
        }.launchIn(viewModelScope)
    }
}
