package com.martin.myapplication.data.remote.api

import retrofit2.http.GET

interface MoviesApi {

    @GET("example-route")
    suspend fun callApi()
}