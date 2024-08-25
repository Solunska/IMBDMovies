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
class TopMoviesViewModel @Inject constructor(
    private val getTopRatedMovies: GetTopRatedMoviesUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(UIState())
    val state: State<UIState> = _state

    init {
        getMovies()
    }

    private fun getMovies() {
        getTopRatedMovies().onEach { result ->
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


//    private val setUIState = MutableStateFlow(UIState())
//    val uiState = setUIState.asStateFlow()

//    private val _state = MutableStateFlow<ApiResult<MovieModel, MoviesError>?>(null)
//    val state: StateFlow<ApiResult<MovieModel, MoviesError>?> get() = _state
//
//    private val _isLoading = MutableStateFlow(false)
//    val isLoading: StateFlow<Boolean> get() = _isLoading
//    private fun fetchTopRatedMovies() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            val topRatedMovies = topRatedMoviesUseCase.invoke()
//            _state.value = topRatedMovies
//            _isLoading.value = false
//        }
//    }
//
//    private val _state2 = MutableStateFlow<ApiResult<MovieDTO, MoviesError>?>(null)
//    val state2: StateFlow<ApiResult<MovieDTO, MoviesError>?> get() = _state2
//
//    private fun fetchNowPlayingMovies() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            val topRatedMovies = topRatedMoviesRepository.getNowPlayingMovies()
//            _state2.value = topRatedMovies
//            _isLoading.value = false
//        }
//    }

