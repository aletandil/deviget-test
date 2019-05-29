package com.projecttesting.network

import kotlinx.coroutines.*
import retrofit2.Response

/**
 * Contains functions pertaining to network requests via API calls from retrofit Deferred functions.
 */
class NetworkHandler {

    companion object {

        /**
         * Generic function that makes HTTP request with Retrofit and exposes a [RequestCallback] to hook into
         * the lifecycle of the request.
         *
         * @param[deferred] [kotlinx.coroutines.Deferred] returned from making HTTP request.
         */
        suspend fun <T> request(deferred: Deferred<Response<T>>, callback: RequestCallback<T>) {

            // before making request, set loading state
            callback.onLoading()

            try {

                // make request
                val response = deferred.await()

                // check if successful
                if (response.isSuccessful)
                    callback.onSuccess(response.body())
                else
                    callback.onError(response.message())

            } catch (e: Exception) {

                // set error state
                callback.onError(e.message)
            }
        }
    }

}

/**
 * Sealed classes are similiar to Java enums
 */
sealed class NetworkResult<out T> {
    object Loading : NetworkResult<Nothing>()
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val error: Throwable) : NetworkResult<Nothing>()
}