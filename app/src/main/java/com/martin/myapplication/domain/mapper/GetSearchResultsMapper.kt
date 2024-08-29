package com.martin.myapplication.data.mapper

import com.martin.myapplication.data.remote.dto.MovieDTO
import com.martin.myapplication.data.remote.dto.MovieDetailsDTO
import com.martin.myapplication.data.remote.dto.MoviesError
import com.martin.myapplication.data.remote.dto.SearchMovieDTO
import com.martin.myapplication.domain.model.MovieDetailsModel
import com.martin.myapplication.domain.model.MovieModel
import com.martin.myapplication.domain.model.SearchMovieModel
import com.slack.eithernet.ApiResult
import javax.inject.Inject

class GetSearchResultsMapper @Inject constructor() {

    fun mapAsResult(response: ApiResult<SearchMovieDTO, MoviesError>): ApiResult<SearchMovieModel, MoviesError> {
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

fun SearchMovieDTO.toMovieModel(): SearchMovieModel {
    return SearchMovieModel(
        results = this.results.map { result ->
            SearchMovieModel.Result(
                genreIds = result.genreIds,
                originalTitle = result.originalTitle,
                posterPath = result.posterPath.toString(),
                releaseDate = result.releaseDate
            )
        }
    )
}
