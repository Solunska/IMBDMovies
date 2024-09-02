package com.martin.myapplication.domain.usecase

import coil.network.HttpException
import com.martin.myapplication.data.mapper.GetMovieDetailsMapper
import com.martin.myapplication.data.mapper.GetMovieMapper
import com.martin.myapplication.data.remote.api.MoviesApi
import com.martin.myapplication.data.remote.dto.AddToWatchlistResponse
import com.martin.myapplication.data.remote.dto.MovieDTO
import com.martin.myapplication.data.remote.dto.MoviesError
import com.martin.myapplication.data.repository.MoviesRepository
import com.martin.myapplication.domain.model.MovieDetailsModel
import com.martin.myapplication.domain.model.MovieModel
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class AddMovieToWatchlistUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(id: Int, watchlistRequest: MoviesApi.WatchlistRequest): Flow<ApiResult<AddToWatchlistResponse, MoviesError>> = flow {
        try {
            val result = moviesRepository.addMoviesToWatchlist(id,watchlistRequest)
            emit(result)
        } catch (e: HttpException) {
            TODO()
        } catch (e: IOException) {
            TODO()
        }
    }
}