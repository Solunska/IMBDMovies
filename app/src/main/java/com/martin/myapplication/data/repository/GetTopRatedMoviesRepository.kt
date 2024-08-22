package com.martin.myapplication.data.repository

import com.martin.myapplication.BuildConfig
import com.martin.myapplication.data.remote.api.MoviesApi
import com.martin.myapplication.data.remote.model.TopMoviesResponse
import com.martin.myapplication.data.remote.model.TopRatedMoviesError
import com.slack.eithernet.ApiResult
import javax.inject.Inject

class GetTopRatedMoviesRepository @Inject constructor(private val moviesApi: MoviesApi) {

    suspend fun getTopRatedMovies(): ApiResult<TopMoviesResponse, TopRatedMoviesError> {
        val result = moviesApi.getPopularMovies(BuildConfig.MOVIES_API_KEY)

        when (result) {
            is ApiResult.Success -> {
                val movies = result.value.results
                println("Fetched Movies: $movies")
            }

            is ApiResult.Failure -> {
                println("Error fetching movies")
            }
        }
        return result
    }
}