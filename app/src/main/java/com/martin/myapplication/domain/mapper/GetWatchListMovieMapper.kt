package com.martin.myapplication.data.mapper

import com.martin.myapplication.data.remote.dto.MovieDTO
import com.martin.myapplication.data.remote.dto.MovieDetailsDTO
import com.martin.myapplication.data.remote.dto.MoviesError
import com.martin.myapplication.data.remote.dto.WatchListMoviesDTO
import com.martin.myapplication.domain.model.MovieDetailsModel
import com.martin.myapplication.domain.model.MovieModel
import com.martin.myapplication.domain.model.WatchListMoviesModel
import com.slack.eithernet.ApiResult
import javax.inject.Inject

class GetWatchListMovieMapper @Inject constructor() {

    fun mapAsResult(response: ApiResult<WatchListMoviesDTO, MoviesError>): ApiResult<WatchListMoviesModel, MoviesError> {
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

fun WatchListMoviesDTO.toMovieModel(): WatchListMoviesModel {
    return WatchListMoviesModel(
        results = this.results.map { result ->
            WatchListMoviesModel.Result(
                posterPath = result.posterPath,
                releaseDate = result.releaseDate,
                genreIds = result.genreIds,
                title = result.title
            )
        }
    )
}
