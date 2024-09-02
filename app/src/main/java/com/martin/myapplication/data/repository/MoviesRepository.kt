package com.martin.myapplication.data.repository

import com.martin.myapplication.data.remote.api.MoviesApi
import com.martin.myapplication.data.remote.dto.AddToWatchlistResponse
import com.martin.myapplication.data.remote.dto.MovieDTO
import com.martin.myapplication.data.remote.dto.MovieDetailsDTO
import com.martin.myapplication.data.remote.dto.MovieReviewsDTO
import com.martin.myapplication.data.remote.dto.MoviesError
import com.martin.myapplication.data.remote.dto.SearchMovieDTO
import com.martin.myapplication.presentation.view.Movie
import com.slack.eithernet.ApiResult
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesApi: MoviesApi) {

    suspend fun getTopRatedMovies(): ApiResult<MovieDTO, MoviesError> {
        val result = moviesApi.getTopRatedMovies()

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

    suspend fun getNowPlayingMovies(): ApiResult<MovieDTO, MoviesError> {
        val result = moviesApi.getNowPlayingMovies()

        when (result) {
            is ApiResult.Success -> {
                val nowPlayingMovies = result.value.results
                println("Fetched Movies: $nowPlayingMovies")
            }

            is ApiResult.Failure -> {
                println("Error fetching now playing movies")
            }
        }
        return result
    }

    suspend fun getUpcomingMovies(): ApiResult<MovieDTO, MoviesError> {
        val result = moviesApi.getUpcomingMovies()

        when (result) {
            is ApiResult.Success -> {
                val upcomingMovies = result.value.results
                println("Fetched Movies: $upcomingMovies")
            }

            is ApiResult.Failure -> {
                println("Error fetching upcoming movies")
            }
        }

        return result
    }

    suspend fun getPopularMovies(): ApiResult<MovieDTO, MoviesError> {
        val result = moviesApi.getPopularMovies()

        when (result) {
            is ApiResult.Success -> {
                val popularMovies = result.value.results
                println("Fetched Movies: $popularMovies")
            }

            is ApiResult.Failure -> {
                println("Error fetching popular movies")
            }
        }

        return result
    }

    suspend fun getMovieDetails(id: Int): ApiResult<MovieDetailsDTO, MoviesError> {


        val result = moviesApi.getMovieDetails(id)

        when (result) {
            is ApiResult.Success -> {
                val movieDetails = result.value
                println("Fetched Movie Details: $movieDetails")
            }

            is ApiResult.Failure -> {
                println("Error fetching movie details")
            }
        }

        return result
    }

    suspend fun getMovieReviews(id: Int): ApiResult<MovieReviewsDTO, MoviesError> {
        val result = moviesApi.getMovieReviews(id)

        when (result) {
            is ApiResult.Success -> {
                val movieReviews = result.value.results
                println("Fetched Movie Reviews: $movieReviews")
            }

            is ApiResult.Failure -> {
                println("Error fetching movie reviews")
            }
        }

        return result
    }

    suspend fun getMovieFromSearch(input: String): ApiResult<SearchMovieDTO, MoviesError> {
        val result = moviesApi.getMovieFromSearch(input)

        when (result) {
            is ApiResult.Success -> {
                val movies = result.value.results
                println("Fetched Movie Results from input $input: $movies")
            }

            is ApiResult.Failure -> {
                println("Error fetching movie results")
            }
        }

        return result
    }

    suspend fun addMoviesToWatchlist(
        id: Int,
        watchlistRequest: MoviesApi.WatchlistRequest,
    ): ApiResult<AddToWatchlistResponse, MoviesError> {
        val result = moviesApi.addMovieToWatchList(id, watchlistRequest)

        when (result) {
            is ApiResult.Success -> {
                println("Movie added to watchlist successfully: ${result.value}")
            }

            is ApiResult.Failure -> {
                println("Error adding movie to watchlist")
            }
        }

        return result
    }

}