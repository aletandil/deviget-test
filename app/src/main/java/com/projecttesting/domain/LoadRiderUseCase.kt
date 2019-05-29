package com.projecttesting.domain

import com.projecttesting.data.models.Rider
import com.projecttesting.data.repositories.RidersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Rider should be able to login/authenticate
 */
open class LoadRiderUseCase @Inject constructor(
    private val ridersRepository: RidersRepository
    // takes in LoadRiderUseCaseParams and outputs a Rider
) : MediatorUseCase<LoadRiderUseCaseParams, LoadRiderUseCaseResult>() {


    override fun execute(parameters: LoadRiderUseCaseParams) {

        result.postValue(LoadRiderUseCaseResult.LoadRiderLoading())

        when (parameters) {
            is LoadRiderUseCaseParams.Get -> getRider(parameters)
        }

    }


    /**
     * Return rider from locally or remotely
     */
    private fun getRider(parameters: LoadRiderUseCaseParams.Get) {
        parameters.coroutineScope.launch {
            withContext(parameters.dispatcher) {

                val rider = ridersRepository.getRider()
                if (rider != null) {
                    result.postValue(LoadRiderUseCaseResult.LoadRiderSuccessful(rider))
                } else {
                    result.postValue(LoadRiderUseCaseResult.LoadRiderError())
                }
            }
        }
    }
}

/**
 * CoroutineScope and CoroutineDispatcher are required for testing
 */
sealed class LoadRiderUseCaseParams {

    class Get(
        val coroutineScope: CoroutineScope,
        val dispatcher: CoroutineDispatcher
    ) : LoadRiderUseCaseParams()

}


sealed class LoadRiderUseCaseResult {
    class LoadRiderLoading() : LoadRiderUseCaseResult()
    data class LoadRiderSuccessful(val rider: Rider) : LoadRiderUseCaseResult()
    class LoadRiderError() : LoadRiderUseCaseResult()
}