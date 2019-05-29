package com.projecttesting.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.projecttesting.domain.LoadRiderUseCase
import com.projecttesting.domain.LoadRiderUseCaseParams
import com.projecttesting.domain.LoadRiderUseCaseResult
import com.projecttesting.ui.base.ScopedViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


/**
 * [@Inject] annotation tells Dagger to use the constructor; otherwise
 * [@Provides] is required
 */
class MainViewModel @Inject constructor(
    private val loadRiderUseCase: LoadRiderUseCase

) : ScopedViewModel() {

    private var _rider: MediatorLiveData<LoadRiderUseCaseResult> = loadRiderUseCase.observe()
    val rider: LiveData<LoadRiderUseCaseResult>
        get() = _rider

    val name: ObservableField<String> = ObservableField()

    init {

        loadRiderUseCase.execute(
            LoadRiderUseCaseParams.Get(
                coroutineScope,
                Dispatchers.IO
            )
        )
    }
}
