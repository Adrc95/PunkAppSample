package com.adrc95.punkappsample.di

import com.adrc95.punkappsample.BuildConfig
import com.adrc95.core.network.client.RetrofitClient
import com.adrc95.punkappsample.data.server.service.PunkApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(): String = "https://punkapi-alxiw.amvera.io/v3/"

    @Provides
    @Singleton
    fun provideJson(): Json = RetrofitClient.createJson()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = RetrofitClient.createOkHttpClient(BuildConfig.DEBUG)

    @Provides
    @Singleton
    fun provideRetrofit(
        @Named("baseUrl") baseUrl: String,
        okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit = RetrofitClient.createRetrofit(baseUrl, okHttpClient, json)

    @Provides
    @Singleton
    fun providePunkApiService(retrofit: Retrofit): PunkApiService{
        return retrofit.create(PunkApiService::class.java)
    }
}
