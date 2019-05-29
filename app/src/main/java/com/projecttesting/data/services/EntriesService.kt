package com.projecttesting.data.services

import com.projecttesting.data.models.TopEntriesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface EntriesService {

    @GET("top.json")
    fun getTopEntries(): Deferred<Response<TopEntriesResponse>>

}
