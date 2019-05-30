package com.projecttesting.domain

import androidx.lifecycle.LiveData
import com.projecttesting.data.models.Entry
import com.projecttesting.data.repositories.EntryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class LoadTopEntriesUseCase @Inject constructor(
    private val entriesRepository: EntryRepository
) : MediatorUseCase<LoadTopEntriesUseCaseParams, LoadTopEntriesUseCaseResult>() {


    override fun execute(parameters: LoadTopEntriesUseCaseParams) {

        result.postValue(LoadTopEntriesUseCaseResult.LoadTopEntriesLoading())

        when (parameters) {
            is LoadTopEntriesUseCaseParams.Get -> getTopEntries(parameters)
        }

    }


    /**
     * Return top entries from locally or remotely
     */
    private fun getTopEntries(parameters: LoadTopEntriesUseCaseParams.Get) {
        parameters.coroutineScope.launch {
            withContext(parameters.dispatcher) {

                val topEntries = entriesRepository.getTopEntries()
                if (topEntries != null) {
                    result.postValue(LoadTopEntriesUseCaseResult.LoadTopEntriesSuccessful(topEntries))
                } else {
                    result.postValue(LoadTopEntriesUseCaseResult.LoadTopEntriesError())
                }
            }
        }
    }
}

/**
 * CoroutineScope and CoroutineDispatcher are required for testing
 */
sealed class LoadTopEntriesUseCaseParams {

    class Get(
        val coroutineScope: CoroutineScope,
        val dispatcher: CoroutineDispatcher
    ) : LoadTopEntriesUseCaseParams()

}


sealed class LoadTopEntriesUseCaseResult {
    class LoadTopEntriesLoading() : LoadTopEntriesUseCaseResult()
    data class LoadTopEntriesSuccessful(val topEntriesResponse: LiveData<List<Entry>>?) : LoadTopEntriesUseCaseResult()
    class LoadTopEntriesError() : LoadTopEntriesUseCaseResult()
}