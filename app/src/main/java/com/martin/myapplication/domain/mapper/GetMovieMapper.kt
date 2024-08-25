package com.martin.myapplication.data.mapper

import com.martin.myapplication.data.remote.dto.MovieDTO
import com.martin.myapplication.data.remote.dto.MoviesError
import com.martin.myapplication.domain.model.MovieModel
import com.slack.eithernet.ApiResult
import javax.inject.Inject

class GetMovieMapper @Inject constructor() {

    fun mapAsResult(response: ApiResult<MovieDTO, MoviesError>): ApiResult<MovieModel, MoviesError> {
        return when (response) {
            is ApiResult.Success -> {
                val movieModel = response.value.toMovieModel()
                ApiResult.success(movieModel)
            }

            is ApiResult.Failure -> {
                TODO()
            }
        }
    }
}

fun MovieDTO.toMovieModel(): MovieModel {
    return MovieModel(
        results = this.results.map { result ->
            MovieModel.Result(
                id = result.id,
                genreIds = result.genreIds,
                title = result.title,
                overview = result.overview,
                popularity = result.popularity,
                posterPath = result.posterPath,
                releaseDate = result.releaseDate
            )
        }
    )
}
