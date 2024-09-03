package com.martin.myapplication.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenresResponse(
    @Json(name = "genres")
    val genres: List<Genre>
)
