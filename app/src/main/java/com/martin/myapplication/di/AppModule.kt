package com.martin.myapplication.di

import com.martin.myapplication.BuildConfig
import com.martin.myapplication.data.remote.api.MoviesApi
import com.slack.eithernet.ApiResultCallAdapterFactory
import com.slack.eithernet.ApiResultConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // OkHttpClient handles HTTP requests and responses, and gives deep logs for debugging if in debug mode
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        return builder.build()
    }

    // Moshi is used for JSON parsing into kotlin objects
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }


    // The MoviesApi defines API endpoints, and the Retrofit instance is responsible for executing HTTP requests to those endpoints
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(ApiResultConverterFactory)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ApiResultCallAdapterFactory)
            .client(okHttpClient)
            .build()
    }
}