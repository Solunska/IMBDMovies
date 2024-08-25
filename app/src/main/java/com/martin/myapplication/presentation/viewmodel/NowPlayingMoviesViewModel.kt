package com.martin.myapplication.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.myapplication.domain.usecase.GetNowPlayingMoviesUseCase
import com.martin.myapplication.domain.usecase.GetTopRatedMoviesUseCase
import com.martin.myapplication.presentation.state.UIState
import com.slack.eithernet.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NowPlayingMoviesViewModel @Inject constructor(
    private val getNowPlayingMovies: GetNowPlayingMoviesUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(UIState())
    val state: State<UIState> = _state

    init {
        getMovies()
    }

    private fun getMovies() {
        getNowPlayingMovies().onEach { result ->
            when (result) {
                is ApiResult.Success -> {
                    _state.value = UIState(result = result.value.results)
                }

                is ApiResult.Failure -> {
                    _state.value = UIState(error = "An unexpected error occured")
                }
            }
        }.launchIn(viewModelScope)
    }
}

