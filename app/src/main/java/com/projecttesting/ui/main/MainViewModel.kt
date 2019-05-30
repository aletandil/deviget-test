package com.projecttesting.ui.main

import android.os.Parcelable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.projecttesting.data.models.Entry
import com.projecttesting.data.models.TopEntriesDataChildrenResponse
import com.projecttesting.data.repositories.EntryRepository
import com.projecttesting.domain.*
import com.projecttesting.ui.base.ScopedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * [@Inject] annotation tells Dagger to use the constructor; otherwise
 * [@Provides] is required
 */
class MainViewModel @Inject constructor(
    private val loadTopEntriesUseCase: LoadTopEntriesUseCase,
    private val markReadedEntryUseCase: MarkReadedEntryUseCase,
    private val dismissEntryUseCase: DismissEntryUseCase,
    val entryRepository: EntryRepository
    ) : ScopedViewModel() {

    var entriesScrollState: Parcelable? = null

    private var _topEntries: MediatorLiveData<LoadTopEntriesUseCaseResult> = loadTopEntriesUseCase.observe()
    val topEntries: LiveData<LoadTopEntriesUseCaseResult>
        get() = _topEntries

    fun getLocalTopEntries(): LiveData<List<Entry>> {
        return entryRepository.getLocalTopEntries()
    }

    init {

        loadTopEntriesUseCase.execute(
            LoadTopEntriesUseCaseParams.Get(
                coroutineScope,
                Dispatchers.IO
            )
        )
    }

    fun markReadedEntry(entry: Entry) {
        markReadedEntryUseCase.execute(
            MarkReadedEntryUseCaseParams.Mark(
                coroutineScope,
                Dispatchers.IO,
                entry
            )
        )
    }

    fun loadMoreEntries() {
        loadTopEntriesUseCase.execute(
            LoadTopEntriesUseCaseParams.Get(
                coroutineScope,
                Dispatchers.IO
            )
        )
    }

    fun dismissEntry(entry: Entry) {
        dismissEntryUseCase.execute(
            DismissEntryUseCaseParams.Dismiss(
                coroutineScope,
                Dispatchers.IO,
                entry
            )
        )
    }

}
