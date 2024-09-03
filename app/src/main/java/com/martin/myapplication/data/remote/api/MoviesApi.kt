package com.martin.myapplication.data.remote.api

import com.martin.myapplication.data.remote.dto.AddToWatchlistResponse
import com.martin.myapplication.data.remote.dto.Genre
import com.martin.myapplication.data.remote.dto.GenresResponse
import com.martin.myapplication.data.remote.dto.MovieDTO
import com.martin.myapplication.data.remote.dto.MovieDetailsDTO
import com.martin.myapplication.data.remote.dto.MovieReviewsDTO
import com.martin.myapplication.data.remote.dto.MoviesError
import com.martin.myapplication.data.remote.dto.SearchMovieDTO
import com.martin.myapplication.data.remote.dto.WatchListMoviesDTO
import com.slack.eithernet.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class WatchlistRequest(
    val media_type: String,
    val media_id: Int,
    val watchlist: Boolean,
)

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

    @GET("search/movie")
    suspend fun getMovieFromSearch(
        @Query("query") input: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): ApiResult<SearchMovieDTO, MoviesError>

    @POST("account/{account_id}/watchlist")
    suspend fun addMovieToWatchList(
        @Path("account_id") id: Int = 21456817,
        @Body watchlistRequest: WatchlistRequest,
    ): ApiResult<AddToWatchlistResponse, MoviesError>

    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchListMovies(
        @Path("account_id") id: Int = 21456817,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): ApiResult<WatchListMoviesDTO, MoviesError>

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("language") language: String = "en-US"
    ): ApiResult<GenresResponse, MoviesError>
}