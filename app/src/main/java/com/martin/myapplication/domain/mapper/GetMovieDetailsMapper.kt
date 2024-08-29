package com.martin.myapplication.data.mapper

import com.martin.myapplication.data.remote.dto.MovieDTO
import com.martin.myapplication.data.remote.dto.MovieDetailsDTO
import com.martin.myapplication.data.remote.dto.MoviesError
import com.martin.myapplication.domain.model.MovieDetailsModel
import com.martin.myapplication.domain.model.MovieModel
import com.slack.eithernet.ApiResult
import javax.inject.Inject

class GetMovieDetailsMapper @Inject constructor() {

    fun mapAsResult(response: ApiResult<MovieDetailsDTO, MoviesError>): ApiResult<MovieDetailsModel, MoviesError> {
        return when (response) {
            is ApiResult.Success -> {
                val movieModel = response.value.toMovieModel()
                ApiResult.success(movieModel)
            }

            is ApiResult.Failure -> when (response) {
                is ApiResult.Failure.NetworkFailure -> error("")
                is ApiResult.Failure.HttpFailure -> error("")
                is ApiResult.Failure.ApiFailure -> error("")
                is ApiResult.Failure.UnknownFailure -> error("")
            }
        }
    }
}

fun MovieDetailsDTO.toMovieModel(): MovieDetailsModel {
    return MovieDetailsModel(
        id = this.id,
        overview = this.overview,
        releaseDate = this.releaseDate,
        posterPath = this.posterPath,
        title = this.title,
        genres = this.genres
    )
}
