package com.heechan.moredetailed

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {
    private val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val clientBuilder = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)

    private val retrofitClient = Retrofit.Builder()
        .baseUrl("https://openapi.naver.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(clientBuilder.build())

    fun get(): Retrofit {
        return retrofitClient.build()
    }
}