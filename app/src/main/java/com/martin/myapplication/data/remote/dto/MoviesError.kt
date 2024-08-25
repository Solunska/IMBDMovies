package com.martin.myapplication.data.remote.dto

data class MoviesError(
    val status_code: Int,
    val status_message: String,
    val success: Boolean
)