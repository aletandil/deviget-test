package com.projecttesting.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * ViewModel with coroutineScope
 *
 * Use coroutineScope to tie coroutines to the ViewModel's Lifecycle
 */
open class ScopedViewModel : ViewModel() {

    // SupervisorJob allows children jobs to fail independently
    private val job = SupervisorJob()

    protected val coroutineScope = CoroutineScope(Dispatchers.Main + job)


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}