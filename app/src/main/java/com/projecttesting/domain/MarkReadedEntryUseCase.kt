package com.projecttesting.domain

import com.projecttesting.data.models.Entry
import com.projecttesting.data.repositories.EntryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class MarkReadedEntryUseCase @Inject constructor(
    private val entriesRepository: EntryRepository
) : MediatorUseCase<MarkReadedEntryUseCaseParams, MarkReadedEntryUseCaseResult>() {


    override fun execute(parameters: MarkReadedEntryUseCaseParams) {

        result.postValue(MarkReadedEntryUseCaseResult.MarkReadedEntryLoading())

        when (parameters) {
            is MarkReadedEntryUseCaseParams.Mark -> markReadedEntry(parameters)
        }

    }


    /**
     * Return top entries from locally or remotely
     */
    private fun markReadedEntry(parameters: MarkReadedEntryUseCaseParams.Mark) {
        parameters.coroutineScope.launch {
            withContext(parameters.dispatcher) {
                val entry = parameters.entry
                entry.readed = true
                entriesRepository.updateEntry(entry)
                result.postValue(MarkReadedEntryUseCaseResult.MarkReadedEntrySuccessful())
            }
        }
    }
}

/**
 * CoroutineScope and CoroutineDispatcher are required for testing
 */
sealed class MarkReadedEntryUseCaseParams {

    class Mark(
        val coroutineScope: CoroutineScope,
        val dispatcher: CoroutineDispatcher,
        val entry: Entry
    ) : MarkReadedEntryUseCaseParams()

}


sealed class MarkReadedEntryUseCaseResult {
    class MarkReadedEntryLoading() : MarkReadedEntryUseCaseResult()
    class MarkReadedEntrySuccessful() : MarkReadedEntryUseCaseResult()
    class MarkReadedEntryError() : MarkReadedEntryUseCaseResult()
}