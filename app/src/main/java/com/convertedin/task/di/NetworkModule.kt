package com.convertedin.task.di

import com.convertedin.task.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import com.convertedin.task.data.service.GeneralService
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        var httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return httpClient.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(httpClient)
        .build()


    @Singleton
    @Provides
    fun provideGeneralService(retrofit: Retrofit): GeneralService =
        retrofit.create(GeneralService::class.java)
}