package com.adrc95.punkappsample.di

import androidx.viewbinding.BuildConfig
import com.adrc95.punkappsample.data.service.APIService
import com.adrc95.punkappsample.data.service.PunkApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Named("punkapi")
    fun providesEndpoint(): String {
        return "https://api.punkapi.com/v2/"
    }

    @Provides
    @Singleton
    fun providesHttpLogging(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) httpLoggingInterceptor.level =
            HttpLoggingInterceptor.Level.BODY else httpLoggingInterceptor.level =
            HttpLoggingInterceptor.Level.NONE
        return httpLoggingInterceptor;
    }

    @ExperimentalSerializationApi
    @Provides
    @Named("json_factory")
    fun provideJsonFactory(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }.asConverterFactory(contentType)
    }

    @Provides
    @Singleton
    fun providePunkApiService(
        @Named("punkapi") endpoint: String,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @Named("json_factory") jsonFactory : Converter.Factory,
    ): APIService<PunkApiService> {
        return APIService(
            PunkApiService::class.java,
            endpoint,
            jsonFactory,
            arrayOf(httpLoggingInterceptor)
        )
    }

}