package com.martin.myapplication.data.remote.api

import com.martin.myapplication.data.remote.model.TopMoviesResponse
import com.martin.myapplication.data.remote.model.TopRatedMoviesError
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/top_rated")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): ApiResult<TopMoviesResponse, TopRatedMoviesError>
}


