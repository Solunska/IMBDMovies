package com.martin.myapplication.domain.model

import com.martin.myapplication.data.remote.dto.AuthorDetailsX
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class MovieReviewsModel(
    val results: List<Result>,
) {
    data class Result(
        val authorDetails: AuthorDetailsX,
        val content: String,
    )
}