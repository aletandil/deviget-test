package com.projecttesting.domain

import androidx.lifecycle.MediatorLiveData

/**
 * Executes business logic in its execute method
 *
 * Takes in any object P and should return a result R
 */
abstract class MediatorUseCase<in P, R> {
    protected val result = MediatorLiveData<R>()

    // "open" in order to mock in tests
    open fun observe(): MediatorLiveData<R> {
        return result
    }

    abstract fun execute(parameters: P)
}
