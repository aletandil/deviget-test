package com.projecttesting.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.projecttesting.domain.LoadTopEntriesUseCase
import com.projecttesting.domain.LoadTopEntriesUseCaseParams
import com.projecttesting.domain.LoadTopEntriesUseCaseResult
import com.projecttesting.ui.base.ScopedViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


/**
 * [@Inject] annotation tells Dagger to use the constructor; otherwise
 * [@Provides] is required
 */
class MainViewModel @Inject constructor(
    private val loadTopEntriesUseCase: LoadTopEntriesUseCase

) : ScopedViewModel() {

    private var _topEntries: MediatorLiveData<LoadTopEntriesUseCaseResult> = loadTopEntriesUseCase.observe()
    val topEntries: LiveData<LoadTopEntriesUseCaseResult>
        get() = _topEntries

    val name: ObservableField<String> = ObservableField()

    init {

        loadTopEntriesUseCase.execute(
            LoadTopEntriesUseCaseParams.Get(
                coroutineScope,
                Dispatchers.IO
            )
        )
    }
}
