package com.martin.myapplication.data.remote.dto


import com.martin.myapplication.domain.model.MovieModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDTO(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<Result>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int,
) {

    @JsonClass(generateAdapter = true)
    data class Result(
        @Json(name = "adult")
        val adult: Boolean,
        @Json(name = "backdrop_path")
        val backdropPath: String,
        @Json(name = "genre_ids")
        val genreIds: List<Int>,
        @Json(name = "id")
        val id: Int,
        @Json(name = "original_language")
        val originalLanguage: String,
        @Json(name = "original_title")
        val originalTitle: String,
        @Json(name = "overview")
        val overview: String,
        @Json(name = "popularity")
        val popularity: Double,
        @Json(name = "poster_path")
        val posterPath: String,
        @Json(name = "release_date")
        val releaseDate: String,
        @Json(name = "title")
        val title: String,
        @Json(name = "video")
        val video: Boolean,
        @Json(name = "vote_average")
        val voteAverage: Double,
        @Json(name = "vote_count")
        val voteCount: Int,
    )
}

//fun MovieDTO.toMovieModel(): MovieModel {
//    return MovieModel(
//        results = this.results.map { result ->
//            MovieModel.Result(
//                id = result.id,
//                genreIds = result.genreIds,
//                title = result.title,
//                overview = result.overview,
//                popularity = result.popularity,
//                posterPath = result.posterPath,
//                releaseDate = result.releaseDate
//            )
//        }
//    )
//}
