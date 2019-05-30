package com.projecttesting.domain

import com.projecttesting.data.models.Entry
import com.projecttesting.data.repositories.EntryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class DismissEntryUseCase @Inject constructor(
    private val entriesRepository: EntryRepository
) : MediatorUseCase<DismissEntryUseCaseParams, DismissEntryUseCaseResult>() {


    override fun execute(parameters: DismissEntryUseCaseParams) {

        result.postValue(DismissEntryUseCaseResult.DismissEntryLoading())

        when (parameters) {
            is DismissEntryUseCaseParams.Dismiss -> dismissEntry(parameters)
        }

    }


    /**
     * Return top entries from locally or remotely
     */
    private fun dismissEntry(parameters: DismissEntryUseCaseParams.Dismiss) {
        parameters.coroutineScope.launch {
            withContext(parameters.dispatcher) {
                val entry = parameters.entry
                entry.visible = false
                entriesRepository.updateEntry(entry)
                result.postValue(DismissEntryUseCaseResult.DismissEntrySuccessful())
            }
        }
    }
}

/**
 * CoroutineScope and CoroutineDispatcher are required for testing
 */
sealed class DismissEntryUseCaseParams {

    class Dismiss(
        val coroutineScope: CoroutineScope,
        val dispatcher: CoroutineDispatcher,
        val entry: Entry
    ) : DismissEntryUseCaseParams()

}


sealed class DismissEntryUseCaseResult {
    class DismissEntryLoading() : DismissEntryUseCaseResult()
    class DismissEntrySuccessful() : DismissEntryUseCaseResult()
    class DismissEntryError() : DismissEntryUseCaseResult()
}