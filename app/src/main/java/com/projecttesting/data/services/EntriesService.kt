package com.projecttesting.data.services

import com.projecttesting.data.models.TopEntriesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface EntriesService {

    @GET("top.json")
    fun getTopEntries(@QueryMap filters: Map<String, String>): Deferred<Response<TopEntriesResponse>>

    companion object {

        const val AFTER_FILTER: String = "after"

    }
}
