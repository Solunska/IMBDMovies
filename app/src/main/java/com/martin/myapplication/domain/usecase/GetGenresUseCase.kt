package com.martin.myapplication.domain.usecase

import coil.network.HttpException
import com.martin.myapplication.data.remote.dto.Genre
import com.martin.myapplication.data.remote.dto.GenresResponse
import com.martin.myapplication.data.remote.dto.MoviesError
import com.martin.myapplication.data.repository.MoviesRepository
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
) {
    operator fun invoke(): Flow<ApiResult<GenresResponse, MoviesError>> = flow {
        try {
            val genres = moviesRepository.getGenres()
            emit(genres)
        } catch (e: HttpException) {
            TODO()
        } catch (e: IOException) {
            TODO()
        }
    }
}