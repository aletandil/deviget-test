package com.projecttesting.utils


import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Suspend function to use callback-based [Task] in coroutines
 *
 * @return result after completion
 * @throws Throwable original exception from library
 */
suspend fun <T> Task<T>.await(): T = suspendCoroutine { continuation ->
    addOnSuccessListener(continuation::resume)
    addOnFailureListener {
        continuation.resumeWithException(it)  // it == Exception
    }
}