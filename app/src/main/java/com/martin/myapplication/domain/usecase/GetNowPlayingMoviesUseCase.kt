package com.martin.myapplication.domain.usecase

import coil.network.HttpException
import com.martin.myapplication.data.mapper.GetMovieMapper
import com.martin.myapplication.data.remote.dto.MovieDTO
import com.martin.myapplication.data.remote.dto.MoviesError
import com.martin.myapplication.data.repository.MoviesRepository
import com.martin.myapplication.domain.model.MovieModel
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject


class GetNowPlayingMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val getMovieMapper: GetMovieMapper,
) {
    operator fun invoke(): Flow<ApiResult<MovieModel, MoviesError>> = flow {
        try {
            val movies = moviesRepository.getNowPlayingMovies()
            val result = getMovieMapper.mapAsResult(movies)
            emit(result)
        } catch (e: HttpException) {
            TODO()
        } catch (e: IOException) {
            TODO()
        }
    }
}