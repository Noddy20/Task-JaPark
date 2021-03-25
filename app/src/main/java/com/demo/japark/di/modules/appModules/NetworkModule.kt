package com.demo.japark.di.modules.appModules

import com.demo.japark.BuildConfig
import com.demo.japark.constants.AppConstants
import com.demo.japark.network.RetrofitApiClient
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor) = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) addInterceptor(interceptor)
        connectTimeout(AppConstants.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(AppConstants.API_READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(AppConstants.API_READ_WRITE_TIMEOUT, TimeUnit.SECONDS)
    }.build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BASE_URL_API)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(Json {
            ignoreUnknownKeys = true
            isLenient = true
        }.asConverterFactory(AppConstants.API_CONTENT_MEDIA_TYPE.toMediaType()))
        .build()


    @Singleton
    @Provides
    fun getRetrofitApiInterface(retrofit: Retrofit): RetrofitApiClient = retrofit.create(RetrofitApiClient::class.java)

}