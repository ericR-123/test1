package com.location.codechallenge.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.location.codechallenge.util.Constants.BASE_URL
import com.location.codechallenge.util.Constants.DEBUG
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideOkHttpClient() =
        if (DEBUG) { // debug ON
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().apply {
                addInterceptor(logger)
                readTimeout(100, TimeUnit.SECONDS)
                connectTimeout(100, TimeUnit.SECONDS)
            }.build()
        } else // debug OFF
            OkHttpClient.Builder()
                .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) =
        retrofit.create(LocationService::class.java)
}
