package com.martin.myapplication.data.remote.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthorDetailsX(
    @Json(name = "avatar_path")
    val avatarPath: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "rating")
    val rating: Int?,
    @Json(name = "username")
    val username: String?
)