package com.projecttesting.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.projecttesting.BASE_URL
import com.projecttesting.data.services.EntriesService
import com.projecttesting.network.ApiClient
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


/**
 * Module which provides all required dependencies about network
 */
@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object NetworkModule {

    /**
     * Provides the Retrofit.Builder
     * CoroutineCallAdapterFactory - A Retrofit 2 CallAdapter.Factory for Kotlin coroutine's Deferred.
     * Moshi replaces Gson
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
    }

    /**
     * Provides an OkHttpClient.Builder
     * @param httpLoggingInterceptor HttpLoggingInterceptor - added as a NetworkInterceptor
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideOkHttpClientBuilder(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideApiClient(
        retrofitBuilder: Retrofit.Builder,
        okHttpClientBuilder: OkHttpClient.Builder): ApiClient =
        ApiClient(retrofitBuilder, okHttpClientBuilder)


    /**
     * @return EntriesService
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideEntryService(apiClient: ApiClient): EntriesService {
        return apiClient.createService(EntriesService::class.java)
    }
}