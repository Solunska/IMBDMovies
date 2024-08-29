package com.martin.myapplication.data.mapper

import com.martin.myapplication.data.remote.dto.MovieDTO
import com.martin.myapplication.data.remote.dto.MovieDetailsDTO
import com.martin.myapplication.data.remote.dto.MovieReviewsDTO
import com.martin.myapplication.data.remote.dto.MoviesError
import com.martin.myapplication.domain.model.MovieDetailsModel
import com.martin.myapplication.domain.model.MovieModel
import com.martin.myapplication.domain.model.MovieReviewsModel
import com.slack.eithernet.ApiResult
import javax.inject.Inject

class GetMovieReviewsMapper @Inject constructor() {

    fun mapAsResult(response: ApiResult<MovieReviewsDTO, MoviesError>): ApiResult<MovieReviewsModel, MoviesError> {
        return when (response) {
            is ApiResult.Success -> {
                val movieModel = response.value.toMovieModel()
                ApiResult.success(movieModel)
            }

            is ApiResult.Failure ->  {
                response
            }
        }
    }
}

fun MovieReviewsDTO.toMovieModel(): MovieReviewsModel {
    return MovieReviewsModel(
        results = this.results.map { result ->
            MovieReviewsModel.Result(
                authorDetails = result.authorDetails,
                content = result.content
            )
        }
    )
}
