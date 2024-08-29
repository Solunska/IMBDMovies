package com.martin.myapplication.data.remote.api

import com.martin.myapplication.data.remote.dto.MovieDTO
import com.martin.myapplication.data.remote.dto.MovieDetailsDTO
import com.martin.myapplication.data.remote.dto.MovieReviewsDTO
import com.martin.myapplication.data.remote.dto.MoviesError
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): ApiResult<MovieDTO, MoviesError>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): ApiResult<MovieDTO, MoviesError>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): ApiResult<MovieDTO, MoviesError>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): ApiResult<MovieDTO, MoviesError>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): ApiResult<MovieDetailsDTO, MoviesError>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") id: Int,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): ApiResult<MovieReviewsDTO, MoviesError>
}