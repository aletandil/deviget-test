package com.projecttesting.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val retrofitBuilder: Retrofit.Builder,
    private val okHttpClientBuilder: OkHttpClient.Builder) {

    fun <S> createService(serviceClass: Class<S>): S {
        val okHttpClient = okHttpClientBuilder
            .build()

        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(serviceClass)

    }
}