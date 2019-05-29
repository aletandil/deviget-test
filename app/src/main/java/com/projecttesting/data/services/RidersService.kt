package com.projecttesting.data.services

import com.projecttesting.data.models.Rider
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface RidersService {

    @GET("/riders/{id}")
    fun getRiderById(@Path("id") riderId: Int): Deferred<Response<Rider>>

    /**
     * Rider link is supplied by initial token request
     */
    @GET
    fun getRiderByRiderLink(@Url riderLink: String): Deferred<Response<Rider>>

    @PUT("/riders/{id}")
    fun updateRider(@Path("id") riderId: Int, @Body rider: Rider): Deferred<Response<Rider>>

}
