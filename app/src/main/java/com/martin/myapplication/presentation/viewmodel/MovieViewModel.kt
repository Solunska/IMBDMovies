package com.martin.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martin.myapplication.data.remote.model.TopMoviesResponse
import com.martin.myapplication.data.remote.model.TopRatedMoviesError
import com.martin.myapplication.data.repository.TopRatedMoviesRepository
import com.slack.eithernet.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val topRatedMoviesRepository: TopRatedMoviesRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ApiResult<TopMoviesResponse, TopRatedMoviesError>?>(null)
    val state: StateFlow<ApiResult<TopMoviesResponse, TopRatedMoviesError>?> get() = _state

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        fetchTopRatedMovies()
    }

    private fun fetchTopRatedMovies() {
        viewModelScope.launch {
            _isLoading.value = true
            val topRatedMovies = topRatedMoviesRepository.getTopRatedMovies()
            _state.value = topRatedMovies
            _isLoading.value = false
        }
    }

}